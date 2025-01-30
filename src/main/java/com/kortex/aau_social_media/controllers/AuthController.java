package com.kortex.aau_social_media.controllers;

import com.kortex.aau_social_media.dto.UserRegistrationDTO;
import com.kortex.aau_social_media.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid UserRegistrationDTO registrationDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        String error = userService.registerUser(registrationDTO);

        if (error != null) {
            model.addAttribute("error", error);
            return "auth/register";
        }

        return "redirect:/auth/login?registered=true";
    }
}