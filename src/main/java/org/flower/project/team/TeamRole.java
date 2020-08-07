package org.flower.project.team;

public enum TeamRole {
    DEVELOPER(0), LEADER(1), PRODUCT_OWNER(2);

    private int role;

    TeamRole(int role) {
        this.role = role;
    }
}
