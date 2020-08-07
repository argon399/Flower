package org.flower.repository;

import org.flower.project.issue.IssueType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueTypeRepository extends JpaRepository<IssueType, Integer> {
    IssueType findByType(String type);
}
