package com.syncbox.services.implementation;

import com.syncbox.helper.Helper;
import com.syncbox.models.entities.User;
import com.syncbox.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Value("${spring.mail.properties.domain_name}")
    private String domainName;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationEmail(User user, String otp) {

        String content = Helper.getHTMLContent(user, otp);

//      Create the email and set required information -- with HTML
        logger.info("sending verification email");
        MimeMessage mail = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail,true,"UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Verify Account - SyncBox");
            helper.setFrom(domainName);
            helper.setText(content, true);
            this.mailSender.send(mail);
            logger.info("Verification email sent to: {}", user.getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

//        IF send simple without html template
//        SimpleMailMessage simpleMail = new SimpleMailMessage();
//        simpleMail.setSubject(subject);
//        simpleMail.setText(content);
//        simpleMail.setFrom(domainName);
//        this.mailSender.send(mail);

    }
}
