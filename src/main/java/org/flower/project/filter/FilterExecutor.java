package org.flower.project.filter;

import org.flower.project.issue.Issue;
import org.flower.project.team.User;

import java.util.List;
import java.util.stream.Collectors;

public class FilterExecutor implements Filter {
    private User executor;

    public FilterExecutor(User executor) {
        this.executor = executor;
    }

    @Override
    public List<Issue> filter(List<Issue> issues) {
        if (executor == null) {
            return null;
        }
        return issues.stream()
                .filter(issue -> issue.getExecutor() != null)
                .filter(issue -> issue.getExecutor().getId() == executor.getId())
                .collect(Collectors.toList());
    }
}
