package com.syncbox.models.requests;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    private String currentPassword;
    @Size(min = 6, message = "Min 6 character is required.")
    private String password;
    private String confirmPassword;
}
