package org.vaadin.gerrit.commands;

import org.vaadin.gerrit.Member;

import java.util.Arrays;
import java.util.List;

public class ListMembersResponse {

    private List<String> lines;

    public ListMembersResponse(String response) {
        if(response != null) {

            lines = Arrays.asList(response.split(System.lineSeparator()));
        } else {
            lines = Arrays.asList(new String[] { "" });
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
        return lines.get(0);
    }

    private int getNumberOfColumns(String line) {
        return getColumns(line).length;
    }

    public Member[] getMembers() {
            return lines.stream()
                        .skip(1)
                        .map(this::getMember)
                        .toArray(size -> new Member[size]);
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
