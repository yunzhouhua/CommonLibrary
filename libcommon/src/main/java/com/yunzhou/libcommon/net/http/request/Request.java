package com.yunzhou.libcommon.net.http.request;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.Method;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.ssl.SSLParams;

import java.io.File;
import java.util.Map;

/**
 * Created by huayunzhou on 2017/9/27.
 */

public abstract class Request<T extends Request> {

    private long id;
    private String url;
    private String finalUrl;
    private ArrayMap<String, String> headers;
    private RequestParams mRequestParams;
    private Method method;
    private SSLParams mSsl;
    private long readTimeout;
    private long writeTimeout;
    private long connectTimeout;
    private Object tag;
    private HttpConfig httpConfig;


    public Request(Method method){
        this.method = method;
        headers = new ArrayMap<>();
        mSsl = null;
        readTimeout = 0;
        writeTimeout = 0;
        connectTimeout = 0;
        httpConfig = Http.getConfig();
    }

    /**
     * 设置请求路径
     * @param url
     * @return
     */
    public T url(String url){
        this.url = url;
        return (T)this;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public void setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
    }

    //==================================== 添加请求头 ===================================
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            this.headers.put(key, value);
        }
        return (T)this;
    }
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, int value){
        if(!TextUtils.isEmpty(key)) {
            this.headers.put(key, String.valueOf(value));
        }
        return (T)this;
    }
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, long value){
        if(!TextUtils.isEmpty(key)) {
            this.headers.put(key, String.valueOf(value));
        }
        return (T)this;
    }
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, double value){
        if(!TextUtils.isEmpty(key)) {
            this.headers.put(key, String.valueOf(value));
        }
        return (T)this;
    }
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, float value){
        if(!TextUtils.isEmpty(key)){
            this.headers.put(key, String.valueOf(value));
        }
        return (T)this;
    }
    /**
     * 添加请求头
     * @param key
     * @param value
     * @return
     */
    public T addHeader(String key, boolean value){
        if(!TextUtils.isEmpty(key)) {
            this.headers.put(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求头
     * @param headers
     * @return
     */
    public T headers(Map<String, String> headers){
        if(headers != null && headers.size() > 0){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                this.headers.put(entry.getKey(), entry.getValue());
            }
        }
        //清空map
        headers.clear();
        headers = null;
        return (T)this;
    }

    /**
     * 添加请求头
     * @param headers
     * @return
     */
    public T headers(ArrayMap<String, String> headers){
        if(headers != null && headers.size() > 0){
            for(int i = 0; i < headers.size(); i++){
                this.headers.put(headers.keyAt(i), headers.valueAt(i));
            }
        }
        headers.clear();
        headers = null;
        return (T)this;
    }

    public ArrayMap<String, String> getHeaders(){
        return this.headers;
    }

    //==================================== 添加请求参数 ===================================
    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
            getRequestParams().addParam(key, value);
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, int value){
        if(!TextUtils.isEmpty(key)){
            getRequestParams().addParam(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, float value){
        if(!TextUtils.isEmpty(key)){
            getRequestParams().addParam(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, long value){
        if(!TextUtils.isEmpty(key)){
            getRequestParams().addParam(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, double value){
        if(!TextUtils.isEmpty(key)){
            getRequestParams().addParam(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param key
     * @param value
     * @return
     */
    public T addParam(String key, boolean value){
        if(!TextUtils.isEmpty(key)){
            getRequestParams().addParam(key, String.valueOf(value));
        }
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param params
     * @return
     */
    public T params(Map<String, String> params){
        if(params != null && params.size() > 0){
            for(Map.Entry<String, String> entry : params.entrySet()){
                getRequestParams().addParam(entry.getKey(), entry.getValue());
            }
        }
        //清空map
        params.clear();
        params = null;
        return (T)this;
    }

    /**
     * 添加请求参数
     * @param params
     * @return
     */
    public T params(ArrayMap<String, String> params){
        if(params != null && params.size() > 0){
            for(int i = 0; i < params.size(); i++){
                getRequestParams().addParam(params.keyAt(i), params.valueAt(i));
            }
        }
        params.clear();
        params = null;
        return (T)this;
    }

    /**
     * 设置SSL相关参数
     * @param ssl
     * @return
     */
    public T ssl(SSLParams ssl){
        this.mSsl = ssl;
        return (T)this;
    }
    public SSLParams getSSL(){
        return mSsl;
    }

    public T setWriteTimeout(long timeout){
        this.writeTimeout = timeout;
        return (T)this;
    }

    public long getWriteTimeout(boolean useDefault){
        if(writeTimeout <= 0){
            return useDefault ? httpConfig.getWriteTimeOut() : writeTimeout;
        }
        return writeTimeout;
    }

    public T setReadTimeout(long timeout){
        this.readTimeout = timeout;
        return (T)this;
    }

    public long getReadTimeout(boolean useDefault){
        if(readTimeout <= 0){
            return useDefault ? httpConfig.getReadTimeOut() : readTimeout;
        }
        return readTimeout;
    }

    public T setConnectTimeout(long timeout){
        this.connectTimeout = timeout;
        return (T)this;
    }

    public long getConnectTimeout(boolean useDefault){
        if(connectTimeout <= 0){
            return useDefault ? httpConfig.getConnectTimeOut() : connectTimeout;
        }
        return connectTimeout;
    }

    /**
     * 如果设置了超时时间，或者SSL，则需要使用新的OkHttpClient
     * @return
     */
    public boolean needNewClient(){
        if(writeTimeout > 0 || readTimeout > 0 ||
                connectTimeout > 0 || mSsl != null){
            return true;
        }else{
            return false;
        }
    }

    public T file(File stream){
        if(stream != null && stream.isFile() && stream.exists()) {
            getRequestParams().addStream(stream);
        }
        return (T)this;
    }

    public T bytes(byte[] stream) {
        if (stream.length > 0){
            getRequestParams().addStream(stream);
        }
        return (T)this;
    }

    /**
     * 要求使用json格式的字符串
     * @param stream
     * @return
     */
    public T json(String stream){
        if(!TextUtils.isEmpty(stream)){
            getRequestParams().addStream(stream);
        }
        return (T)this;
    }

    public T file(String key, File stream){
        if(!TextUtils.isEmpty(key) && stream != null && stream.isFile() && stream.exists()) {
            getRequestParams().addStream(key, stream);
        }
        return (T)this;
    }

    public T bytes(String key, byte[] stream){
        if (!TextUtils.isEmpty(key) && stream.length > 0){
            getRequestParams().addStream(key, stream);
        }
        return (T)this;
    }

    /**
     * 要求使用json格式的字符串
     * @param key
     * @param stream
     * @return
     */
    public T json(String key, String stream){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(stream)){
            getRequestParams().addStream(key, stream);
        }
        return (T)this;
    }

    public RequestParams getRequestParams() {
        if(this.mRequestParams == null){
            this.mRequestParams = new RequestParams();
        }
        return mRequestParams;
    }

    /**
     * 设置tag
     * @param tag
     * @return
     */
    public T tag(Object tag){
        this.tag = tag;
        return (T)this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public final void execute(Callback callback){
        Http.getExecutor().execute(this, callback);
    }
}
