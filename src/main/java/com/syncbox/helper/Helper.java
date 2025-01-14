package com.syncbox.helper;

import com.syncbox.models.entities.User;

public class Helper {


    public static String generateOTP(){
        int randomSixDigit = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(randomSixDigit);
    }

    public static String generateVerificationLink(String token){
        return AppConstants.DOMAIN_NAME_URL + "/verify-email?token=" + token;
    }


    public static String getHTMLContent(User user, String otp) {
        return  " <body\n" +
                "    style=\"\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      background-color: #e9ecef;\n" +
                "      font-family: 'Source Sans Pro', Helvetica, Arial, sans-serif;\n" +
                "    \"\n" +
                "  >\n" +
                "    <!-- Logo Section -->\n" +
                "    <div style=\"text-align: center; padding: 20px; background-color: #e9ecef\">\n" +
                "      <a href='"+AppConstants.DOMAIN_NAME_URL+"' style='display: inline-block; text-decoration: none'>\n" +
                "        <img\n" +
                "          src='https://res.cloudinary.com/dgjfrwadk/image/upload/v1734871228/syncbox_spdcul'" +
                "          alt=\"SyncBox Logo\"\n" +
                "          style=\"width: 48px; height: auto\"\n" +
                "        />\n" +
                "        <h1 style=\"margin: 10px 0 0; color: rgb(126, 58, 242); font-size: 24px\">\n" +
                "          SyncBox\n" +
                "        </h1>\n" +
                "      </a>\n" +
                "    </div>\n" +
                "\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        max-width: 600px;\n" +
                "        margin: 0 auto;\n" +
                "        background: #ffffff;\n" +
                "        border-radius: 10px;\n" +
                "        overflow: hidden;\n" +
                "      \"\n" +
                "    >\n" +
                "      <!-- Content Section -->\n" +
                "      <div style=\"padding: 24px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1)\">\n" +
                "        <h1\n" +
                "          style=\"\n" +
                "            margin: 0 0 16px;\n" +
                "            font-size: 28px;\n" +
                "            font-weight: 700;\n" +
                "            color: #333;\n" +
                "        >\n" +
                "          Confirm Your Email Address\n" +
                "        </h1>\n" +
                "        <p\n" +
                "          style=\"\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "            color: #555;\n" +
                "            margin: 0 0 16px;\n" +
                "          \"\n" +
                "        >\n" +
                "          Tap the button below to confirm your email address. If you didn't\n" +
                "          create an account of\n" +
                "          <a href='"+AppConstants.DOMAIN_NAME_URL+"' style='color: #6633ff; text-decoration: none'>SyncBox</a>,\n" +
                "          you can safely delete this email.\n" +
                "        </p>\n" +
                "        <!-- OTP Display Section -->\n" +
                "        <p\n" +
                "          style=\"\n" +
                "            font-size: 18px;\n" +
                "            font-weight: bold;\n" +
                "            color: #333;\n" +
                "            margin: 16px 0;\n" +
                "            text-align: center;\n" +
                "          \"\n" +
                "        >\n" +
                "          Your OTP Code: <span style=\"color: #6633ff\">" + otp + "</span>\n" +
                "        </p>\n" +
                "        <a\n" +
                "          href='"+ generateVerificationLink(user.getEmailToken()) +"'" +
                "          style=\"\n" +
                "            display: inline-block;\n" +
                "            padding: 14px 28px;\n" +
                "            font-size: 16px;\n" +
                "            color: #ffffff;\n" +
                "            background-color: #6633ff;\n" +
                "            border-radius: 6px;\n" +
                "            text-decoration: none;\n" +
                "            text-align: center;\n" +
                "            margin-bottom: 16px;\n" +
                "          \"\n" +
                "          >Click to verify</a\n" +
                "        >\n" +
                "        <p\n" +
                "          style=\"\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "            color: #555;\n" +
                "            margin: 0 0 8px;\n" +
                "          \"\n" +
                "        >\n" +
                "          If that doesn't work, copy and paste the following link in your\n" +
                "          browser:\n" +
                "        </p>\n" +
                "        <p style=\"font-size: 16px; line-height: 1.5; color: #555; margin: 0\">\n" +
                "          <a\n" +
                "            href='"+generateVerificationLink(user.getEmailToken())+"'" +
                "            style=\"color: #6633ff; text-decoration: none\"\n" +
                "            target=\"_blank\"\n" +
                "            >"+generateVerificationLink(user.getEmailToken())+"</a\n" +
                "          >\n" +
                "        </p>\n" +
                "        <p\n" +
                "          style=\"\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "            color: #555;\n" +
                "            margin: 16px 0 0;\n" +
                "          \"\n" +
                "        >\n" +
                "          "+user.getName()+",<br />SyncBox\n" +
                "        </p>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- Footer Section -->\n" +
                "    <div\n" +
                "      style=\"\n" +
                "        text-align: center;\n" +
                "        padding: 20px;\n" +
                "        font-size: 14px;\n" +
                "        color: #666;\n" +
                "        background-color: #e9ecef;\n" +
                "        margin-top: 16px;\n" +
                "      \"\n" +
                "    >\n" +
                "      <p style=\"margin: 0; line-height: 1.5\">\n" +
                "        You received this email because we received a request for register an\n" +
                "        account. If you didn't request for registration, you can safely delete\n" +
                "        this email.\n" +
                "      </p>\n" +
                "      <p style=\"margin: 12px 0 0; line-height: 1.5\">\n" +
                "        To stop receiving these emails, you can unsubscribe at any time.\n" +
                "      </p>\n" +
                "    </div>\n" +
                "  </body>\n";

    }
}
