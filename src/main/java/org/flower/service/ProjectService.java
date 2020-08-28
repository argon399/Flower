package org.flower.service;

import org.flower.project.team.Team;
import org.flower.repository.ProjectRepository;
import org.flower.project.Project;
import org.flower.project.team.User;
import org.flower.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<Project> loadByMember(User user) {
        List<Project> allProject = projectRepository.findAll();

        return allProject.stream()
                .filter(project -> project.getTeam().isMember(user))
                .collect(Collectors.toList());
    }

    public void addProject(Project project) {
        Team team = new Team();
        team.setLeader(project.getOwner());

        teamRepository.save(team);

        project.setTeam(team);

        projectRepository.save(project);
    }

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public void delProject(Project project) {
        projectRepository.delete(project);

        if (project.getTeam() != null) {
            teamRepository.delete(project.getTeam());
        }
    }
}
