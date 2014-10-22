package org.vaadin.gerrit.factories;

import org.vaadin.gerrit.GerritConnection;
import org.vaadin.gerrit.credentials.Credentials;

public interface GerritConnectionFactory {
    public GerritConnection getConnection(String host, Credentials credentials);
}
