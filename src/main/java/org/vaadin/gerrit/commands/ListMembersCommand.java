package org.vaadin.gerrit.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.vaadin.gerrit.GerritClientException;
import org.vaadin.gerrit.GerritConnection;

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
        //GroupName has to escaped: https://code.google.com/p/gerrit/issues/detail?id=2589
        return String.format("gerrit ls-members '%s'%s", groupName, recursive ? " --recursive" : "");
    }

    public ListMembersResponse getResponse(GerritConnection connection) throws GerritClientException {
        String response = connection.executeCommand(getCommand());

        return new ListMembersResponse(response);
    }
}

