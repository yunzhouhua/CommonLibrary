package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.request.Request;

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
    public abstract void cancle(@NonNull Object tag);

}
