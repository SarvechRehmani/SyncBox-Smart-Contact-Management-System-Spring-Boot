package com.syncbox.services;

import com.syncbox.models.entities.User;

public interface EmailService {

    void sendVerificationEmail(User user);
}
