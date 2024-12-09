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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contactDto", contactDto);
        return "user/addContact";
    }

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
        String fileName = AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER + UUID.randomUUID().toString();
        String imageUrl = this.imageService.uploadImage(contactDto.getProfileImage(), fileName);
        contact.setCloudinaryPublicId(fileName);
        contact.setUser(user);
        contact.setPicture(imageUrl);
        // Save contact to database
//        this.contactService.saveContact(contact);
        System.out.println(contact);
        session.setAttribute("message",new Message("You have successfully added a new contact", MessageType.purple));
        return "redirect:/user/contacts/add";
    }



}
