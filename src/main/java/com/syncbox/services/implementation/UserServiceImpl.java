package com.syncbox.services.implementation;

import com.syncbox.exceptions.ResourceNotFoundException;
import com.syncbox.helper.AppConstants;
import com.syncbox.helper.Helper;
import com.syncbox.models.entities.Role;
import com.syncbox.models.entities.User;
import com.syncbox.repositories.UserRepository;
import com.syncbox.services.EmailService;
import com.syncbox.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public User saveUser(User user) {
        String userID = UUID.randomUUID().toString();
        Role userRole = new Role(AppConstants.USER_ROLE_ID);
        user.setUserId(userID);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(userRole);
//        Generating OTP
        String otp = Helper.generateOTP();
        user.setEmailToken(this.passwordEncoder.encode(otp));
        this.logger.info("Generated OTP: {}", otp);

        this.logger.info("Saving user to the database");
        User savedUser = this.userRepository.save(user);

        // Send verification link to user's registered email
        this.logger.info("Sending OTP to user's registered email");
        this.emailService.sendVerificationEmail(user);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        this.logger.info("Fetching user by email");
        return this.userRepository.findByEmailIgnoreCase(email).orElse(null);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = this.userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found."));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderId(user.getProviderId());

//        save to Database
        this.logger.info("Update User");
        User save = this.userRepository.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = this.userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found."));
        this.userRepository.delete(user2);
    }

    @Override
    public boolean isUserExist(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = this.userRepository.findByEmailIgnoreCase(email).orElse(null);
        return user != null;
    }

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }
}
