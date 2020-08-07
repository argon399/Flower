package org.flower.project.filter;

import org.flower.project.issue.Issue;
import org.flower.project.team.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterReporter implements Filter {
    private User reporter;

    public FilterReporter(User reporter) {
        this.reporter = reporter;
    }

    @Override
    public Set<Issue> filter(List<Issue> issues) {
        if (reporter == null) {
            return null;
        }

        return issues.stream()
                .filter(issue -> issue.getReporter() != null)
                .filter(issue -> issue.getReporter().getId() == reporter.getId())
                .collect(Collectors.toSet());
    }
}
