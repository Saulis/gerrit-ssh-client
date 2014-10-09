package com.vaadin.credentials;

public class DefaultPrivateKeyCredentialsTest extends CredentialsTest<DefaultPrivateKeyCredentials> {

    @Override
    public DefaultPrivateKeyCredentials createSut() {
        return new DefaultPrivateKeyCredentials(username);
    }
}
