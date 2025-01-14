package com.syncbox.helper;

import com.syncbox.models.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class AuthenticatedUserHelper {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatedUserHelper.class);

    public static String getAuthenticatedEmail(Authentication authentication) {
        // Implement logic to get email from the authenticated user
        if (authentication instanceof OAuth2AuthenticationToken oAuth2Token) {
            var clientId = oAuth2Token.getAuthorizedClientRegistrationId();
            var oAuth2User = (OAuth2User) authentication.getPrincipal();
            if (clientId.equalsIgnoreCase("google")) {
                logger.info("Getting email from google authenticated.");
                return oAuth2User.getAttribute("email");
            } else if (clientId.equalsIgnoreCase("github")) {
                logger.info("Getting email from github authenticated.");
                return (oAuth2User.getAttribute("email") != null) ?
                        oAuth2User.getAttribute("email") :
                        oAuth2User.getAttribute("login") + "@github.com";
            } else {
                // Handle other OAuth2 providers here
                return "";
            }
        } else {
//            Self registered user
            return ((User) authentication.getPrincipal()).getEmail();
        }
    }
}

