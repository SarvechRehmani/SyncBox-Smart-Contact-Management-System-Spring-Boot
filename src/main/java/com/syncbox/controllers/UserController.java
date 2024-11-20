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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService,ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public String register(@Valid  @ModelAttribute UserDto userDto, BindingResult bindingResult, HttpSession session){
        System.out.println(userDto);
//        Validation
        if(bindingResult.hasErrors()){
            return "/register";
        }

//        Converting Dto to Entity
        User user = this.modelMapper.map(userDto, User.class);
//        Save to Database
        User user2 = this.userService.saveUser(user);
        Message message = new Message("Registration Successfully", MessageType.purple);
        session.setAttribute("message",message);
        return "redirect:/sign-up";
    }
}
