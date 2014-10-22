package org.vaadin.gerrit.factories;

import org.vaadin.gerrit.commands.ListMembersCommand;

public interface CommandFactory {
    public ListMembersCommand createListMembersCommand(String groupName);
}
