package com.example.wdh.securitydemo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wdh on 2016/9/23.
 * SHA加密工具类,支持SHA1,SHA256,SHA384和SHA512,调用方法可以直接引用该类的全局静态变量，
 * 也可以手动输入
 */

public final class SHAUtil {

    public static final String SHA_1 = "SHA-1";

    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";

    private static boolean isLegal(String algorithm) {
        return SHA_1.equals(algorithm) || SHA_256.equals(algorithm) || SHA_384.equals(algorithm) || SHA_512.equals(algorithm);
    }

    public static String getSpecificSHA(String val, String algorithm) throws Exception {
        if (!isLegal(algorithm)) throw new NoSuchAlgorithmException();
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update(val.getBytes("UTF-8"));
        byte[] m = md5.digest();//加密
        return getString(m);
    }

    private static String getString(byte[] buff) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buff) {
            if (Integer.toHexString(0xff & b).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xff & b));
            } else {
                sb.append(Integer.toHexString(0xff & b));
            }
        }
        return sb.toString();
    }
}
