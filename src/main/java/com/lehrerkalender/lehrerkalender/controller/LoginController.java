package com.lehrerkalender.lehrerkalender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin() {
        return "login-page";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login-page";
    }

    @GetMapping("/logged-out")
    public String loggedOut(Model model) {
        model.addAttribute("loggedOut", true);
        return "login-page";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
