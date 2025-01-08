package com.syncbox.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String uploadImage(MultipartFile contactImage, String fileName,  String type);

    public String getUrlFromPublicId(String publicId);

    public boolean deleteOldImage(String publicId);

}
