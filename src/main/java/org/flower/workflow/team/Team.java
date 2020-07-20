package org.flower.workflow.team;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Team name cannot be empty")
    private String name;

    @NotBlank(message = "Leader cannot be empty")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_leader")
    private User leader;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "id_team"), inverseJoinColumns = @JoinColumn(name = "id_team_role"))
    @MapKeyJoinColumn(name = "id_member")
    private Map<User, TeamRole> members = new HashMap<>();

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

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public void addMember(User member, TeamRole role) {
        members.put(member, role);
    }

    public TeamRole getMemberRole(User member) {
        return member == null ? null : members.get(member);
    }

    public boolean isMember(User member) {
        return members.containsKey(member);
    }
}
