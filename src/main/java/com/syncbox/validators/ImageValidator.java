package com.syncbox.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageValidator implements ConstraintValidator<ValidImage, MultipartFile> {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2;

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
       if(value != null){
           String extension = getFileExtension(value.getOriginalFilename());
           if (!extension.trim().isEmpty() && !ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
               context.disableDefaultConstraintViolation();
               context.buildConstraintViolationWithTemplate("Invalid image type. Allowed types: jpg, jpeg, png, gif")
                       .addConstraintViolation();
               return false;
           }

           if (value.getSize() > MAX_FILE_SIZE){
               context.disableDefaultConstraintViolation();
               context.buildConstraintViolationWithTemplate("Image size should be less than or equal to 2MB")
                       .addConstraintViolation();
               return false;
           }
       }
        return true;
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        return (lastIndexOfDot != -1) ? fileName.substring(lastIndexOfDot + 1) : "";
    }
}
