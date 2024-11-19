package com.syncbox.controllers;

import com.syncbox.models.entities.User;
import com.syncbox.models.requests.UserDto;
import com.syncbox.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    private ModelMapper modelMapper;

    public UserController(UserService userService,ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto){
        System.out.println(userDto);
//        Converting Dto to Entity
        User user = this.modelMapper.map(userDto, User.class);
//        Save to Database
        User user2 = this.userService.saveUser(user);
        System.out.println("Saved User: "+user2);
        return "redirect:/sign-up";
    }
}
