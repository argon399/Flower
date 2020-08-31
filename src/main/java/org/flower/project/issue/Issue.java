package org.flower.project.issue;

import org.flower.project.team.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue
    private int id;

    private String label;
    @Column(length = 4000 )
    private String description;
    private Date dateCreated;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_priority")
    private IssuePriority priority;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_status")
    private IssueStatus status;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_type")
    private IssueType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_parent")
    private Issue parent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reporter")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_executor")
    private User executor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public IssueType getType() {
        return type;
    }

    public void setType(IssueType type) {
        this.type = type;
    }

    public Issue getParent() {
        return parent;
    }

    public void setParent(Issue parent) {
        this.parent = parent;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }
}
