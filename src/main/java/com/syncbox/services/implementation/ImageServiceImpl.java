package com.syncbox.services.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.syncbox.controllers.ContactController;
import com.syncbox.helper.AppConstants;
import com.syncbox.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String fileName, String type) {

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            if(type.equalsIgnoreCase("contacts")){
                cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", fileName.substring(AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER.length()),
                        "tags", "contact_images",
                        "folder", "contact_images"
                ));
            }else if(type.equalsIgnoreCase("users")){
                cloudinary.uploader().upload(data, ObjectUtils.asMap(
                        "public_id", fileName.substring(AppConstants.CLOUDINARY_USER_PROFILE_IMAGE_FOLDER.length()),
                        "tags", "user_profiles",
                        "folder", "user_profiles"
                ));
            }
            return this.getUrlFromPublicId(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url().transformation(
                new Transformation<>()
                        .width(AppConstants.CONTACT_IMAGE_WIDTH)
                        .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                        .crop(AppConstants.CONTACT_IMAGE_CROP)
        ).generate(publicId);
    }

    @Override
    public boolean deleteOldImage(String publicId) {
        if (publicId == null || publicId.isEmpty()) {
            logger.error("Cannot delete image: publicId is null or empty.");
            return false;
        }
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            logger.info("Successfully deleted image with publicId: {}", publicId);
            return true;
        } catch (IOException e) {
            logger.error("Error deleting image with publicId {}: {}", publicId, e.getMessage());
            return false;
        }
    }

}
