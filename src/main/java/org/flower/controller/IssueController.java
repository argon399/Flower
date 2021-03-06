package org.flower.controller;

import org.flower.project.Project;
import org.flower.project.Sprint;
import org.flower.project.issue.Issue;
import org.flower.project.issue.IssuePriority;
import org.flower.project.issue.IssueStatus;
import org.flower.project.issue.IssueType;
import org.flower.project.team.User;
import org.flower.repository.IssueRepository;
import org.flower.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class IssueController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("/project/{project}/issue/add")
    public String getNewIssueForm(@AuthenticationPrincipal User user,
                                  @PathVariable Project project,
                                  Model model) {
        if (checkPrivileges(project, user)) {
            model.addAttribute("issue", new Issue());
            model.addAttribute("allUsers", project.getTeam().getMembers());
            model.addAttribute("allTypes", IssueType.values());
            model.addAttribute("allStatuses", IssueStatus.values());
            model.addAttribute("allPriorities", IssuePriority.values());
        } else {
            throw new AccessDeniedException("403");
        }

        return "issue";
    }

    @PostMapping(value={"/project/{project}/issue/add", "/project/{project}/issue/{parent-issue}/add"})
    public String addNewIssue(@AuthenticationPrincipal User user,
                              @PathVariable Project project,
                              @Valid Issue issue,
                              Model model) {
        if (checkPrivileges(project, user)) {
            issue.setDateCreated(new Date());
            project.addIssue(issue);
            projectService.saveProject(project);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project/" + project.getId();
    }

    @GetMapping("/project/{project}/issue/{issue}/edit")
    public String editIssue(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            @PathVariable Issue issue,
                            Model model) {
        if (project.getOwner().equals(user) || project.getTeam().getLeader().equals(user)) {
            model.addAttribute("isEdit", true);
            model.addAttribute("issue", issue);
            model.addAttribute("allUsers", project.getTeam().getMembers());
            model.addAttribute("allTypes", IssueType.values());
            model.addAttribute("allStatuses", IssueStatus.values());
            model.addAttribute("allPriorities", IssuePriority.values());
        } else {
            throw new AccessDeniedException("403");
        }

        return "issue";
    }

    @GetMapping("/project/{project}/issue/{issue}/delete")
    public String deleteIssue(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            @PathVariable Issue issue,
                            Model model) {
        if (checkPrivileges(project, user)) {
            project.removeIssue(issue);
            projectService.saveProject(project);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project/" + project.getId();
    }

    @PostMapping("/project/{project}/issue/{issue}/save")
    public String saveIssue(@AuthenticationPrincipal User user,
                            @PathVariable Project project,
                            @Valid Issue issue,
                            Model model) {
        if (checkPrivileges(project, user)) {
            issueRepository.save(issue);
        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project/" + project.getId();
    }

    @GetMapping("/project/{project}/issue/{issue}/create-child")
    public String createChildIssue(@AuthenticationPrincipal User user,
                              @PathVariable Project project,
                              @PathVariable Issue issue,
                              Model model) {
        if (checkPrivileges(project, user)) {
            model.addAttribute("parentIssue", issue);
        } else {
            throw new AccessDeniedException("403");
        }

        return getNewIssueForm(user, project, model);
    }

    @GetMapping("/project/{project}/issue/{issue}/move")
    public String selectSprint(@AuthenticationPrincipal User user,
                                   @PathVariable Project project,
                                   @PathVariable Issue issue,
                                   Model model) {
        if (checkPrivileges(project, user)) {
            model.addAttribute("sprints", project.getSprints());
            model.addAttribute("issue", issue);
        } else {
            throw new AccessDeniedException("403");
        }

        return "issue-move";
    }

    @PostMapping("/project/{project}/issue/{issue}/move")
    public String moveToSprint(@AuthenticationPrincipal User user,
                               @PathVariable Project project,
                               @PathVariable Issue issue,
                               @RequestParam("sprint") Sprint sprint,
                               Model model) {
        if (checkPrivileges(project, user)) {
            project.moveIssue(issue, sprint);
            projectService.saveProject(project);

        } else {
            throw new AccessDeniedException("403");
        }

        return "redirect:/project/" + project.getId();
    }

    private boolean checkPrivileges(Project project, User user) {
        return project.getOwner().equals(user) || project.getTeam().getLeader().equals(user);
    }

}
