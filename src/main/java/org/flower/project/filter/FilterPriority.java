package org.flower.project.filter;

import org.flower.project.issue.Issue;
import org.flower.project.issue.IssuePriority;

import java.util.List;
import java.util.stream.Collectors;

public class FilterPriority implements Filter {
    private IssuePriority priority;

    public FilterPriority(IssuePriority priority) {
        this.priority = priority;
    }

    @Override
    public List<Issue> filter(List<Issue> issues) {
        if (priority == null) {
            return null;
        }

        return issues.stream()
                .filter(issue -> issue.getPriority() != null)
                .filter(issue -> issue.getPriority().equals(priority))
                .collect(Collectors.toList());
    }
}
