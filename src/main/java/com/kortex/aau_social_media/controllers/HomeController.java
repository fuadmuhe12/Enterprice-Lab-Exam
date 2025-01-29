package com.kortex.aau_social_media.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home/home";
    }

    @GetMapping("/home/discussion")
    public String discussion() {
        return "home/discussion";
    }


}