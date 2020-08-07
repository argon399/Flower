package org.flower.controller;

import org.flower.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/password-restore")
public class PasswordRestoreController {
    @Autowired
    UserService userService;

    @GetMapping
    public String passwordRestore(Model model) {
        return "/password-restore";
    }

    @PostMapping
    public String createNewPassword(Model model, @RequestParam("username") String username) {
        boolean isEmpty = StringUtils.isEmpty(username);
        if (isEmpty) {
            model.addAttribute("usernameError","Username cannot be empty");
            return "/password-restore";
        }

        if (!userService.createNewPassword(username)) {
            model.addAttribute("usernameError","Username not found");
            return "/password-restore";
        }

        return "/password-send";
    }

    @GetMapping("/activate/{code}")
    public String activateNewPassword(Model model, @PathVariable String code) {
        boolean isActivate = userService.activateNewPassword(code);

        if(isActivate) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "New password successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }
}
