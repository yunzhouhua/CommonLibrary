package com.yunzhou.libcommon.net.http.callback;

import android.content.Context;

/**
 * 网络请求回调
 * Created by huayunzhou on 2017/9/27.
 */

public abstract class Callback<T>{
    private Context context;

    public Callback(Context context){
        this.context = context;
    }
}
