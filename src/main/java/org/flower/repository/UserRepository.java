package org.flower.repository;

import org.flower.project.team.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByActivationCode(String code);

    User findByPasswordCode(String code);
}
