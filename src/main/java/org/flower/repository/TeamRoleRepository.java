package org.flower.repository;

import org.flower.workflow.team.TeamRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRoleRepository extends JpaRepository<TeamRole, Long> {
    TeamRole findByRole(String role);
}
