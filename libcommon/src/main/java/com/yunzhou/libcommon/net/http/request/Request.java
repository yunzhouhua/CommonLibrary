package com.yunzhou.libcommon.net.http.request;

import android.text.TextUtils;
import android.util.ArrayMap;
import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.Method;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.ssl.SSLParams;
import com.yunzhou.libcommon.utils.StringUtils;
import java.util.Map;
import okhttp3.RequestBody;

/**
 * Created by huayunzhou on 2017/9/27.
 */

public abstract class Request<T extends Request> {

    private long id;
    private Method mMethod;
    private String url;
    //finalUrl主要用于存放get请求中拼接后的url
    private String finalUrl;
    private ArrayMap<String, String> headers;
    private ArrayMap<String, String> params;
    private Object tag;
    private HttpConfig httpConfig;
    private okhttp3.Request.Builder builder;
    //下面这些属性用于区分当前请求是否需要使用新的okHttpClient
    private SSLParams mSsl;
    private long readTimeout;
    private long writeTimeout;
    private long connectTimeout;

    /**
     * post请求创建RequestBody，增加灵活性
     * @return
     */
    protected abstract RequestBody buildRequestBody();

    /**
     * 不同请求，创建不同的Request,增加灵活性
     * @return
     */
    protected abstract okhttp3.Request buildRequest(RequestBody requestBody);

    public Request(Method method){
        //id默认为当前时间戳
        this.id = System.currentTimeMillis();
        this.mMethod = method;
        this.builder = new okhttp3.Request.Builder();
        this.headers = new ArrayMap<>();
        this.mSsl = null;
        this.readTimeout = 0;
        this.writeTimeout = 0;
        this.connectTimeout = 0;
        this.httpConfig = Http.getConfig();
    }

    public final okhttp3.Request generateRequest(Callback callback){
        initBuild();
        RequestBody requestBody = buildRequestBody();
        RequestBody wrapRequestBody = wrapRequestBody(requestBody, callback);
        okhttp3.Request request = buildRequest(wrapRequestBody);
        return request;
    }

    /**
     * build基本数据配置，url/param/tag之类
     */
    private void initBuild() {
        if(TextUtils.isEmpty(this.url)){
            throw new IllegalStateException("url can't be null");
        }
        if(this.mMethod == Method.GET) {
            urlParamPack();
        }
        //设置请求url
        builder = builder.url(TextUtils.isEmpty(getFinalUrl()) ? getUrl() : getFinalUrl());
        //设置请求头
        appendHeaders();
        //设置tag
        if(tag != null){
            builder = builder.tag(tag);
        }
    }

    public RequestBody wrapRequestBody(RequestBody requestBody, Callback callback){
        return requestBody;
    }

    private void appendHeaders(){
        if(this.headers != null && this.headers.size() > 0){
            for(int i = 0; i < this.headers.size(); i++){
                this.builder = this.builder.addHeader(this.headers.keyAt(i), this.headers.valueAt(i));
            }
        }
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, value);
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, String.valueOf(value));
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, String.valueOf(value));
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, String.valueOf(value));
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, String.valueOf(value));
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
            if(this.params == null){
                this.params = new ArrayMap<>();
            }
            this.params.put(key, String.valueOf(value));
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
                if(this.params == null){
                    this.params = new ArrayMap<>();
                }
                this.params.put(entry.getKey(), entry.getValue());
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
                if(this.params == null){
                    this.params = new ArrayMap<>();
                }
                this.params.put(params.keyAt(i), params.valueAt(i));
            }
        }
        params.clear();
        params = null;
        return (T)this;
    }

    /**
     * 设置请求id
     * @param id
     * @return
     */
    public T id(long id){
        this.id = id;
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

    public void setUrl(String url){
        this.url = url;
    }

    public ArrayMap<String, String> getParams() {
        return params;
    }

    public void setParams(ArrayMap<String, String> params) {
        this.params = params;
    }

    protected okhttp3.Request.Builder getBuilder() {
        return builder;
    }

    protected void setBuilder(okhttp3.Request.Builder builder) {
        this.builder = builder;
    }

    public String getFinalUrl() {
        return finalUrl;
    }

    public void setFinalUrl(String finalUrl) {
        this.finalUrl = finalUrl;
    }

    public final void execute(Callback callback){
        Http.getExecutor().execute(this, callback);
    }

    /**
     * 将url与请求参数拼接成字符串
     */
    private void urlParamPack() {
        String url = getUrl();
        if(TextUtils.isEmpty(url)){
            throw new IllegalStateException("url can't be null!");
        }
        if(getParams() == null || getParams().size() <= 0){
            return ;
        }else{
            String params = buildParams(getParams());
            String finalUrl = null;
            if(url.contains("?")){
                setFinalUrl(StringUtils.plusString(url, "&", params));
            }else{
                setFinalUrl(StringUtils.plusString(url, "?", params));
            }
        }
    }

    private String buildParams(ArrayMap<String, String> params) {
        if(params == null || params.size() <= 0){
            return StringUtils.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < params.size(); i++){
            builder.append("&").append(params.keyAt(i)).append("=").append(params.valueAt(i));
        }
        if(builder.length() > 0){
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }
}
