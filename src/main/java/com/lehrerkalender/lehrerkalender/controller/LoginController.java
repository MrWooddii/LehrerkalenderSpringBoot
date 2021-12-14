package com.lehrerkalender.lehrerkalender.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @GetMapping("/logout")
    public String loggedOut(Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        //Auf der Login-Seite wird dadurch die "Logout-Message" eingeblendet
        model.addAttribute("loggedOut", true);
        return "login-page";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }

}
