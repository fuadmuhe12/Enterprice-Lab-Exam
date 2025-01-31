package com.kortex.aau_social_media.controllers;

import com.kortex.aau_social_media.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            Model model) {

        // currentUser now holds all the user information (UserEntity fields)
        // We'll pass it to the template as an attribute named "user"
        model.addAttribute("user", currentUser.getUser());

        // Return the Thymeleaf view name "profile"
        return "profile";
    }
}
