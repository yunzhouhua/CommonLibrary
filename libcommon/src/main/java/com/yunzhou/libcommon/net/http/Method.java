package com.yunzhou.libcommon.net.http;

/**
 * Created by huayunzhou on 2017/9/27.
 */

public enum Method {
    GET("GET"),
    POST("POST"),
    HEAD("HEAD"),
    PUT("PUT"),
    DELETE("DELETE"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    CONNECT("CONNECT");

    private String value;

    Method(String value){
        this.value = value;
    }

    public String value(){
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
