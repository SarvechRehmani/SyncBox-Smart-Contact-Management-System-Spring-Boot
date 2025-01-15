package com.syncbox.services;

import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    Contact saveContact(Contact contact);

    void updateContact(Contact contact);


//    Delete operation
    void deleteContactById(String id);

    void deleteContactsByUser(User user);

//    Getting Operations
    Contact getContactById(String id);

    Contact getContactByUserAndId(User user,String id);

    boolean deleteContactByUserAndId(User user,String id);

    List<Contact> getContactsByUser(User user);

    Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String sortDirection);

    List<Contact> getContactsByUser(String userId);

    Page<Contact> searchContactsByUser(User user, String query, int page, int size, String sortBy, String sortDirection);


}
