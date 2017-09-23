package com.yunzhou.libcommon.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by huayunzhou on 2017/9/23.
 */

public class RegexUtils {

    //e-mail 正则表达式
    public final static String REG_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    //手机号 正则表达式
    public final static String REG_PHONE = "^1\\d{10}$";
    //IP 正则表达式
    public final static String REG_IP = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
    //网络路径 正则表达式
    public final static String REG_URL = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[[^\\s]| ]+";
    //用户合法输入 正则表达式（中文/英文。数字）
    public final static  String REG_VALID_INPUT = "([\u4e00-\u9fa5A-Za-z0-9])+";

    /**
     * 正则匹配
     * @param source    数据源
     * @param regex     正则表达式
     * @return          是否匹配
     */
    public static boolean match(String source, String regex){
        if(TextUtils.isEmpty(source) || TextUtils.isEmpty(regex)){
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(source).matches();
    }


    /**
     * 是否是Email
     * @param email email
     * @return  true:匹配；false:不匹配
     */
    public static boolean isEmail(String email){
        return match(email, REG_EMAIL);
    }

    /**
     * 是否是手机号
     * @param phone 手机号
     * @return  true:匹配；false:不匹配
     */
    public static boolean isPhone(String phone){
        return match(phone, REG_PHONE);
    }

    /**
     * 是否是IP地址
     * @param ip    IP地址
     * @return  true:匹配；false:不匹配
     */
    public static boolean isIP(String ip){
        return match(ip, REG_IP);
    }


    /**
     * 是否是网络路径
     * @param url   url
     * @return  true:匹配；false:不匹配
     */
    public static boolean isUrl(String url){
        return match(url, REG_URL);
    }


    /**
     * 是否是用户正确输入(中文/英文/数字)
     * @param input 输入
     * @return  true:匹配；false:不匹配
     */
    public static boolean isValidInput(String input){
        return match(input, REG_VALID_INPUT);
    }
}
