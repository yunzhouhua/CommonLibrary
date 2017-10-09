package com.yunzhou.libcommon.net.http.log;

import android.util.ArrayMap;
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
        Log.e(TAG, "==================== START ====================");
        Log.e(TAG, "== url : " + request.getUrl());
        if(request.getParams() != null && request.getParams().size() > 0) {
            Log.e(TAG, "== params : " + request.getParams().toString());
        }
    }

    public static void endFail(String url, HttpError error){
        Log.e(TAG, "==================== END FAIL ====================");
        Log.e(TAG, "== url : " + url);
        Log.e(TAG, "== error code : " + error.getCode());
        Log.e(TAG, "== error msg : " + error.getMessage());
    }

    public static void endSuccess(String url, Object result){
        Log.e(TAG, "==================== END SUCCESS ====================");
        Log.e(TAG, "== url : " + url);
        if(result == null){
            Log.e(TAG, "== result : null");
        }else if(result instanceof String){
            Log.e(TAG, "== result : " + (String)result);
        }else if(result instanceof byte[]){
            Log.e(TAG, "== result : " + new String((byte[])result));
        }else{
            Log.e(TAG, "== result : " + result.toString());
        }
    }
}
