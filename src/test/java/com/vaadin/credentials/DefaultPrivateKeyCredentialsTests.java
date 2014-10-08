package com.vaadin.credentials;

public class DefaultPrivateKeyCredentialsTests extends CredentialsTests<DefaultPrivateKeyCredentials> {

    @Override
    public DefaultPrivateKeyCredentials createSut() {
        return new DefaultPrivateKeyCredentials(username);
    }
}
