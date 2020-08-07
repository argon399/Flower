package org.flower.repository;

import org.flower.project.issue.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueStatusRepository extends JpaRepository<IssueStatus, Integer> {
    IssueStatus findByStatus(String status);
}
