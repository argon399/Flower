package org.flower.project.team;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_leader")
    private User leader;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "team_member", joinColumns = @JoinColumn(name = "id_team"), inverseJoinColumns = @JoinColumn(name = "id_team_role"))
//    @MapKeyJoinColumn(name = "id_member")

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "team_member", joinColumns = @JoinColumn(name = "id_team"))
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "id_team_role")
    @MapKeyJoinColumn(name = "id_member")
    private Map<User, TeamRole> members = new HashMap<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;

        members.keySet().stream()
                .filter(member -> members.get(member) == TeamRole.LEADER)
                .map(member -> members.put(member, TeamRole.DEVELOPER));

        members.put(leader, TeamRole.LEADER);
    }

    public void setOwner(User owner) {
        for (User member : members.keySet()) {
            if (members.get(member) == TeamRole.PRODUCT_OWNER)
                members.put(member, TeamRole.DEVELOPER);
        }

        members.put(owner, TeamRole.PRODUCT_OWNER);
    }

    public void setMemberRole(User member, TeamRole role) {
        TeamRole oldRole = members.get(member);

        if (oldRole == TeamRole.LEADER) {
            leader = null;
        }

        members.put(member, role);

        if (role == TeamRole.LEADER) {
            leader = member;
        }
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

    public Set<User> getMembers() {
        return members.keySet();
    }

    public Map<User, TeamRole> getMemberMap() {
        return members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id &&
                Objects.equals(leader, team.leader) &&
                members.equals(team.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leader);
    }
}
