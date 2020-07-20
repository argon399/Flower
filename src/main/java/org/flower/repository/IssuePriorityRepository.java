package org.flower.repository;

import org.flower.workflow.issue.IssuePriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuePriorityRepository extends JpaRepository<IssuePriority, Long> {
    IssuePriority findByPriority(String priority);
}
