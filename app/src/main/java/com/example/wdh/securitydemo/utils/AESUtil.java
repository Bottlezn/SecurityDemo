package com.example.wdh.securitydemo.utils;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 注，这个算法无法在网上现有的工具验证，因为填充模式等参数不同
 *
 *
 * Created by wdh on 2016/9/22.
 * AES加密工具类
 * 要求密钥长度必须是128/192/256 bits.如果传入的数值长度不对，需要进行特殊处理。
 * 然而我这里没做出处理
 * 默认使用的算法是  AES/CBC/PKCS5Padding
 * IV填充是  0102030412345678
 *
 */

public final class AESUtil {

    private final static String ALGORITHM = "AES";
    private final static String KEY_SRC = "ThisIsMyDESdedKeySrcassdasdasdasdsaasaasa";
    private final static String IVPARAMETERSPEC = "0102030412345678";////初始化向量参数，AES 为16bytes. DES 为8bytes.

    /**
     * 将转换才字节数组进行加密
     *
     * @param data   字符串转换成字节数组
     * @param keyStr 密钥
     * @return 加密后的字符串
     * @throws Exception 
     */
    public static String encryptStr(@NonNull byte[] data,
                                    @Nullable String keyStr) throws Exception {
        byte[] s = encryptData(data, keyStr);
        return Base64Util.data2Str(s);
    }

    public static byte[] decryptData(byte[] input, String keySrc) throws Exception {

        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.DECRYPT_MODE, getSecureKey(keySrc), iv);
        return cipher.doFinal(input);
    }

    public static byte[] encryptData(byte[] input, String keySrc) throws Exception {

        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.ENCRYPT_MODE, getSecureKey(keySrc), iv);
        return cipher.doFinal(input);
    }

    /**
     * 解密数据成字节数组转换的字符串
     *
     * @param data   加密的字节数组
     * @param keyStr 密钥
     * @return 解密完毕的字节数组转换的字符串
     * @throws Exception
     */
    public static String decryptStr(@NonNull byte[] data,
                                    @Nullable String keyStr) throws Exception {

        return new String(decryptData(Base64.decode(data, Base64.DEFAULT), keyStr), "UTF-8");
    }

    private static SecretKeySpec getSecureKey(String keySrc) throws Exception {
        SecretKeySpec keySpec;
        if (keySrc == null) {
            keySpec = new SecretKeySpec(getRawKey(KEY_SRC.getBytes("UTF-8")), ALGORITHM);
            Log.w("WTF", "KEY_SRC.getBytes(\"UTF-8\").length = " + KEY_SRC.getBytes("UTF-8").length);
        } else {
            keySpec = new SecretKeySpec(getRawKey(keySrc.getBytes("UTF-8")), ALGORITHM);
        }
        return keySpec;
    }

    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr = null;
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.JELLY_BEAN) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        kgen.init(128, sr); // 256 bits or 128 bits,192bits
        SecretKey skey = kgen.generateKey();
        return skey.getEncoded();
    }
}
