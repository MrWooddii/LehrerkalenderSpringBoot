package com.lehrerkalender.controller;

import com.lehrerkalender.entity.User;
import com.lehrerkalender.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpServletRequest request) {

        boolean uniqueUsername = userService.UsernameIsUnique(user.getUsername());
        boolean uniqueEmail = userService.mailIsUnique(user.getEmail());

        if(result.hasErrors() || !uniqueUsername || !uniqueEmail) {
            if(!uniqueUsername) {
                model.addAttribute("usernameTaken", true);
            }
            if(!uniqueEmail) {
                model.addAttribute("emailTaken", true);
            }
            System.out.println(">>>>> Error");
            return "register-form";
        }
        user.setPassword(userService.encryptPassword(user.getPassword()));
        userService.registerUser(user);

        return "home";
    }

    //Auto-Login nach der Registrierung
    //TODO: Autologin

}