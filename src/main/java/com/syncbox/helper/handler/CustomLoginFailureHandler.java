package com.syncbox.helper.handler;

import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.entities.User;
import com.syncbox.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    private final UserService userService;

    private final BCryptPasswordEncoder passwordEncoder;

    public CustomLoginFailureHandler(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        System.out.println(request.getParameter("email"));
        User user = this.userService.getUserByEmail(request.getParameter("email"));

        if (exception instanceof DisabledException && passwordEncoder.matches(request.getParameter("password"), user.getPassword())) {
            session.setAttribute("message", new Message("Your account is disabled. Please contact your administrator for assistance.", MessageType.red));
        } else {
            session.setAttribute("message", new Message("Oops! Login failed. Please check your email and password, then try again.", MessageType.red));
        }
        response.sendRedirect("/sign-in");
    }
}
