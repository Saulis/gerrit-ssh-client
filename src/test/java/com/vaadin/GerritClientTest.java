package com.vaadin;

import com.google.inject.Inject;
import com.vaadin.commands.ListMembersCommand;
import com.vaadin.commands.ListMembersResponse;
import com.vaadin.credentials.Credentials;
import com.vaadin.factories.CommandFactory;
import com.vaadin.factories.GerritConnectionFactory;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class GerritClientTest {

    private final String host = "host";
    private GerritClient sut;
    private ListMembersCommand listMembersCommand;
    private ListMembersResponse listMembersResponse;
    private Credentials credentials;
    private Member[] members  = new Member[0];

    @Inject
    private CommandFactory commandFactory;

    @Inject
    private GerritConnectionFactory connectionFactory;

    @Before
    public void setup() throws GerritClientException {
        credentials = mock(Credentials.class);

        sut = new GerritClient(commandFactory, this.connectionFactory, host, credentials);

        listMembersCommand = mock(ListMembersCommand.class);
        listMembersResponse = mock(ListMembersResponse.class);

        when(commandFactory.createListMembersCommand(anyString())).thenReturn(listMembersCommand);
        when(listMembersCommand.getResponse(any(GerritConnection.class))).thenReturn(listMembersResponse);
    }

    private Member[] getGroupMembers() throws Exception {
        return sut.getGroupMembers("foo");
    }

    @Test
    public void listMembersCommandIsCreated() throws Exception {
        getGroupMembers();

        verify(commandFactory).createListMembersCommand("foo");
    }

    @Test
    public void listMembersConnectionIsCreated() throws Exception {
        getGroupMembers();

        verify(connectionFactory).getConnection(host, credentials);
    }

    @Test
    public void listMembersIsRecursive() throws Exception {
        getGroupMembers();

        verify(listMembersCommand).setRecursive(true);
    }

    @Test
    public void membersAreReturned() throws Exception {
        when(listMembersResponse.getMembers()).thenReturn(members);

        assertThat(getGroupMembers(), is(members));
    }

    @Test(expected = GerritClientException.class)
    public void getGroupMembersThrowsExceptionOnError() throws Exception {
        when(listMembersResponse.hasErrors()).thenReturn(true);

        getGroupMembers();
    }

    @Test
    public void memberIsFetched() throws GerritClientException {
        Member[] members = getMembers(1234, 2345);
        when(listMembersResponse.getMembers()).thenReturn(members);

        Member member = sut.getMemberFromGroup(2345, "foobar");

        assertThat(member.Id, is(2345));
    }

    @Test(expected = GerritClientException.class)
    public void memberNotFoundThrowsException() throws GerritClientException {
        when(listMembersResponse.getMembers()).thenReturn(new Member[0]);

        sut.getMemberFromGroup(1234, "foobar");
    }

    private Member[] getMembers(int... ids) {
        return IntStream.of(ids)
                .mapToObj(i -> getMember(i))
                .toArray(s -> new Member[s]);
    }

    private Member getMember(int id) {
        Member member = new Member();
        member.Id = id;

        return member;
    }
}
