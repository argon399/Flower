package org.flower.project.issue;

public enum IssueStatus {
    OPEN(0), IN_PROGRESS(1), REVIEW(2), TEST(3), RESOLVED(4), REOPEN(5), CLOSED(6);

    private int status;

    IssueStatus(int status) {
        this.status = status;
    }
}
