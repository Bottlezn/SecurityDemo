package com.example.wdh.securitydemo.utils;

import android.util.Base64;

/**
 * Created by wdh on 2016/9/21.
 * 字符串的base64编码解码工具类
 */

public final class Base64Util {

    /**
     * 将字节数组编码成Base字符串
     *
     * @param bytes
     * @return
     */
    public static String data2Str(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 将Base64字节数组解码成字符串
     *
     * @param bytes
     * @return
     */
    public static String decodeData2Str(byte[] bytes) {
        return new String(Base64.decode(bytes, Base64.DEFAULT));
    }


}
