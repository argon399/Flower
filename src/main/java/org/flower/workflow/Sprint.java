package org.flower.workflow;

import org.flower.workflow.issue.Issue;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue
    private Long id;

    private String label;

    private Date dateStart;

    private Date dateEnd;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sprint_issue", joinColumns = @JoinColumn(name = "id_sprint"), inverseJoinColumns = @JoinColumn(name = "id_issue"))
    private List<Issue> issues = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void addIssue(Issue issue) {
        if (issue != null) {
            issues.add(issue);
        }
    }

    public boolean deleteIssue(Issue issue) {
        if (issue == null) {
            return false;
        }

        return issues.remove(issue);
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
