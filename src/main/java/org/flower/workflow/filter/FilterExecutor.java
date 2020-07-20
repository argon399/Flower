package org.flower.workflow.filter;

import org.flower.workflow.issue.Issue;
import org.flower.workflow.team.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterExecutor implements Filter {
    private User executor;

    public FilterExecutor(User executor) {
        this.executor = executor;
    }

    @Override
    public Set<Issue> filter(List<Issue> issues) {
        if (executor == null) {
            return null;
        }
        return issues.stream()
                .filter(issue -> issue.getExecutor() != null)
                .filter(issue -> issue.getExecutor().getId().equals(executor.getId()))
                .collect(Collectors.toSet());
    }
}
