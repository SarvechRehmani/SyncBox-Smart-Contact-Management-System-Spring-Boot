package com.syncbox.helper.handler;

import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        HttpSession session = request.getSession();

        if (authentication != null) {
            // Set a success message in the session
            session.setAttribute("message", new Message("You have successfully logged out!", MessageType.green));
            logger.info("User {} has successfully logged out.", authentication.getName());
        } else {
            session.setAttribute("message", new Message("You are not signed in, so logout is not applicable.", MessageType.red));
            logger.warn("Logout attempt without an authenticated user.");
        }
        // Redirect to sign-in page
        response.sendRedirect("/sign-in");
    }
}
