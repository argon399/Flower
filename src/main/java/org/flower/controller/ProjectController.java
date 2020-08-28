package org.flower.controller;

import org.flower.project.Project;
import org.flower.project.team.Team;
import org.flower.project.team.TeamRole;
import org.flower.project.team.User;
import org.flower.service.ProjectService;
import org.flower.service.TeamService;
import org.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getIndex(@AuthenticationPrincipal User user, Model model) {
        List<Project> projects = projectService.loadByMember(user);
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);
        return "project";
    }

    @GetMapping("{project}")
    public String selectProject(@AuthenticationPrincipal User user,
                                @PathVariable Project project,
                                Model model) {
        if (project != null) {
            model.addAttribute("project", project);
        }

        List<Project> projects = projectService.loadByMember(user);
        model.addAttribute("projects", projects);
        model.addAttribute("user", user);

        return "project";
    }

    @GetMapping("add")
    public String getNewProject(Model model) {
        model.addAttribute("project", new Project());
        return  "project-add";
    }

    @PostMapping("add")
    public String addProject(@AuthenticationPrincipal User user, Model model, @Valid Project project) {
        project.setOwner(user);

        projectService.addProject(project);

        return "redirect:/project";
    }

    @GetMapping("edit/{project}")
    public String editProject(@AuthenticationPrincipal User user,
                              @PathVariable Project project,
                              Model model) {
        if (project.getOwner().equals(user)) {
            model.addAttribute("project", project);
            model.addAttribute("users", project.getTeam().getMembers());
        } else {
            throw new AccessDeniedException("403");
        }
        return  "project-edit";
    }

    @PostMapping("edit/{oldProject}")
    public String saveProject(@AuthenticationPrincipal User user,
                              @PathVariable Project oldProject,
                              @Valid Project newProject,
                              Model model) {
        if (oldProject.getOwner().equals(user)) {
            oldProject.setName(newProject.getName());
            oldProject.setDescription(newProject.getDescription());
            oldProject.setOwner(newProject.getOwner());

            projectService.saveProject(oldProject);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project";
    }

    @GetMapping("delete/{project}")
    public String deleteProject(@AuthenticationPrincipal User user,
                              @PathVariable Project project,
                              Model model) {
        if (project.getOwner().equals(user)) {
            projectService.delProject(project);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project";
    }

    @GetMapping("team/{team}")
    public String manageTeam(@AuthenticationPrincipal User user,
                             @PathVariable Team team,
                             Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER || team.getMemberRole(user) == TeamRole.PRODUCT_OWNER)) {
            model.addAttribute("team", team);
            model.addAttribute("members", team.getMemberMap());
            model.addAttribute("allUsers", userService.findAll());
            model.addAttribute("allRoles", TeamRole.values());
        } else {
            throw new AccessDeniedException("403");
        }

        return "team";
    }

    @PostMapping("team")
    public String saveTeam(@AuthenticationPrincipal User user,
                           @RequestParam("id") Team team,
                           @RequestParam Map<String, String> allParams,
                           Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER || team.getMemberRole(user) == TeamRole.PRODUCT_OWNER)) {
            teamService.saveTeam(team, allParams);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project";
    }

    @GetMapping("team/{team}/add")
    public String getAddMemberForm(@AuthenticationPrincipal User user,
                                   @PathVariable Team team,
                                   Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER || team.getMemberRole(user) == TeamRole.PRODUCT_OWNER)) {
            model.addAttribute("allUsers", userService.findAll());
            model.addAttribute("allRoles", Arrays.stream(TeamRole.values()).filter(role -> role != TeamRole.PRODUCT_OWNER).collect(Collectors.toSet()));
            model.addAttribute("teamId", team.getId());
        } else {
            throw new AccessDeniedException("403");
        }

        return "team-add";
    }

    @PostMapping("team/{team}/add")
    public String addMember(@AuthenticationPrincipal User user,
                            @PathVariable Team team,
                            @RequestParam User member,
                            @RequestParam TeamRole role,
                            Model model) {
        if (team.isMember(user) && (team.getMemberRole(user) == TeamRole.LEADER || team.getMemberRole(user) == TeamRole.PRODUCT_OWNER)) {
            team.addMember(member, role);
            teamService.saveTeam(team);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project/team/" + team.getId();
    }
}
