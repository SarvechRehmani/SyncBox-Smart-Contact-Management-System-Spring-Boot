package com.syncbox.controllers;

import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.UserDto;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

//    @ModelAttribute
//    public String common(Principal principal, Model model) {
//        String username = principal.getName();
//        model.addAttribute("username", username);
//        return "dashboard";
//    }

//    User Dashboard
    @GetMapping("/dashboard")
    public String userDashboard(){
        return "user/dashboard";
    }

//    User Profile
    @GetMapping("profile")
    public String userProfile(){
        return "user/profile";
    }
}
