package com.vaadin;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.vaadin.commands.ListMembersCommand;
import com.vaadin.commands.ListMembersResponse;
import com.vaadin.credentials.Credentials;
import com.vaadin.factories.CommandFactory;
import com.vaadin.factories.GerritConnectionFactory;

import java.util.Arrays;
import java.util.Optional;

public class GerritClient {

    private final String host;
    private final Credentials credentials;
    private final CommandFactory commandFactory;
    private final GerritConnectionFactory connectionFactory;

    public GerritClient(String host, Credentials credentials) {
        this(Guice.createInjector(new GerritClientModule()), host, credentials);
    }

    private GerritClient(Injector injector, String host, Credentials credentials) {
        this(injector.getInstance(CommandFactory.class), injector.getInstance(GerritConnectionFactory.class), host, credentials);
    }

    GerritClient(CommandFactory commandFactory, GerritConnectionFactory connectionFactory, String host, Credentials credentials) {
        this.commandFactory = commandFactory;
        this.connectionFactory = connectionFactory;
        this.host = host;
        this.credentials = credentials;
    }

    public Member[] getGroupMembers(String groupName) throws GerritClientException {
        ListMembersCommand command = commandFactory.createListMembersCommand(groupName);
        command.setRecursive(true);

        GerritConnection connection = connectionFactory.getConnection(host, credentials);

        ListMembersResponse response = command.getResponse(connection);

        if(response.hasErrors()) {
            throw new GerritClientException(response.getErrorMessage());
        }

        return response.getMembers();
    }

    public Member getMemberFromGroup(int id, String groupName) throws GerritClientException {
        Optional<Member> member = Arrays.stream(getGroupMembers(groupName))
                                        .filter(m -> m.Id == id)
                                        .findFirst();

        if(member.isPresent()) {
            return member.get();
        }

        throw new GerritClientException(String.format("Member with id '%s' is not found in group '%s.'", id, groupName));
    }
}
