package org.flower.service;

import org.flower.project.team.Team;
import org.flower.project.team.TeamRole;
import org.flower.project.team.User;
import org.flower.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    public void saveTeam(Team team, Map<String, String> params) {
        if (team != null) {
            for (User member : team.getMembers()) {
                if(params.containsKey("member" + member.getId())) {
                    TeamRole role = TeamRole.valueOf(params.get("member" + member.getId()));
                    team.setMemberRole(member, role);
                }
            }

            saveTeam(team);
        }
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }
}
