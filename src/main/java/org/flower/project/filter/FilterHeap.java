package org.flower.project.filter;

import org.flower.project.Sprint;
import org.flower.project.issue.Issue;

import java.util.ArrayList;
import java.util.List;

public class FilterHeap {
    private List<Filter> filters = new ArrayList<>();

    public List<Issue> filter(List<Issue> issues) {
        List<Issue> filteredIssues = issues;

        for (Filter filter : filters) {
            filteredIssues = filter.filter(filteredIssues);
        }

        return filteredIssues;
    }

    public List<Sprint> filterSprint(List<Sprint> sprints) {
        List<Sprint> filteredSprints = new ArrayList<>();

        for (Sprint sprint : sprints) {
            Sprint newSprint = new Sprint();
            newSprint.setId(sprint.getId());
            newSprint.setLabel(sprint.getLabel());
            newSprint.setDateStart(sprint.getDateStart());
            newSprint.setDateEnd(sprint.getDateEnd());

            newSprint.setIssues(filter(sprint.getIssues()));

            filteredSprints.add(newSprint);
        }

        return filteredSprints;
    }


    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    public void clear() {
        filters.clear();
    }
}
