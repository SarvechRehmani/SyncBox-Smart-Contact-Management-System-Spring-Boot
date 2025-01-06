package com.syncbox.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {


    // User Dashboard
    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication) {

        // System.out.println("User Dashboard : user : "+email);
        return "user/dashboard";
    }

    // User Profile
    @GetMapping("profile")
    public String userProfile(Model model) {
        return "user/profile";
    }

    @GetMapping("/reset-password")
    public  String resetPassword(){
        System.out.println("Reset Password page.");
        return "/user/reset_password";
    }
}
