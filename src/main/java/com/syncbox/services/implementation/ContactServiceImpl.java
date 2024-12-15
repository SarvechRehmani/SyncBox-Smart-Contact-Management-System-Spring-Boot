package com.syncbox.services.implementation;

import com.syncbox.exceptions.ResourceNotFoundException;
import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import com.syncbox.repositories.ContactRepository;
import com.syncbox.services.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private String query;
    private User user;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact saveContact(Contact contact) {
        String ContactID = UUID.randomUUID().toString();
        contact.setContactId(ContactID);
        return this.contactRepository.save(contact);
    }

    @Override
    public void updateContact(Contact contact) {
        this.contactRepository.save(contact);
    }

    @Override
    public void deleteContactById(String id) {
        this.contactRepository.deleteById(id);
    }

    @Override
    public void deleteContactsByUser(User user) {

    }

    @Override
    public Contact getContactById(String id) {
        return this.contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find contact with this id:"+id));
    }

    @Override
    public List<Contact> getContactsByUser(User user) {
        return this.contactRepository.findByUser(user);
    }

    @Override
    public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.contactRepository.findByUser(user, pageable);
    }

    @Override
    public List<Contact> getContactsByUser(String userId) {
        return this.contactRepository.getContactsOfUser(userId);
    }

    @Override
    public List<Contact> searchContactsByUser(String query, User user) {
        return this.contactRepository.searchContactsByUser(query, user);
    }
}
