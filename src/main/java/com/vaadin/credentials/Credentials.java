package com.vaadin.credentials;

public abstract class Credentials {
    private final String username;
    private String passphrase;

    public Credentials(String username) {
        this.username = username;
        this.passphrase = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String value) {
        this.passphrase = value;
    }

    public boolean hasPassphrase() {
        return !passphrase.equals("");
    }

    public abstract String getPrivateKey();
}
