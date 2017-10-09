package com.yunzhou.libcommon.net.http.callback;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import com.yunzhou.libcommon.R;
import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.exception.CanceledException;
import com.yunzhou.libcommon.utils.StringUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;

/**
 * 网络请求回调
 * Created by huayunzhou on 2017/9/27.
 */

public abstract class Callback<T>{
    private Context mContext;
    public Callback(){
        this(null);
    }

    public Callback(Context context){
        this.mContext = context;
    }

    /**
     * 请求失败回调
     * @param error
     */
    protected abstract void onFailed(@NonNull HttpError error);

    /**
     * 请求成功回调
     * @param result
     */
    protected abstract void onSuccess(long id, T result);

    /**
     * 更新进度，如果需要监听上传/下载进度时可以重写他
     * @param id
     * @param current
     * @param total
     */
    public void updateProgress(long id, long current, long total){};

    public abstract T parseResponse(long id, @NonNull final Response response) throws IOException;

    public final void runOnUIThreadFailed(@NonNull final HttpError error){
        // Step 1. 检测是否需要终端操作
        boolean interruption = checkInterruptionFailed(error);
        if (interruption) {
            Log.d("Http", "sendFailResult: interruption");
            return;
        }

        // Step 2. 重置错误信息的数据
        String parsedMessage = parseUniteErrorMessage(error);
        if (TextUtils.isEmpty(parsedMessage)) {
            parsedMessage = parseErrorMessage(error);
        }
        if (!TextUtils.isEmpty(parsedMessage)) {
            error.setMessage(parsedMessage);
        }

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailed(error);
            }
        });
    }

    public final void runOnUIThreadSuccess(final long id, @NonNull final T result){
        boolean interruption = checkInterruptionSuccessed(id);
        if(interruption){
            Log.d("Http", "sendFailResult: interruption");
            return ;
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                onSuccess(id, result);
            }
        });
    }

    /**
     * 检测是否需要中断处理
     * <p>
     * 例如： 如果一个请求如果canceled了 或者context 已经finish了 那么再进行返回就报错了
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean checkInterruptionFailed(@NonNull HttpError error) {
        if (mContext != null && mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (activity.isFinishing()) {
                Log.d(Http.LOG_TAG, StringUtils.plusString(error.getId(), " on Error activity is finishing to do nothing "));
                return true;
            }
        }

        if (error.getException() instanceof CanceledException) {
            Log.d(Http.LOG_TAG, StringUtils.plusString(error.getId(), "  is canceled "));
            return true;
        }
        return false;
    }

    /**
     * 检测是否需要中断处理
     * <p>
     * 例如： 如果context 已经finish了 那么再进行继续操作了
     */
    @SuppressWarnings("WeakerAccess")
    protected boolean checkInterruptionSuccessed(long id) {
        if (mContext != null && mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (activity.isFinishing()) {
                Log.d("Http", StringUtils.plusString(id, " on Error activity is finishing to do nothing "));
                return true;
            }
        }
        return false;
    }

    /**
     * 解析统一的返回错误信息
     */
    @SuppressWarnings("WeakerAccess")
    protected String parseUniteErrorMessage(@NonNull HttpError error) {
        Exception e = error.getException();
        Resources resources = Http.getResource();

        if (e instanceof ConnectException) {
            return resources.getString(R.string.net_poor_connections);
        } else if (e instanceof SocketTimeoutException) {
            return resources.getString(R.string.net_server_error);
        } else if (e instanceof UnknownHostException) {
            return resources.getString(R.string.net_server_error);
        }
        return null;
    }
    /**
     * 如果错误信息要单独处理，就重写这个方法进行处理
     */
    @SuppressWarnings("WeakerAccess,unused")
    protected String parseErrorMessage(HttpError error) {
        return null;
    }
}
