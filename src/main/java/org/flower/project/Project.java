package org.flower.project;

import org.flower.project.filter.Filter;
import org.flower.project.issue.Issue;
import org.flower.project.team.Team;
import org.flower.project.team.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @Column(length = 4000)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_owner")
    private User owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_team")
    private Team team;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "backlog_issue", joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = "id_issue"))
    private List<Issue> backlog = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_sprint", joinColumns = @JoinColumn(name = "id_project"), inverseJoinColumns = @JoinColumn(name = "id_sprint"))
    private List<Sprint> sprints = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                name.equals(project.name) &&
                description.equals(project.description) &&
                owner.equals(project.owner) &&
                Objects.equals(team, project.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, owner, team);
    }

    public void removeIssue(Issue issue) {
        if (issue != null) {
            backlog.remove(issue);

            for (Sprint sprint : sprints) {
                sprint.deleteIssue(issue);
            }
        }
    }

    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
    }

    public void removeSprint(Sprint sprint) {
        sprints.remove(sprint);
    }
}
