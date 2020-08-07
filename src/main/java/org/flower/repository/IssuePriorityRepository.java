package org.flower.repository;

import org.flower.project.issue.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Integer> {
    IssuePriority findByPriority(String priority);
}
