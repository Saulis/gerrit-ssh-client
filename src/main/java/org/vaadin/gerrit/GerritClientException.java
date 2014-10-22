package org.vaadin.gerrit;

public class GerritClientException extends Exception {
    public GerritClientException(String message) {
        super(message);
    }

    public GerritClientException(Exception innerException) {
        super(innerException);
    }
}
