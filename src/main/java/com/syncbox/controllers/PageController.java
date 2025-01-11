package com.syncbox.controllers;

import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.UserDto;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class PageController {

    private final UserService userService;

    private final ModelMapper modelMapper;



    public PageController(UserService userService,ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping("/home")
    public  String index(){
        System.out.println("home Controller");
        return "redirect:/";
    }

    @RequestMapping("/")
    public  String home(){
        System.out.println("Home Controller");
        return "home";
    }

    @RequestMapping("/about")
    public  String about(){
        System.out.println("About Controller");
        return "about";
    }

    @RequestMapping("/services")
    public  String services(){
        System.out.println("About Controller");
        return "services";
    }

    @RequestMapping("/contact-us")
    public  String contactUs(){
        System.out.println("About Controller");
        return "contact";
    }



    @GetMapping("/sign-in")
    public  String login(){
        System.out.println("Login Controller");
        return "login";
    }

    @RequestMapping("/sign-up")
    public  String register(Model model){
        System.out.println("Register Controller");

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        return "register";
    }

    @PostMapping("/sign-up")
    public String register(@Valid @ModelAttribute UserDto userDto, BindingResult result, HttpSession session){
        System.out.println(userDto);
//        Validation
        if(result.hasErrors()){
            return "register";
        }

        if(!userDto.getPassword().equals(userDto.getConfirmPassword())){
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match");
            return "register";
        }

//        Converting Dto to Entity
        User user = this.modelMapper.map(userDto, User.class);
//        Save to Database
        User user2 = this.userService.saveUser(user);
        session.setAttribute("email", user2.getEmail());
        Message message = new Message("Registration Successfully.", MessageType.purple);
        session.setAttribute("message",message);
        return "redirect:/sign-up";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam(required = false) String token, String opt,HttpSession session){
        String email = (String) session.getAttribute("email");
        if(email == null || email.isEmpty()){
            return "error-page";
        }
        if(token != null){
            boolean isVerified = this.userService.verifyEmail(email, token, null);
            Message message;
            if(isVerified){
                message = new Message("Email verified successfully. You can now Sign-in", MessageType.green);
                session.removeAttribute("email");
            }else{
                message = new Message("Email verification failed.", MessageType.red);
            }
            session.setAttribute("message",message);
        }
        return "/otpVerification";
    }

    @PostMapping("/verify-email")
    public String verifyOTP(@RequestBody Map<String, String> payload, HttpSession session) {
        String otp = payload.get("otp");
        System.out.println(otp);
        String email = (String) session.getAttribute("email");
        if(email == null || email.isEmpty()){
            return "error-page";
        }
        boolean isVerified = this.userService.verifyEmail(email,null, otp);

        Message message;
        if(isVerified){
            message = new Message("Email verified successfully. You can now Sign-in", MessageType.green);
            session.removeAttribute("email");
        }else{
            message = new Message("Email verification failed.", MessageType.red);
        }
        session.setAttribute("message",message);
        return "redirect:/sign-in";
    }

}