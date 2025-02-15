package com.syncbox.models.requests;

import com.syncbox.validators.ValidImage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePictureRequest {

    @ValidImage
    private MultipartFile newProfilePicture;
}
