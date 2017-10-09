package com.yunzhou.libcommon.net.http.log;

import android.util.Log;

import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.request.Request;

/**
 * 网络请求日志输出工具类
 * Created by huayunzhou on 2017/10/9.
 */

public class NetLog {
    private static final String TAG = "HTTP";

    public static void start(Request request){
        Log.d(TAG, "==================== START ====================");
        Log.d(TAG, "== url : " + request.getUrl());
        if(request.getParams() != null && request.getParams().size() > 0) {
            Log.d(TAG, "== params : " + request.getParams().toString());
        }
    }

    public static void endFail(String url, HttpError error){
        Log.d(TAG, "==================== END FAIL ====================");
        Log.d(TAG, "== url : " + url);
        Log.d(TAG, "== error code : " + error.getCode());
        Log.d(TAG, "== error msg : " + error.getMessage());
    }

    public static void endSuccess(String url, Object result){
        Log.d(TAG, "==================== END SUCCESS ====================");
        Log.d(TAG, "== url : " + url);
        if(result == null){
            Log.d(TAG, "== result : null");
        }else if(result instanceof String){
            Log.d(TAG, "== result : " + (String)result);
        }else if(result instanceof byte[]){
            Log.d(TAG, "== result : " + new String((byte[])result));
        }else{
            Log.d(TAG, "== result : " + result.toString());
        }
    }
}
