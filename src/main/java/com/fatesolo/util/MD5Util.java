package com.fatesolo.util;

import java.security.MessageDigest;

//MD5加密工具类
public class MD5Util {

    public static byte[] encode2bytes(String source) {
        byte[] result = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String encode2hex(String source) {
        byte[] data = encode2bytes(source);

        StringBuilder hexString = new StringBuilder();
        for (byte aData : data) {
            String hex = Integer.toHexString(0xff & aData);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
    }

    /*
     * @param unknown : 明文字符串
     * @param okHex : 加密后的32位字符串
     */
    public static boolean validate(String unknown, String okHex) {
        return okHex.equals(encode2hex(unknown));
    }

}
