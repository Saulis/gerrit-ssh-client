package com.vaadin;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.vaadin.credentials.Credentials;
import com.vaadin.credentials.DefaultPrivateKeyCredentials;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GerritConnectionTest {

    private final String username = "foo";
    private final String host = "bar";
    private final String command = "foobar";

    JSch jsch;
    Session session;
    ChannelExec channel;

    private GerritConnection sut;
    private Credentials credentials;

    @Before
    public void setup() throws JSchException, IOException {
        jsch = mock(JSch.class);
        channel = mock(ChannelExec.class);
        session = mock(Session.class);
        credentials = mock(Credentials.class);

        when(jsch.getSession(anyString(), anyString(), anyInt())).thenReturn(session);
        when(session.openChannel(anyString())).thenReturn(channel);
        mockChannelToReturn("");

        sut = new GerritConnection(jsch, host, credentials);
    }

    private void mockChannelToReturn(String response) throws IOException {
        when(channel.getInputStream()).thenReturn(new ByteArrayInputStream(response.getBytes()));
    }

    private String executeCommand() {
        try {
            return sut.executeCommand(command);
        } catch (GerritClientException e) {
            fail(e.getMessage());
        }

        return "";
    }

    @Test
    public void identityIsAdded() throws JSchException {
        when(credentials.getPrivateKey()).thenReturn("foobar");

        executeCommand();

        verify(jsch).addIdentity("foobar");
    }

    @Test
    public void sessionIsCreated() throws JSchException {
        when(credentials.getUsername()).thenReturn(username);

        executeCommand();

        verify(jsch).getSession(username, host, 29418);
    }

    @Test
    public void channelIsOpened() throws JSchException {
        executeCommand();

        verify(session).openChannel("exec");
    }

    @Test
    public void commandIsSet() throws IOException {
        executeCommand();

        verify(channel).setCommand("foobar");
    }

    @Test
    public void responseIsReturned() throws IOException {
        mockChannelToReturn("response");

        String response = executeCommand();

        assertThat(response, is("response"));
    }

    @Test
    public void channelIsClosed() {
        executeCommand();

        verify(channel).disconnect();
    }

    @Test
    public void sessionIsClosed() {
        executeCommand();

        verify(session).disconnect();
    }

    @Test(expected = GerritClientException.class)
    public void exceptionIsThrownOnException() throws JSchException, GerritClientException {
        when(session.openChannel(anyString())).thenThrow(Exception.class);

        sut.executeCommand(command);
    }
}
