package com.yunzhou.libcommon.net.http.request;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.Method;
import com.yunzhou.libcommon.net.http.callback.Callback;

import java.util.Map;

/**
 * Created by huayunzhou on 2017/9/27.
 */

public abstract class Request<T extends Request> {

    private long id;
    private String url;
    private ArrayMap<String, String> headers;
    private ArrayMap<String, String> params;
    private Method method;
    private Object tag;

    public Request(Method method){
        this.method = method;
        headers = new ArrayMap<>();
        params = new ArrayMap<>();
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
    public T addParams(String key, String value){
        if(!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
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
    public T addParams(String key, int value){
        if(!TextUtils.isEmpty(key)){
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
    public T addParams(String key, float value){
        if(!TextUtils.isEmpty(key)){
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
    public T addParams(String key, long value){
        if(!TextUtils.isEmpty(key)){
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
    public T addParams(String key, double value){
        if(!TextUtils.isEmpty(key)){
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
    public T addParams(String key, boolean value){
        if(!TextUtils.isEmpty(key)){
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
                this.params.put(params.keyAt(i), params.valueAt(i));
            }
        }
        params.clear();
        params = null;
        return (T)this;
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

    public String getUrl() {
        return url;
    }

    public final void execute(Callback callback){
        Http.getExecutor().execute(this, callback);
    }
}
