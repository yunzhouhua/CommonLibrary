package com.yunzhou.libcommon.net.http.config;

import com.yunzhou.libcommon.net.http.cookie.annotation.CookieType;
import com.yunzhou.libcommon.net.http.ssl.SSLParams;

/**
 * Http/Https请求，配置
 * Created by huayunzhou on 2017/9/26.
 */
public class HttpConfig {

    private static final long DEFAULT_TIME_OUT_MILLISECONDS = 10000L;

    /**
     * 读超时时间
     */
    private long readTimeOut;
    /**
     * 写超时时间
     */
    private long writeTimeOut;
    /**
     * 链接超时时间
     */
    private long connectTimeOut;

    /**
     * cookie类型
     */
    @CookieType
    private int cookieType;

    /**
     * https验证证书相关
     */
    private SSLParams sslParams;


    public HttpConfig(){
        cookieType = CookieType.FILE;
    }

    public static HttpConfig getDefaultConfig(){
        HttpConfig config = new HttpConfig();
        config.readTimeOut = DEFAULT_TIME_OUT_MILLISECONDS;
        config.writeTimeOut = DEFAULT_TIME_OUT_MILLISECONDS;
        config.connectTimeOut = DEFAULT_TIME_OUT_MILLISECONDS;
        return config;
    }

    public long getReadTimeOut() {
        if(readTimeOut < 0){
            return DEFAULT_TIME_OUT_MILLISECONDS;
        }
        return readTimeOut;
    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public long getWriteTimeOut() {
        if(writeTimeOut < 0){
            return DEFAULT_TIME_OUT_MILLISECONDS;
        }
        return writeTimeOut;
    }

    public void setWriteTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public long getConnectTimeOut() {
        if(connectTimeOut < 0){
            return DEFAULT_TIME_OUT_MILLISECONDS;
        }
        return connectTimeOut;
    }

    public void setConnectTimeOut(long connectionTimeOut) {
        this.connectTimeOut = connectionTimeOut;
    }

    @CookieType
    public int getCookieType() {
        return cookieType;
    }

    public void setCookieType(int cookieType) {
        this.cookieType = cookieType;
    }

    public SSLParams getSslParams() {
        return sslParams;
    }

    public void setSslParams(SSLParams sslParams) {
        this.sslParams = sslParams;
    }
}
