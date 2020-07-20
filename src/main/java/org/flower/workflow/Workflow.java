package org.flower.workflow;

import org.flower.workflow.filter.Filter;
import org.flower.workflow.issue.Issue;
import org.flower.workflow.team.Team;
import org.flower.workflow.team.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "workflow")
public class Workflow {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(length = 4000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_admin")
    private User admin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "backlog_issue", joinColumns = @JoinColumn(name = "id_workflow"), inverseJoinColumns = @JoinColumn(name = "id_issue"))
    private List<Issue> backlog = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "workflow_sprint", joinColumns = @JoinColumn(name = "id_workflow"), inverseJoinColumns = @JoinColumn(name = "id_sprint"))
    private List<Sprint> sprints = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<Issue> getBacklog() {
        return backlog;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void addIssue(Issue issue) {
        backlog.add(issue);
    }

    public boolean deleteIssue(Issue issue) {
        if (issue == null) {
            return false;
        }

        return backlog.remove(issue);
    }

    public Set<Issue> filterBacklog(Filter filter) {
        if (filter == null) {
            return null;
        }

        return filter.filter(backlog);
    }

    public Set<Issue> filterSprint(Filter filter, Sprint sprint) {
        if (filter == null || sprint == null || sprint.getIssues() == null) {
            return null;
        }

        return filter.filter(sprint.getIssues());
    }
}
