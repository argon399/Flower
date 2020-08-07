package org.flower.controller;

import org.flower.service.UserService;
import org.flower.project.team.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("fio",user.getFio());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String fio,
            Model model
    ) {
        userService.updateProfile(user, password, email, fio);

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email",user.getEmail());
        model.addAttribute("fio",user.getFio());
        model.addAttribute("saved", true);

        return getProfile(model, user);
    }
}