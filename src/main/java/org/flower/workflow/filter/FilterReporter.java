package org.flower.workflow.filter;

import org.flower.workflow.issue.Issue;
import org.flower.workflow.team.User;

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
                .filter(issue -> issue.getReporter().getId().equals(reporter.getId()))
                .collect(Collectors.toSet());
    }
}
