package com.vaadin.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.vaadin.GerritClientException;
import com.vaadin.GerritConnection;

public class ListMembersCommand {
    private boolean recursive;
    private final String groupName;

    @Inject
    public ListMembersCommand(@Assisted String groupName) {

        this.groupName = groupName;
        this.recursive = false;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    private String getCommand() {
        return String.format("gerrit ls-members %s%s", groupName, recursive ? " --recursive" : "");
    }

    public ListMembersResponse getResponse(GerritConnection connection) throws GerritClientException {
        String response = connection.executeCommand(getCommand());

        return new ListMembersResponse(response);
    }
}

