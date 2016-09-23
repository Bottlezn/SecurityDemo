package com.example.wdh.securitydemo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wdh on 2016/9/23.
 * MD5工具类
 */

public final class MD5Util {


    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuilder strBuilder = new StringBuilder();
            for (byte b : encryption) {
                if (Integer.toHexString(0xff & b).length() == 1) {
                    strBuilder.append("0").append(Integer.toHexString(0xff & b));
                } else {
                    strBuilder.append(Integer.toHexString(0xff & b));
                }
            }
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}
