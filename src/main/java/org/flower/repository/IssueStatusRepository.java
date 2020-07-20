package org.flower.repository;

import org.flower.workflow.issue.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Long> {
    IssueStatus findByStatus(String status);
}
