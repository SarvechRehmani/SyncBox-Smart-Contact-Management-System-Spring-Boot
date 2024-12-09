package com.syncbox.models.requests;

import com.syncbox.helper.AppConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    @NotBlank(message = "Name is required.")
    private String name;
    @Email(regexp = AppConstants.EMAIL_REGEX ,message = "Invalid Email Address.")
    private String email;
    @NotBlank(message = "Password is required.")
    @Size(min = 6,message = "Min 6 character is required.")
    private String password;
    private String confirmPassword;
    private String about;
    @Size(min = 8, max = 12, message = "Invalid Phone Number.")
    private String phoneNumber;
    private String profilePic = "Default.png";
}
