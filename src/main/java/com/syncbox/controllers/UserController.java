package com.syncbox.controllers;

import com.syncbox.exceptions.ResourceNotFoundException;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.UserDto;
import com.syncbox.models.response.UserResponseDto;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Optional;

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
    public String userProfile() {
        return "user/profile";
    }
}
