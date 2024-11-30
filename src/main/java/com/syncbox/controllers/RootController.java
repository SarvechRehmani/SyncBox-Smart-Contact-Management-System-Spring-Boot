package com.syncbox.controllers;

import com.syncbox.exceptions.ResourceNotFoundException;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(RootController.class);

    public RootController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addAuthenticatedUser(Model model, Authentication authentication) {
//        Know if user is authenticated
        if(authentication == null) return;

        this.logger.info("Extracting Authenticated User's Email");
        var email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        this.logger.info("getting Authenticated User from DB.");
        var user = this.userService.getUserByEmail(email);
//        If user is not null then set password field null for security purpose.
        if(user != null) user.setPassword(null);
//        Set User in the session.
        model.addAttribute("user", user);
    }
}
