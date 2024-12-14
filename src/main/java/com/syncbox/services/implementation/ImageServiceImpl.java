package com.syncbox.services.implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.syncbox.helper.AppConstants;
import com.syncbox.services.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage, String fileName) {

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", fileName.substring(AppConstants.CLOUDINARY_CONTACT_IMAGE_FOLDER.length()),
                    "tags", "contact_images",
                    "folder", "contact_images"
            ));
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


}