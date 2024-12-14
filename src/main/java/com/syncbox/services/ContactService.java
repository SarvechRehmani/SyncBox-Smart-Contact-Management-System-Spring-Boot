package com.syncbox.services;

import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;

import java.util.List;

public interface ContactService {

    Contact saveContact(Contact contact);

    void updateContact(Contact contact);


//    Delete operation
    void deleteContactById(String id);

    void deleteContactsByUser(User user);

//    Getting Operations
    Contact getContactById(String id);

    List<Contact> getContactsByUser(User user);

    List<Contact> getContactsByUser(String userId);

    List<Contact> searchContactsByUser(String query, User user);


}