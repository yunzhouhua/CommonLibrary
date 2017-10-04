package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.request.RequestParams;
import com.yunzhou.libcommon.net.http.request.Request;
import com.yunzhou.libcommon.utils.StringUtils;

/**
 * Excutor 执行网络请求
 * Created by huayunzhou on 2017/9/27.
 */
public abstract class Executor {

    /**
     * 初始化
     * @param context
     */
    public abstract void init(@NonNull Context context);
    /**
     * 执行请求
     */
    public abstract <T> void execute(Request request, Callback<T> callback);
    /**
     * 移除Cookie
     */
    public abstract void clearCookie(Context context);

    /**
     * 取消网络请求
     * @param tag
     */
    public abstract void cancel(@NonNull Object tag);

    /**
     * 取消所有网络请求
     */
    public abstract void cancelAll();

    protected String formatUrl(Request request){
        String url = request.getUrl();
        if(TextUtils.isEmpty(url)){
            throw new IllegalStateException("Request need a url");
        }

        String paramsString = buildParams(request);
        if(TextUtils.isEmpty(paramsString)){
            return url;
        }
        if(url.contains("?")){
            return StringUtils.plusString(url, "&", paramsString);
        }else{
            return StringUtils.plusString(url, "?", paramsString);
        }
    }

    private String buildParams(Request request) {
        RequestParams body = request.getRequestParams();
        switch (request.getMethod()){
            case POST:
                return StringUtils.EMPTY;
            default:
                return buildParams(body.getBasicParams());
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
