package com.syncbox.models.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String about;
    private String phoneNumber;
    private String profilePic = "Default.png";
}
