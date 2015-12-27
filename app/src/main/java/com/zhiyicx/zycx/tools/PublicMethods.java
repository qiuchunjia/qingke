package com.zhiyicx.zycx.tools;


public class PublicMethods {
    public static String encryptName(String username, String key) {
        try {
            return DES.encrypt1(username, key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String encryptPassWd(String userpasswd, String key) {
        try {
            return DES.encrypt1(userpasswd, key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String encryptMD5(String string) {
        try {
            return MD5.encryptMD5(string);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}
