package org.flower.controller;

import org.flower.repository.TeamRepository;
import org.flower.repository.UserRepository;
import org.flower.workflow.team.Team;
import org.flower.workflow.team.TeamRole;
import org.flower.workflow.team.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private static TeamRepository teamRepository;
    @Autowired
    private static UserRepository userRepository;
    @GetMapping("/")
    public String start(Model model) {

        Team team = new Team();
        User user = new User();
        user.setUsername("test");
        userRepository.save(user);
        team.addMember(user, TeamRole.LEADER);
        teamRepository.save(team);

        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }
}
