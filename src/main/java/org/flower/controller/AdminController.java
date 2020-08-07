package org.flower.controller;

import org.flower.service.UserService;
import org.flower.project.team.User;
import org.flower.project.team.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String user(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin_user";
    }

    @GetMapping("/user/{user}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userEditForm(
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", UserRole.values());
        return "admin_user_edit";
    }

    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String userSave(@ModelAttribute User userData,
            @RequestParam("id") User user
    ) {
        userService.saveUser(user, userData);

        return "redirect:/admin/user";
    }
}
