package com.example.wdh.securitydemo.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 注，这个算法无法在网上现有的工具验证，因为填充模式等参数不同
 *
 *
 * Created by wdh on 2016/9/22.
 * 3DES加密工具类
 * 另这里没有对输入的KEY值的大小做处理，一般来说DES的key值字符串长度是24个字符串，大于24的也只取前24位
 * IV填充是  01020304
 * 算法是  DESede/CBC/PKCS5Padding
 *
 */

public final class DESedeUtil {

    private final static String ALGORITHM = "DESede";
    private final static String KEY_SRC = "ThisIsMyDESdedKeySrcassdasdasdasdsaasaasa";
    private final static String IVPARAMETERSPEC = "01020304";////初始化向量参数，AES 为16bytes. DES 为8bytes.

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

    public static byte[] decryptData(byte[] input, String keySrc) throws Exception {

        SecretKey secretkey = getSecureKey(keySrc);
        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.DECRYPT_MODE, secretkey, iv);
        return cipher.doFinal(input);
    }

    public static byte[] encryptData(byte[] input, String keySrc) throws Exception {
        SecretKey secretkey = getSecureKey(keySrc);
        IvParameterSpec iv = new IvParameterSpec(IVPARAMETERSPEC.getBytes());
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");//Android默认采用的是ECB模式不安全，更改成CBC，另可添加PADDING
        cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
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

    private static SecretKey getSecureKey(String keySrc) throws Exception {
        SecretKeyFactory keyFactory;
        DESedeKeySpec dks;
        if (keySrc == null) {
            dks = new DESedeKeySpec(KEY_SRC.getBytes("UTF-8"));
            Log.w("WTF", "KEY_SRC.getBytes(\"UTF-8\").length = " + KEY_SRC.getBytes("UTF-8").length);
        } else {
            dks = new DESedeKeySpec(keySrc.getBytes("UTF-8"));
        }
        keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);
    }

}
