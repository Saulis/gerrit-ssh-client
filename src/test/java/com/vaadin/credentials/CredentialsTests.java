package com.vaadin.credentials;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public abstract class CredentialsTests<T extends Credentials> {

    protected final String username = "foo";
    protected T sut;

    public abstract T createSut();

    @Before
    public void setup() {
        sut = createSut();
    }

    @Test
    public void usernameIsSet() {
        assertThat(sut.getUsername(), is(username));
    }

    @Test
    public void passPhraseIsSet() {
        sut.setPassphrase("foo");

        assertTrue(sut.hasPassphrase());
        assertThat(sut.getPassphrase(), is("foo"));
    }
}
