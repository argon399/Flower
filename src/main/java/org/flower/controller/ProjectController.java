package org.flower.controller;

import org.flower.project.Project;
import org.flower.project.Sprint;
import org.flower.project.team.User;
import org.flower.repository.SprintRepository;
import org.flower.service.ProjectService;
import org.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private SprintRepository sprintRepository;

    @GetMapping
    public String getProjectPage(@AuthenticationPrincipal User user, Model model) {
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
        model.addAttribute("backlogIssues", project.getBacklog());
        model.addAttribute("sprints", project.getSprints());

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
            model.addAttribute("users", userService.findAll());
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
            projectService.deleteProject(project);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project";
    }

    @GetMapping("{project}/sprint/add")
    public String getSprint(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            Model model) {
        if (project.getOwner().equals(user) || project.getTeam().getLeader().equals(user)) {
            model.addAttribute("sprint", new Sprint());
        } else {
            throw new AccessDeniedException("403");
        }

        return  "sprint-add";
    }

    @PostMapping("{project}/sprint/add")
    public String addSprint(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            Model model,
                            @Valid Sprint sprint) {

        sprintRepository.save(sprint);
        project.addSprint(sprint);
        projectService.saveProject(project);

        return "redirect:/project/" + project.getId();
    }

    @GetMapping("{project}/sprint/remove/{sprint}")
    public String removeSprint(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            Model model,
                            @PathVariable Sprint sprint) {

        project.removeSprint(sprint);
        sprintRepository.delete(sprint);
        projectService.saveProject(project);

        return "redirect:/project";
    }

}
