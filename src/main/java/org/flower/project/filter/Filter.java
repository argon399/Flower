package org.flower.project.filter;

import org.flower.project.issue.Issue;

import java.util.List;
import java.util.Set;

public interface Filter {
    Set<Issue> filter(List<Issue> issues);
}
