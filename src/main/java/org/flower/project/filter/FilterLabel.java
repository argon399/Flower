package org.flower.project.filter;

import org.flower.project.issue.Issue;

import java.util.List;
import java.util.stream.Collectors;

public class FilterLabel implements Filter {
    private String label;

    public FilterLabel(String name) {
        this.label = name;
    }

    @Override
    public List<Issue> filter(List<Issue> issues) {
        if (label == null) {
            return null;
        }
        return issues.stream()
                .filter(issue -> issue.getLabel() != null)
                .filter(issue -> issue.getLabel().contains(label))
                .collect(Collectors.toList());
    }
}
