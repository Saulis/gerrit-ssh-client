package com.vaadin;

import com.vaadin.credentials.DefaultPrivateKeyCredentials;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class GerritClientIT {

    private GerritClient sut;

    @Before
    public void setup() {
        DefaultPrivateKeyCredentials credentials = new DefaultPrivateKeyCredentials(getUsername());
        sut = new GerritClient(getHost(), credentials);
    }

    private String getHost() {
        return getProperty("host");
    }

    private String getUsername() {
        return getProperty("username");
    }

    private String getProperty(String property) {
        String value = System.getProperty(property);

        if(value == null || value.equals("")) {
            fail(String.format("Property '%s' is missing. Add -Dproperty=value when running integration tests.", property));
        }

        return value;
    }

    @Test
    public void membersAreFound() throws GerritClientException {
        Member[] groupMembers = sut.getGroupMembers("Project Deleters (evil)");

        assertThat(groupMembers.length, is(1));
    }

    @Test
    public void emptyGroupIsFound() throws Exception {
        Member[] groupMembers = sut.getGroupMembers("Registered Users");

        assertThat(groupMembers.length, is(0));
    }
}
