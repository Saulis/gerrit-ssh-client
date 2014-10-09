package com.vaadin.commands;

import com.vaadin.Member;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ListMembersResponseTest {

    private ListMembersResponse getResponse(String response) {
        return new ListMembersResponse(response);
    }

    @Test
    public void nullIsHandled() {
        ListMembersResponse response = getResponse(null);

        assertThat(response.hasErrors(), is(true));
    }

    @Test
    public void errorIsHandled() {
        ListMembersResponse response = getResponse("Error");

        assertThat(response.hasErrors(), is(true));
        assertThat(response.getErrorMessage(), is("Error"));
    }

    @Test
    public void emptyResponseIsHandled() {
        ListMembersResponse response = getResponse("id\tusername\tfull name\temail");

        assertThat(response.hasErrors(), is(false));
        assertThat(response.getMembers().length, is(0));
    }

    @Test
    public void membersAreReturned() {
        ListMembersResponse response = getResponseWithMembers();
        Member[] members = response.getMembers();

        assertThat(members.length, is(2));

        Member member = members[1];
        assertThat(member.Id, is(100001));
        assertThat(member.Username, is("johnny"));
        assertThat(member.FullName, is("John Smith"));
        assertThat(member.Email, is("n/a"));
    }

    private ListMembersResponse getResponseWithMembers() {
        return getResponse("id\tusername\tfull name\temail\n" +
                "100000\tjim\tJim Bob\tsomebody@example.com\n" +
                "100001\tjohnny\tJohn Smith\tn/a");
    }

}