package com.yunzhou.libcommon.net.http.request;

import android.util.ArrayMap;

import java.io.File;

/**
 * Http请求参数封装
 * Get:
 *      get请求参数都是以key=value的方式拼接的字符串，使用mBasicParams即可
 * Post:
 *      Post请求有如下三种请求参数的形态
 *      1.都是基础数据类型，可以使用mBasicParams，也可以使用mMultiPart来进行存储，建议使用前者
 *      2.单一数据流，即上传单一二进制流，如文本内容，字符串等，使用mSinglePart进行存储
 *      3.基础数据与数据流复合，基础数据用mBasicParams，数据流使用mMultiPart
 * *注意：mSinglePart内容为字符串时，要求使用json格式的字符串
 * Created by huayunzhou on 2017/10/3.
 */

public class RequestParams {
    private ArrayMap<String, String> mBasicParams;
    private Object mSinglePart;
    private ArrayMap<String, Object> mMultiPart;

    public void addParam(String key, String value){
        getBasicParams().put(key, value);
    }

    public void addStream(File file){
        setSinglePart(file);
    }

    public void addStream(byte[] bytes){
        setSinglePart(bytes);
    }

    public void addStream(String str){
        setSinglePart(str);
    }

    public void addStream(String key, File file){
        getMultiPart().put(key, file);
    }

    public void addStream(String key, byte[] bytes){
        getMultiPart().put(key, bytes);
    }

    public void addStream(String key, String str){
        getMultiPart().put(key, str);
    }



    public ArrayMap<String, String> getBasicParams() {
        if(this.mBasicParams == null){
            this.mBasicParams = new ArrayMap<>();
        }
        return this.mBasicParams;
    }

    public void setBasicParams(ArrayMap<String, String> mBasicParams) {
        this.mBasicParams = mBasicParams;
    }

    public Object getSinglePart() {
        return this.mSinglePart;
    }

    public void setSinglePart(Object mSinglePart) {
        if(this.mSinglePart != null){
            //单一数据流只可以被设置一次
            throw new IllegalStateException("Sorry, Single Stream has already initialized!");
        }
        this.mSinglePart = mSinglePart;
    }

    public ArrayMap<String, Object> getMultiPart() {
        if(this.mMultiPart == null){
            this.mMultiPart = new ArrayMap<>();
        }
        return this.mMultiPart;
    }

    public void setMultiPart(ArrayMap<String, Object> mMultiPart) {
        this.mMultiPart = mMultiPart;
    }
}
