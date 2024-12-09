package com.syncbox.repositories;

import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

//    Custom Finder Method
    List<Contact> findByUser(User user);
    List<Contact> findByUserAndNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase
            (User user, String nameQuery, String emailQuery, String phoneNumberQuery);


    //    Custom Query
    @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId")
    List<Contact> getContactsOfUser(String userId);
    @Query(
            "SELECT c FROM Contact c WHERE " +
            "(LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(c.phoneNumber) LIKE LOWER(CONCAT('%', :query, '%'))) AND c.user = :user"
    )
    List<Contact> searchContactsByUser(@Param("query") String query, @Param("user") User user);

}
