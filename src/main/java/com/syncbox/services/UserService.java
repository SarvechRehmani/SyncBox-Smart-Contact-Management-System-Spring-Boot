package com.syncbox.services;

import com.syncbox.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);
    Optional<User> getUserById(String id);
    User getUserByEmail(String email);
    Optional<User> updateUser(User user);
    public void updateUserAccountDetails(User user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistByEmail(String email);
    List<User> getAllUser();
    boolean verifyEmail(String email, String token, String otp);
}
