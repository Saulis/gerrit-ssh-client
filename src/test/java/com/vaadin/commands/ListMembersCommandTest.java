package com.vaadin.commands;

import com.vaadin.GerritClientException;
import com.vaadin.GerritConnection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ListMembersCommandTest {

    private ListMembersCommand sut = new ListMembersCommand("foobar");

    private GerritConnection connection;

    @Before
    public void setup() throws GerritClientException {
        sut = new ListMembersCommand("foobar");

        connection = mock(GerritConnection.class);
        when(connection.executeCommand(anyString())).thenReturn("foo");
    }

    @Test
    public void commandIsNotRecursive() throws GerritClientException {
        getResponse();

        verify(connection).executeCommand("gerrit ls-members foobar");
    }

    @Test
    public void commandIsRecursive() throws GerritClientException {
        sut.setRecursive(true);

        getResponse();

        verify(connection).executeCommand("gerrit ls-members foobar --recursive");
    }

    private ListMembersResponse getResponse() {
        try {
            return sut.getResponse(connection);
        } catch (GerritClientException e) {
            fail(e.getMessage());
        }

        return null;
    }

}