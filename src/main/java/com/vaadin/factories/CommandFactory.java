package com.vaadin.factories;

import com.vaadin.commands.ListMembersCommand;

public interface CommandFactory {
    public ListMembersCommand createListMembersCommand(String groupName);
}
