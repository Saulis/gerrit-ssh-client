package com.vaadin.credentials;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CustomPrivateKeyCredentialsTests extends CredentialsTests<CustomPrivateKeyCredentials> {

    @Override
    public CustomPrivateKeyCredentials createSut() {
        return new CustomPrivateKeyCredentials(username, "bar");
    }

    @Test
    public void privateKeyIsSet() {
        assertThat(sut.getPrivateKey(), is("bar"));
    }

}