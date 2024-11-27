package com.syncbox.helper.handler;

import com.syncbox.helper.AppConstants;
import com.syncbox.helper.Message;
import com.syncbox.helper.MessageType;
import com.syncbox.models.Providers;
import com.syncbox.models.entities.User;
import com.syncbox.repositories.UserRepository;
import com.syncbox.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    public CustomLoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        try {
            if (authentication instanceof OAuth2AuthenticationToken authToken) {
                String authProvider = authToken.getAuthorizedClientRegistrationId();
                this.logger.info("Authentication provider: {}", authProvider);
                DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authToken.getPrincipal();
                if ("google".equalsIgnoreCase(authProvider)) {
                    // Handle specific Google login logic here
                    this.logger.info("Logged in via Google");
                    this.googleAuthentication(oAuth2User);
                } else if ("github".equalsIgnoreCase(authProvider)) {
                    // Handle specific GitHub login logic here
                    this.logger.info("Logged in via GitHub");
                    this.githubAuthentication(oAuth2User);
                } else {
                    this.logger.info("Logged in via other provider");
                }
            } else {
                this.logger.info("Standard login process");
            }
        } catch (Exception e) {
            this.logger.error("Error during login success handling", e);
        }

        // Add session attributes
        HttpSession session = request.getSession();
        session.setAttribute("message", new Message("Welcome back to SyncBox! You’ve successfully logged in.", MessageType.purple));
        // Role-based redirection
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/dashboard");
        } else {
            response.sendRedirect("/user/dashboard");
        }
    }
    private void googleAuthentication(DefaultOAuth2User oAuth2User){
        this.logger.info("Extracting user from DefaultOAuth2User : {}",oAuth2User);
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String picture = oAuth2User.getAttribute("picture");
        boolean emailVerified = Boolean.TRUE.equals(oAuth2User.getAttribute("email_verified"));
        String providerId = oAuth2User.getName();
        User user = User.builder()
                .name(name)
                .email(email)
                .password(email.toLowerCase())
                .profilePic(picture)
                .provider(Providers.GOOGLE)
                .providerId(providerId)
                .emailVerified(emailVerified)
                .enabled(true)
                .about("Google account authentication was used to create this profile.")
                .build();

        this.logger.info("Check user present if not then save user to database.");
        User isPresentUser = this.userService.getUserByEmail(email).orElse(null);
        if(isPresentUser == null){
            this.logger.info("Saving user to database.");
            this.userService.saveUser(user);
            this.logger.info("USER SAVED SUCCESSFUL FROM GOOGLE");
        }
    }


    public void githubAuthentication(DefaultOAuth2User oAuth2User){
        this.logger.info("Extracting user from DefaultOAuth2User : {}",oAuth2User);
        String name = oAuth2User.getAttribute("name");
        String email = (oAuth2User.getAttribute("email")) != null ?
                oAuth2User.getAttribute("email") :
                oAuth2User.getAttribute("login") + "@github.com";
        String picture = oAuth2User.getAttribute("avatar_url");
        String providerId = oAuth2User.getAttribute("node_id");
        String about = oAuth2User.getAttribute("bio");
        User user = User.builder()
                .name(name)
                .email(email)
                .password(email.toLowerCase())
                .profilePic(picture)
                .provider(Providers.GITHUB)
                .providerId(providerId)
                .emailVerified(true)
                .enabled(true)
                .about(about)
                .build();

        this.logger.info("Check user present if not then save user to database.");
        User isPresentUser = this.userService.getUserByEmail(email).orElse(null);
        if(isPresentUser == null){
            this.logger.info("Saving user to database.");
            this.userService.saveUser(user);
            this.logger.info("USER SAVED SUCCESSFUL FROM GITHUB");
        }
    }
}
