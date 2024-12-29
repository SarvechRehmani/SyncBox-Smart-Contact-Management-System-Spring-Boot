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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    //    Add Contact View
    @GetMapping("/add")
    public String addContactView(Model model) {
        ContactDto contactDto = new ContactDto();
        model.addAttribute("contactDto", contactDto);
        this.logger.info("Add Contact View.");
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
        this.logger.info("Converting DTO to Entity");
//      Converting Dto to Entity
        Contact contact = this.modelMapper.map(contactDto, Contact.class);
        this.logger.info("Getting Authenticated User from Authentication");
//      get authenticated user
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        contact.setUser(user);
       if(!contactDto.getProfileImage().isEmpty()){
           String fileName = AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER + UUID.randomUUID().toString();
           String imageUrl = this.imageService.uploadImage(contactDto.getProfileImage(), fileName);
           contact.setCloudinaryPublicId(fileName);
           contact.setPicture(imageUrl);
       }else{
           contact.setCloudinaryPublicId(AppConstants.DEFAULT_CLOUDINARY_PUBLIC_ID);
           contact.setPicture(AppConstants.DEFAULT_CONTACT_IMG);
       }
        this.logger.info("Saving Contact to Database");
        // Save contact to database
        this.contactService.saveContact(contact);
        session.setAttribute("message",new Message("You have successfully added a new contact", MessageType.purple));
        return "redirect:/user/contacts/add";
    }

    //  Contact List View with user and pagination
    @GetMapping
    public String contactListView(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model, Authentication authentication) {
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        model.addAttribute("pageContacts", this.contactService.getContactsByUser(user,page,size,sortBy,direction));
        this.logger.info("Contact List View.");
        return "user/contacts";
    }

//  Mark Contact As Favourite handler
    @GetMapping("/update-favorite/{id}")
    public ResponseEntity<?> markContactAsFavorite(@PathVariable String id) {
        this.logger.info("Mark Contact As Favorite Handler.");
        Map<String, Object> response = new HashMap<>();
        if(id == null) {
            response.put("message","Id is null. Please provide correct Id");
            return ResponseEntity.badRequest().body(response);
        }
        this.logger.info("Fetching contact by ID");
        Contact contact = this.contactService.getContactById(id);
        if(contact == null) {
            response.put("message", "Contact not found with this id:"+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        this.logger.info("Toggle favourite contact");
        contact.setFavorite(!contact.isFavorite());
        // Update logic here
        this.logger.info("Updating contact in database");
        contactService.updateContact(contact);
        response.put("message", "Favorite status updated successfully");
        response.put("favorite", contact.isFavorite()); // Optional: include new status
        return ResponseEntity.ok(response); // Return JSON
    }

//    Edit contact View
    @GetMapping("/edit/{id}")
    public String editContactView(@PathVariable String id ,Model model,  Authentication authentication){
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        ContactDto contact = this.modelMapper.map(this.contactService.getContactByUserAndId(user,id), ContactDto.class);
        model.addAttribute("contactDto", contact);
        return "user/editContact";
    }

//  Edit contact process
    @PostMapping("/edit")
    public String editContact(@Valid @ModelAttribute ContactDto contactDto, HttpSession session, Authentication authentication){
        Contact contact = this.contactService.getContactById(contactDto.getContactId());
        String oldPicture = contact.getPicture();
        String oldCloudinaryPublicId  = contact.getCloudinaryPublicId();
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = this.userService.getUserByEmail(email);
        if(contact.getUser().getUserId().equals(user.getUserId())){
            Contact contact1 = mapFieldFromContactDTO(contact, contactDto);
//            Contact contact1 = this.modelMapper.map(contactDto, Contact.class);
            System.out.println("user ID matched");
//            process new profile picture
            if(!contactDto.getProfileImage().isEmpty()){
//                Upload new profile picture
                String fileName = AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER + UUID.randomUUID().toString();
                String imageUrl = this.imageService.uploadImage(contactDto.getProfileImage(), fileName);
                contact1.setCloudinaryPublicId(fileName);
                contact1.setPicture(imageUrl);
                this.logger.info("New Profile Picture is uploaded.");
//                Delete old profile picture
                if (oldPicture != null && !oldPicture.isEmpty() && !oldPicture.equals(AppConstants.DEFAULT_CONTACT_IMG)) {
                    boolean isDeleted = this.imageService.deleteOldImage(oldCloudinaryPublicId);
                    if (isDeleted) {
                        this.logger.info("Old profile picture successfully deleted.");
                    } else {
                        this.logger.error("Failed to delete old profile picture.");
                    }
                }
            }
//            System.out.println(contact1);
            this.contactService.updateContact(contact1);
            session.setAttribute("message", new Message("You have successfully updated the "+contact1.getName()+" contact.", MessageType.purple));
        }else{
            session.setAttribute("message", new Message("You are not authorized to edit this contact.", MessageType.red));
        }
        return "redirect:/user/contacts";
    }



    public Contact mapFieldFromContactDTO(Contact contact, ContactDto contactDto){
        contact.setName(contactDto.getName());
        contact.setEmail(contactDto.getEmail());
        contact.setPhoneNumber(contactDto.getPhoneNumber());
        contact.setAddress(contactDto.getAddress());
        contact.setDescription(contactDto.getDescription());
        contact.setFavorite(contactDto.isFavorite());
        contact.setWebsiteLink(contactDto.getWebsiteLink());
        contact.setLinkedInLink(contactDto.getLinkedInLink());
        return contact;
    }
}
