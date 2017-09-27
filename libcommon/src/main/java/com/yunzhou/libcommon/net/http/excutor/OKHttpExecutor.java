package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.cookie.CookieManager;
import com.yunzhou.libcommon.net.http.request.Request;

import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.OkHttpClient;

/**
 * Created by huayunzhou on 2017/9/27.
 */
public class OKHttpExecutor extends Executor {

    private OkHttpClient mOkHttpClient;
    private CookieManager mCookieManager;

    @Override
    public void init(@NonNull Context context) {
        HttpConfig config = Http.getConfig();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时时间
        builder.connectTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        builder.readTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        //设置Cookie
        mCookieManager = new CookieManager(context, config.getCookieType());
        builder.cookieJar(mCookieManager);
        //设置Https
        if(config.getSslParams() != null &&
                config.getSslParams().getmTrustManager() != null &&
                config.getSslParams().getmSSLSocketFactory() != null){
            builder.sslSocketFactory(config.getSslParams().getmSSLSocketFactory(),
                    config.getSslParams().getmTrustManager());
        }

        mOkHttpClient = builder.build();
    }

    @Override
    public <T> void execute(Request request, Callback<T> callback) {
        Headers headers = transformHeaders(request);
    }

    /**
     *
     * @param request
     * @return
     */
    private Headers transformHeaders(Request request) {
        ArrayMap<String, String> headers = request.getHeaders();
        Headers.Builder builder = new Headers.Builder();
        if(headers != null && headers.size() > 0){
            for(int i = 0; i < headers.size(); i++){
                builder.add(headers.keyAt(i), headers.valueAt(i));
            }
        }
        return builder.build();
    }

    @Override
    public void clearCookie(Context context) {

    }

    @Override
    public void cancle(@NonNull Object tag) {

    }
}
