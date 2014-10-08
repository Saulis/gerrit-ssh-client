package com.vaadin.factories;

import com.vaadin.GerritConnection;
import com.vaadin.credentials.Credentials;

public interface GerritConnectionFactory {
    public GerritConnection getConnection(String host, Credentials credentials);
}
