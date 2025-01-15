package com.syncbox.helper;

public class AppConstants {
    public static final String APP_NAME = "SyncBox";
    public static final String DOMAIN_NAME_URL = "http://localhost:8080";

    public static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static final long USER_ROLE_ID = 101;
    public static final long ADMIN_ROLE_ID = 102;

    public static final int CONTACT_IMAGE_WIDTH = 500;
    public static final int CONTACT_IMAGE_HEIGHT = 500;
    public static final String CONTACT_IMAGE_CROP = "fill";

    public  static final String CLOUDINARY_USER_PROFILE_IMAGE_FOLDER = "user_profiles/";
    public static  final String DEFAULT_PROFILE_PICTURE_FOR_USER = "https://res.cloudinary.com/dgjfrwadk/image/upload/v1734871228/Default_profile_pic_cqkpah";
    public static final String DEFAULT_CLOUDINARY_PUBLIC_ID_FOR_USER = "Default_profile_pic_cqkpah";

    public  static final String CLOUDINARY_CONTACT_IMAGE_FOLDER = "contact_images/";
    public static final String DEFAULT_CONTACT_IMG = "https://res.cloudinary.com/dgjfrwadk/image/upload/v1734871228/Default_eqbncs.png";
    public static final String DEFAULT_CLOUDINARY_PUBLIC_ID_FOR_CONTACT = "Default_eqbncs";


    public static  final String PAGE_SIZE = "5";

}
