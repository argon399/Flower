package org.flower.project.filter;

import org.flower.project.issue.Issue;

import java.util.List;

public interface Filter {
    List<Issue> filter(List<Issue> issues);
}
