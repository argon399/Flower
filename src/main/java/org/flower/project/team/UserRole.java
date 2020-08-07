package org.flower.project.team;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER(0), ADMIN(1);

    private int userRole;

    UserRole(int userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
