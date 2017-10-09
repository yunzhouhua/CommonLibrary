package com.yunzhou.libcommon.net.http;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.excutor.Executor;
import com.yunzhou.libcommon.net.http.excutor.OKHttpExecutor;
import com.yunzhou.libcommon.net.http.request.GetRequest;
import com.yunzhou.libcommon.net.http.request.PostRequest;

/**
 * Created by huayunzhou on 2017/9/26.
 */

public class Http {

    public static final String LOG_TAG = "http";

    private static Http mInstance;
    private HttpConfig mHttpConfig;
    private Executor mExecutor;
    private Resources mResource;

    private Http(){}

    private static Http getInstance(){
        if(mInstance == null){
            synchronized (Http.class){
                if(mInstance == null){
                    mInstance = new Http();
                }
            }
        }
        return mInstance;
    }

    /**
     * 网络请求初始化
     * @param context
     */
    public static void init(@NonNull Context context){
        init(context, new OKHttpExecutor());
    }

    public static void init(@NonNull Context context, @NonNull Executor executor){
        init(context, executor, HttpConfig.getDefaultConfig());
    }

    public static void init(@NonNull Context context, @NonNull Executor executor, @NonNull HttpConfig config){
        Http http = Http.getInstance();
        http.mResource = context.getApplicationContext().getResources();
        http.mHttpConfig = config;
        http.mExecutor = executor;
        http.mExecutor.init(context);
    }

    /**
     * get请求
     * @return
     */
    public static GetRequest get(){
        return new GetRequest(){} ;
    }

    /**
     * post请求
     * @return
     */
    public static PostRequest post(){
        return new PostRequest();
    }

    /**
     * 对外暴露获取Executor的方法
     * @return
     */
    public static Executor getExecutor(){
        return getInstance().mExecutor;
    }

    /**
     * 对外暴露获取HttpConfig的方法
     * @return
     */
    public static HttpConfig getConfig(){
        return getInstance().mHttpConfig;
    }

    /**
     * 对外暴露获取Resource的方法
     * @return
     */
    public static Resources getResource(){
        return getInstance().mResource;
    }

    /**
     * 根据tag来取消网路请求
     * @param tag
     */
    public static void cancel(Object tag){
        if(getInstance().mExecutor != null){
            getInstance().mExecutor.cancel(tag);
        }
    }

    /**
     * 取消所有网络请求
     */
    public static void cancelAll(){
        if(getInstance().mExecutor != null){
            getInstance().mExecutor.cancelAll();
        }
    }

    /**
     * 清空Cookie
     * @param context
     */
    public static void clearCookie(Context context){
        if(getInstance().mExecutor != null){
            getInstance().mExecutor.clearCookie(context);
        }
    }
}
