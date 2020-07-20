package org.flower.workflow.filter;

import org.flower.workflow.issue.Issue;

import java.util.List;
import java.util.Set;

public interface Filter {
    Set<Issue> filter(List<Issue> issues);
}
