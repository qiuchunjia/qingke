package com.zhiyicx.zycx.tools;

import java.security.MessageDigest;

/**
 * @author Mr . H
 */
public class MD5 {
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static String encryptMD5(String data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data.getBytes());
        byte b[] = md5.digest();

        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        return buf.toString();
    }
}
