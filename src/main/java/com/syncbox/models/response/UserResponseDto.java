package com.syncbox.models.response;

import com.syncbox.models.Providers;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {


    private String userOrProviderId;
    private String name;
    private String email;
    private String phoneNumber;
    private String about;
    private String profilePic;
    private boolean enabled;
    private boolean emailVerified;
    private boolean phoneVerified;
    private String providerId;
    private Providers provider;
}
