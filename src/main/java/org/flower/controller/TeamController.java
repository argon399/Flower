package org.flower.controller;

import org.flower.project.team.Team;
import org.flower.project.team.TeamRole;
import org.flower.project.team.User;
import org.flower.service.TeamService;
import org.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @GetMapping("{team}")
    public String manageTeam(@AuthenticationPrincipal User user,
                             @PathVariable Team team,
                             Model model) {
        if (team.isMember(user) && team.getMemberRole(user) == TeamRole.LEADER) {
            model.addAttribute("team", team);
            model.addAttribute("members", team.getMemberMap());
            model.addAttribute("allRoles", TeamRole.values());
        } else {
            throw new AccessDeniedException("403");
        }

        return "team";
    }

    @PostMapping
    public String saveTeam(@AuthenticationPrincipal User user,
                           @RequestParam("id") Team team,
                           @RequestParam Map<String, String> allParams,
                           Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER)) {
            teamService.saveTeam(team, allParams);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project";
    }

    @GetMapping("{team}/add")
    public String getAddMemberForm(@AuthenticationPrincipal User user,
                                   @PathVariable Team team,
                                   Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER)) {
            model.addAttribute("allUsers", userService.findAll());
            model.addAttribute("allRoles", TeamRole.values());
            model.addAttribute("teamId", team.getId());
        } else {
            return "redirect:/project";
        }

        return "team-add";
    }

    @PostMapping("{team}/add")
    public String addMember(@AuthenticationPrincipal User user,
                            @PathVariable Team team,
                            @RequestParam User member,
                            @RequestParam TeamRole role,
                            Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER)) {
            team.addMember(member, role);
            teamService.saveTeam(team);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/team/" + team.getId();
    }

    @GetMapping("{team}/delete/{member}")
    public String deleteMember(@AuthenticationPrincipal User user,
                            @PathVariable Team team,
                            @PathVariable User member,
                            Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER)) {
            team.deleteMember(member);
            teamService.saveTeam(team);
        } else {
            return "redirect:/project";
        }

        return "redirect:/team/" + team.getId();
    }
}
