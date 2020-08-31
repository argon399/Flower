package org.flower.project.issue;

public enum IssueType {
    EPIC(0), STORY(1), TASK(2), BUG(4);

    private int type;

    IssueType(int type) {
        this.type = type;
    }
}
