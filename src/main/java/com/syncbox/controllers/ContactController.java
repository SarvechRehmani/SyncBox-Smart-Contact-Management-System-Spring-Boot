package com.syncbox.controllers;

import com.syncbox.helper.AppConstants;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import com.syncbox.models.requests.ContactDto;
import com.syncbox.services.ContactService;
import com.syncbox.services.ImageService;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private final ContactService contactService;

    private final UserService userService;

    private final ImageService imageService;

    private final ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    public ContactController(ModelMapper modelMapper, UserService userService, ContactService contactService,ImageService imageService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.contactService = contactService;
        this.imageService = imageService;
    }

    @GetMapping
    public String contactListView(Model model, Authentication authentication) {
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        model.addAttribute("contacts", this.contactService.getContactsByUser(user));
        return "user/contacts";
    }


    @GetMapping("/update-favorite/{id}")
    public ResponseEntity<?> markContactAsFavorite(@PathVariable String id) {
        Contact contact = this.contactService.getContactById(id);
        contact.setFavorite(!contact.isFavorite());
        // Update logic here
        contactService.updateContact(contact);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Favorite status updated successfully");
        response.put("favorite", contact.isFavorite()); // Optional: include new status
        return ResponseEntity.ok(response); // Return JSON
    }


    //    Add Contact View
    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contactDto", contactDto);
        return "user/addContact";
    }
//    Handle Add Contact Request
    @PostMapping("/add")
    public String addContact(@Valid @ModelAttribute ContactDto contactDto, BindingResult result, Authentication authentication, HttpSession session) {
//       Validation
        this.logger.info("Validate Fields of ContactDTO");
        this.logger.info(contactDto.toString());
        if(result.hasErrors()){
            session.setAttribute("message", new Message("Please correct the errors in the form and try again.", MessageType.yellow));
            return "user/addContact";
        }
//      Converting Dto to Entity
        Contact contact = this.modelMapper.map(contactDto, Contact.class);
//      get authenticated user
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        contact.setUser(user);
       if(!contactDto.getProfileImage().isEmpty()){
           String fileName = AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER + UUID.randomUUID().toString();
           String imageUrl = this.imageService.uploadImage(contactDto.getProfileImage(), fileName);
           contact.setCloudinaryPublicId(fileName);
           contact.setPicture(imageUrl);
       }
        // Save contact to database
        this.contactService.saveContact(contact);
        session.setAttribute("message",new Message("You have successfully added a new contact", MessageType.purple));
        return "redirect:/user/contacts/add";
    }



}
