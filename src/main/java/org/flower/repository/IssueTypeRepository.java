package org.flower.repository;

import org.flower.workflow.issue.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueTypeRepository extends JpaRepository<IssueType, Long> {
    IssueType findByType(String type);
}
