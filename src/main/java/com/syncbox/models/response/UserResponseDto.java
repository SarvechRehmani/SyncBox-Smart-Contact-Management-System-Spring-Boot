package com.syncbox.models.response;

import com.syncbox.models.Providers;
import com.syncbox.models.entities.Role;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String about;
    private String address;
    private String gender;
    private String dob;
    private String joinDate;
    private String profilePic;
    private String cloudinaryPublicId;
    private boolean enabled;
    private boolean emailVerified;
    private boolean phoneVerified;
    private String providerId;
    private Providers provider;
    private Role role;
}
