package com.syncbox.repositories;

import com.syncbox.models.entities.Contact;
import com.syncbox.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

//    ------ Custom Finder Method  ------
    Optional<Contact> findByUserAndContactId(User user, String id);
    List<Contact> findByUser(User user);
    Page<Contact> findByUser(User user, Pageable pageable);
    List<Contact> findByUserAndNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase
            (User user, String nameQuery, String emailQuery, String phoneNumberQuery);



    //   ------ Custom Query ------
    @Modifying
    @Query("DELETE FROM Contact c WHERE c.user = :user AND c.contactId = :id")
    void deleteByUserAndContactId(@Param("user") User user, @Param("id") String id);

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
