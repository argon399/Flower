package org.flower.workflow.filter;

import org.flower.workflow.issue.Issue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterLabel implements Filter {
    private String label;

    public FilterLabel(String name) {
        this.label = name;
    }

    @Override
    public Set<Issue> filter(List<Issue> issues) {
        if (label == null) {
            return null;
        }
        return issues.stream()
                .filter(issue -> issue.getLabel() != null)
                .filter(issue -> issue.getLabel().equals(label))
                .collect(Collectors.toSet());
    }
}
