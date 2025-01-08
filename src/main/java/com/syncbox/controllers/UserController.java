package com.syncbox.controllers;

import com.syncbox.helper.AppConstants;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.ContactDto;
import com.syncbox.models.requests.ResetPassword;
import com.syncbox.models.requests.UserProfilePictureRequest;
import com.syncbox.models.response.UserResponseDto;
import com.syncbox.services.ImageService;
import com.syncbox.services.UserService;
import com.syncbox.validators.ValidImage;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    // User Dashboard
    @GetMapping("/dashboard")
    public String userDashboard(Authentication authentication) {
        // System.out.println("User Dashboard : user : "+email);
        return "user/dashboard";
    }

//    // User Profile
    @GetMapping("profile")
    public String userProfile(Model model) {
        model.addAttribute("request", new UserProfilePictureRequest());
        System.out.println("User Profile");
        return "user/profile";
    }

//    Update Profile picture
    @PostMapping(value = "/update-picture")
    public String updateProfilePicture(@Valid @ModelAttribute("request") UserProfilePictureRequest request, BindingResult result, HttpSession session, Authentication authentication){
        if (result.hasErrors()){
            return "user/profile";
        }
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User authenticateUser = this.userService.getUserByEmail(email);
        String oldCloudinaryPublicId = authenticateUser.getCloudinaryPublicId();
        if(request.getNewProfilePicture().isEmpty()){
            session.setAttribute("message", new Message("Please select a picture.", MessageType.yellow));
            return "redirect:/user/profile";
        }else{
            String fileName = AppConstants.CLOUDINARY_USER_PROFILE_IMAGE_FOLDER + UUID.randomUUID().toString();
            String imageUrl = this.imageService.uploadImage(request.getNewProfilePicture(), fileName,"users");
            authenticateUser.setCloudinaryPublicId(fileName);
            authenticateUser.setProfilePic(imageUrl);
        }
        this.userService.updateUserAccountDetails(authenticateUser);
        if(!oldCloudinaryPublicId.equals(AppConstants.DEFAULT_CLOUDINARY_PUBLIC_ID_FOR_USER)){
            this.imageService.deleteOldImage(oldCloudinaryPublicId);
            this.logger.info("Old profile image has been deleted successfully..");
        }
        session.setAttribute("message", new Message("Profile picture updated successfully.", MessageType.green));
        return "redirect:/user/profile";
    }

//    Update Profile Process
    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute UserResponseDto user, HttpSession session, Authentication authentication){
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User authenticateUser = this.userService.getUserByEmail(email);
        authenticateUser.setName(user.getName());
        authenticateUser.setPhoneNumber(user.getPhoneNumber());
        authenticateUser.setAddress(user.getAddress());
        authenticateUser.setAbout(user.getAbout());
        this.userService.updateUserAccountDetails(authenticateUser);
        session.setAttribute("message", new Message("Profile updated successfully.", MessageType.green));
        return "redirect:/user/profile";
    }

//    Reset Password View
    @GetMapping("/reset-password")
    public  String resetPassword(Model model) {
        System.out.println("Reset Password page.");
        model.addAttribute("resetPassword", new ResetPassword());
        return "/user/reset_password";
    }
//    Reset Password Process
    @PostMapping("/reset-password")
    public String resetPassword(@Valid @ModelAttribute ResetPassword resetPassword, BindingResult result, HttpSession session, Authentication authentication){
        if(result.hasErrors()){
            return "/user/reset_password";
        }
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        if(!this.passwordEncoder.matches(resetPassword.getCurrentPassword(), user.getPassword())){
            result.rejectValue("currentPassword", "error.user", "Passwords is not correct.");
            return "/user/reset_password";
        } else if(!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())){
            result.rejectValue("confirmPassword", "error.user", "Passwords do not match.");
            return "/user/reset_password";
        }else{
            user.setPassword(this.passwordEncoder.encode(resetPassword.getPassword()));
            this.userService.updateUserAccountDetails(user);
            session.setAttribute("message", new Message("Password updated successfully.", MessageType.green));
        }
        return "redirect:/user/profile";
    }

//    Add Account Details like Date of Birth and Gender
    @PostMapping("/add/account-details")
    public String addAccountDetails(@ModelAttribute UserResponseDto user, HttpSession session, Authentication authentication){
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User authenticateUser = this.userService.getUserByEmail(email);
        if(authenticateUser!= null){
            authenticateUser.setDob(user.getDob());
            authenticateUser.setGender(user.getGender());
            this.userService.updateUserAccountDetails(authenticateUser);
            session.setAttribute("message",new Message("Account details updated successfully.", MessageType.green));
            return "redirect:/user/profile";
        }else{
            this.logger.error("User not found.");
            session.setAttribute("message",new Message("User not found.", MessageType.red));
            return "redirect:/logout";
        }
    }

}
