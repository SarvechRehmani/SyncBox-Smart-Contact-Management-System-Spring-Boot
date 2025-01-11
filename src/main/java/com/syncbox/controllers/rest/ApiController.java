package com.syncbox.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import com.syncbox.models.response.ContactResponseDto;
import com.syncbox.services.ContactService;
import com.syncbox.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final Logger logger = LoggerFactory.getLogger(ApiController.class);

    private final ContactService contactService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;


    public ApiController(ContactService contactService, UserService userService, ModelMapper modelMapper, ObjectMapper objectMapper){
        this.contactService = contactService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponseDto> getContactById(@PathVariable String id, Authentication authentication){

        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = userService.getUserByEmail(email);

        Contact contact = this.contactService.getContactByUserAndId(user,id);
        ContactResponseDto contactResponseDto = this.objectMapper.convertValue(contact, ContactResponseDto.class);

        return ResponseEntity.ok(contactResponseDto);
    }

    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable String id, Authentication authentication, HttpSession session){
        this.logger.info("Deleting contact controller");
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        User user = userService.getUserByEmail(email);
        boolean flag = contactService.deleteContactByUserAndId(user,id);
        session.setAttribute("message", new Message("The contact has been successfully deleted.", MessageType.purple));
        return ResponseEntity.ok(flag);
    }


}
