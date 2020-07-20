package org.flower.workflow.filter;

import org.flower.workflow.issue.Issue;
import org.flower.workflow.issue.IssuePriority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterPriority implements Filter {
    private IssuePriority priority;

    public FilterPriority(IssuePriority priority) {
        this.priority = priority;
    }

    @Override
    public Set<Issue> filter(List<Issue> issues) {
        if (priority == null) {
            return null;
        }

        return issues.stream()
                .filter(issue -> issue.getPriority() != null)
                .filter(issue -> issue.getPriority().equals(priority))
                .collect(Collectors.toSet());
    }
}
