package com.syncbox.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syncbox.helper.AuthenticatedUserHelper;
import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import com.syncbox.models.response.ContactResponseDto;
import com.syncbox.services.ContactService;
import com.syncbox.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

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
    public ResponseEntity<?> deleteContact(@PathVariable String id, Authentication authentication){
        System.out.println("hello 1");
        String email = AuthenticatedUserHelper.getAuthenticatedEmail(authentication);
        System.out.println("2");
        User user = userService.getUserByEmail(email);
        System.out.println("3");
        System.out.println("id is :"+ id);
        contactService.deleteContactByUserAndId(user,id);
        System.out.println("4");
        return ResponseEntity.ok("Contact deleted successfully.");
    }
}
