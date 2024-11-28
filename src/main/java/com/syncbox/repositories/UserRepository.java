package com.syncbox.repositories;

import com.syncbox.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM User u WHERE u.userId = :id OR u.providerId = :id")
    Optional<User> findByUserIdOrProviderId(String id);

//    this is also working fine but, I prefer above method because of custom query
//    Optional<User> findByUserIdOrProviderId(String userId, String providerId);
}
