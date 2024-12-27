package com.syncbox.models.requests;

import com.syncbox.helper.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ContactDto {

    private String contactId;
    @NotBlank(message = "Contact name is required.")
    private String name;
    @Email(regexp = AppConstants.EMAIL_REGEX , message = "Invalid email address.")
    private String email;
    @Size(min = 8, max = 12, message = "Invalid phone number.")
    private String phoneNumber;
    private String address;
    private MultipartFile profileImage;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;
}