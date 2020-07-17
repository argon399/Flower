package org.flower.workflow.team;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER, TEAM_LEADER, ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
