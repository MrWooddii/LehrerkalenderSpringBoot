package com.lehrerkalender.lehrerkalender.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/landing-page")
    public String showLandingPage(Model model) {
        model.addAttribute("appName", appName);
        return "landing-page";
    }
}
