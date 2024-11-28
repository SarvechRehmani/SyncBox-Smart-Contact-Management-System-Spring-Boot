package com.syncbox.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class AuthenticatedUserHelper {

    public static String getUserDtoResponse(Authentication authentication) {
        // Implement logic to get username from the authenticated user
        String id = "";
        String name = "User";
        String email = "";
        if(authentication instanceof OAuth2AuthenticationToken oAuth2AuthenticationToken) {
            var clientId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oAuth2User = (OAuth2User) authentication.getPrincipal();
            id = oAuth2User.getName();
            name = oAuth2User.getAttribute("name");
            if(clientId.equalsIgnoreCase("google")){
                email = oAuth2User.getAttribute("email");
            }else if(clientId.equalsIgnoreCase("github")){
                email = (oAuth2User.getAttribute("email") != null) ?
                        oAuth2User.getAttribute("email") :
                        oAuth2User.getAttribute("login") + "@github.com";
            }else{
                // Handle other OAuth2 providers here

            }
        }else{
//            Self registered user

            return "";
        }
        return "username";
    }

}

