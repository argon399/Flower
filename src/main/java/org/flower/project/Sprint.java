package org.flower.project;

import org.flower.project.issue.Issue;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sprint")
public class Sprint {
    @Id
    @GeneratedValue
    private int id;

    private String label;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateStart;

    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date dateEnd;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sprint_issue", joinColumns = @JoinColumn(name = "id_sprint"), inverseJoinColumns = @JoinColumn(name = "id_issue"))
    private List<Issue> issues = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
