package org.flower.project.filter;

import org.flower.project.issue.Issue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterDateCreate implements Filter {
    private Date dateCreate;

    public FilterDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public Set<Issue> filter(List<Issue> issues) {
        if (dateCreate == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        return issues.stream()
                .filter(issue -> issue.getDateCreated() != null)
                .filter(issue -> dateFormat.format(issue.getDateCreated()).equals(dateFormat.format(dateCreate)))
                .collect(Collectors.toSet());
    }
}
