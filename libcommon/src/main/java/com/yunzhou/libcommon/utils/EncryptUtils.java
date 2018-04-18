package com.yunzhou.libcommon.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * Created by huayunzhou on 2017/9/23.
 */
@SuppressWarnings("unused")
public class EncryptUtils {


    //****************************  md5  ******************************
    /**
     * 32位 md5加密
     * @param data  原始数据
     * @return  加密后的数据
     */
    public final static String md5Encode(String data) {
        if (data == null) {
            return "";
        }
        try {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }finally {
                md.reset();
            }
            byte[] results = md.digest(data.getBytes("UTF-16LE"));
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < results.length; i++) {
                if ((results[i] & 0xff) < 0x10) {
                    buf.append("0");
                }
                buf.append(Long.toString(results[i] & 0xff, 16));
            }
            return buf.toString().toUpperCase();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return data.toUpperCase();
    }

    // *******************Base64 加密解密 *******************

    /**
     * base64加密
     */
    public static byte[] encodeBase64(byte[] input) {
        return Base64.encode(input, Base64.DEFAULT);
    }

    /**
     * base64加密
     */
    public static String encodeBase64(String s) {
        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64解码
     */
    public static byte[] decodeBase64(byte[] input) {
        return Base64.decode(input, Base64.DEFAULT);
    }

    /**
     * base64解码
     */
    public static String decodeBase64(String s) {
        return new String(Base64.decode(s, Base64.DEFAULT));
    }
}
