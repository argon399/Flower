package org.flower.project.issue;

public enum IssuePriority {
    LOW(0), MIDDLE(1), HIGH(2);

    private int priority;

    IssuePriority(int priority) {
        this.priority = priority;
    }
}
