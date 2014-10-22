package org.vaadin.gerrit;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.vaadin.gerrit.credentials.Credentials;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public class GerritConnection {
    private final JSch jsch;
    private final String host;
    private final Credentials credentials;

    @Inject
    public GerritConnection(JSch jsch, @Assisted String host, @Assisted Credentials credentials) {
        this.jsch = jsch;
        this.host = host;
        this.credentials = credentials;
    }

    public String executeCommand(String command) throws GerritClientException {
        ChannelExec channel = null;
        Session session = null;

        try {
            session = openSession();
            channel = openChannel(command, session);

            InputStream response = channel.getInputStream();

            return IOUtils.toString(response, Charset.forName("utf-8"));
        }
        catch (Exception e) {
            throw new GerritClientException(e);
        }
        finally {
            if (channel != null)
                channel.disconnect();
            if (session != null)
                session.disconnect();
        }
    }

    private Session openSession() throws JSchException {
        addIdentity();

        Session session = jsch.getSession(credentials.getUsername(), host, 29418);
        Properties properties = System.getProperties();
        properties.put("StrictHostKeyChecking", "no");

        session.setConfig(properties);
        session.connect();

        return session;
    }

    private void addIdentity() throws JSchException {
        if(credentials.hasPassphrase()) {
            jsch.addIdentity(credentials.getPrivateKey(), credentials.getPassphrase());
        } else {
            jsch.addIdentity(credentials.getPrivateKey());
        }
    }

    private ChannelExec openChannel(String command, Session session) throws JSchException {
        ChannelExec channel;
        channel = (ChannelExec)session.openChannel("exec");

        channel.setCommand(command);

        channel.connect();

        return channel;
    }
}
