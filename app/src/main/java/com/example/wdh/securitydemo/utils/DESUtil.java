package com.example.wdh.securitydemo.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 注，这个算法无法在网上现有的工具验证，因为填充模式等参数不同
 *
 *
 *
 *
 * Created by wdh on 2016/9/21.
 * DES加密和解密的工具类
 * DES有三个入口，分别是key：64位，
 * date：64位为一块
 * mode：选择加密或者解密模式
 * 一切编码格式都utf-8
 * IV填充是  01020304
 * 算法是  DESede/CBC/PKCS5Padding
 *
 *
 *
 *
 */

public final class DESUtil {


    private static final String ALGORITHM = "DES";

    private final static String IVPARAMETERSPEC = "01020304";////初始化向量参数，AES 为16bytes. DES 为8bytes.
    /**
     * 加密解密的字符才密钥，用来生产一个给DES加密解密的64位key
     * 必须大于等于8位，DES的key值会自动去字符串转byte以后的数组0到7索引
     */
    private static final String KEY_SRC = "你还好吗";

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位
     * 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws java.lang.Exception
     */
    private static Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        return new javax.crypto.spec.SecretKeySpec(arrB, "DES");
    }


    /**
     * 将转换才字节数组进行加密
     *
     * @param data   字符串转换成字节数组
     * @param keyStr 密钥
     * @return 加密后的字符串
     * @throws Exception 呵呵哒，自己意会
     */
    public static String encryptStr(@NonNull byte[] data,
                                    @Nullable String keyStr) throws Exception {
        byte[] s = encryptData(data, keyStr);
        return Base64Util.data2Str(s);
    }

    /**
     * @param data   需要加密的数据
     * @param keyStr 加密密钥
     * @return 返回加密完毕的字符串
     * @throws Exception 可能抛出异常
     */
    public static byte[] encryptData(@NonNull byte[] data,
                                     @Nullable String keyStr) throws Exception {
        SecretKey secretkey = getSecureKey(keyStr);
        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
        return cipher.doFinal(data);
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

    /**
     * 解密数据成字节数组
     *
     * @param data   加密的字节数组
     * @param keyStr 密钥
     * @return 解密完毕的字节数组
     * @throws Exception
     */
    public static byte[] decryptData(@NonNull byte[] data,
                                     @Nullable String keyStr) throws Exception {

        SecretKey secretkey = getSecureKey(keyStr);
        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.DECRYPT_MODE, secretkey, iv);
        return cipher.doFinal(data);
    }

    private static SecretKey getSecureKey(String keySrc) throws Exception {
        SecretKeyFactory keyFactory;
        DESKeySpec dks;
        if (keySrc == null) {
            dks = new DESKeySpec(KEY_SRC.getBytes("UTF-8"));
        } else {
            dks = new DESKeySpec(keySrc.getBytes("UTF-8"));
        }
        keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }
}
