package com.lehrerkalender.controller;

import com.lehrerkalender.user.User;
import com.lehrerkalender.email.EmailService;
import com.lehrerkalender.user.SecurityService;
import com.lehrerkalender.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "register-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpServletRequest request, HttpSession session) {

        boolean usernameIsTaken = userService.usernameIsTaken(user.getUsername());
        boolean emailIsTaken = userService.mailIsTaken(user.getEmail());

        if(result.hasErrors() || usernameIsTaken || emailIsTaken) {
            if(usernameIsTaken) {
                model.addAttribute("usernameTaken", true);
            }
            if(emailIsTaken) {
                model.addAttribute("emailTaken", true);
            }
            return "register-form";
        }

        String rawPassword = user.getPassword();

        userService.registerUser(user);

        //Einfache Willkommensmail an die angegebene Mail-Adresse senden
        //TODO: Mail Verifikation
        emailService.sendSimpleMessage(user.getEmail(), "Herzlich Willkommen zu deinem Lehrerkalender!", "Vielen Dank für deine Registrierung " + user.getFirstName() + "!");

        try {
            securityService.autoLogin(user.getUsername(), rawPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/user/home";
    }
}
