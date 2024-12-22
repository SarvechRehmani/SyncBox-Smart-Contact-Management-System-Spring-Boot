package com.syncbox.models.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactResponseDto {

    private String contactId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String cloudinaryPublicId;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
}
