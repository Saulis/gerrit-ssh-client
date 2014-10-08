package com.vaadin.commands;

import com.vaadin.Member;

import java.util.ArrayList;
import java.util.List;

public class ListMembersResponse {

    private String[] lines;

    public ListMembersResponse(String response) {
        if(response != null) {
            lines = response.split(System.lineSeparator());
        } else {
            lines = new String[] { "" };
        }
    }

    public boolean hasErrors() {
        return !hasHeaders();
    }

    public String getErrorMessage() {
        return getFirstLine();
    }

    private boolean hasHeaders() {
        return getNumberOfColumns(getFirstLine()) >= 4;
    }

    private String getFirstLine() {
        return lines[0];
    }

    private int getNumberOfColumns(String line) {
        return getColumns(line).length;
    }

    public Member[] getMembers() {
        List<Member> members = new ArrayList<Member>();

        if(hasHeaders()) {
            for(int i=1;i < lines.length;i++) {
                members.add(getMember(lines[i]));
            }
        }

        return members.toArray(new Member[members.size()]);
    }

    private Member getMember(String line) {
        Member member = new Member();

        member.Id = getColumnAsInt(line, 0);
        member.Username = getColumn(line, 1);
        member.FullName = getColumn(line, 2);
        member.Email = getColumn(line, 3);

        return member;
    }

    private int getColumnAsInt(String line, int i) {
        return Integer.valueOf(getColumn(line, i));
    }

    private String getColumn(String line, int index) {
        return getColumns(line)[index];
    }

    private String[] getColumns(String lines) {
        return lines.split("\t");
    }
}
