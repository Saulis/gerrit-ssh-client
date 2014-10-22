package org.vaadin.gerrit.credentials;

public class CustomPrivateKeyCredentials extends Credentials {
    private final String privateKey;

    public CustomPrivateKeyCredentials(String username, String privateKey) {
        super(username);
        this.privateKey = privateKey;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }
}
