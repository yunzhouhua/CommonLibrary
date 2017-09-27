package com.yunzhou.libcommon.net.http;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.excutor.Executor;
import com.yunzhou.libcommon.net.http.request.Request;

/**
 * Created by huayunzhou on 2017/9/26.
 */

public class Http {

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
        init(context, null);
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
    public static Request get(){
//        return new Request(){} ;
        return null;
    }

    /**
     * post请求
     * @return
     */
    public static Request post(){
//        return new Request() {};
        return null;
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
     * 根据tag来取消网路请求
     * @param tag
     */
    public static void cancel(Object tag){
        if(getInstance().mExecutor != null){
            getInstance().mExecutor.cancle(tag);
        }
    }

    public static void clearCookie(Context context){
        if(getInstance().mExecutor != null){
            getInstance().mExecutor.clearCookie(context);
        }
    }
}
