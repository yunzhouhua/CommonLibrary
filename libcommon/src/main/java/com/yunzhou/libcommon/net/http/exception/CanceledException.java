package com.yunzhou.libcommon.net.http.exception;

/**
 * 网络请求取消
 * Created by huayunzhou on 2017/9/27.
 */

public class CanceledException extends Exception {
    public CanceledException(){
        super("Call is Canceled");
    }
}
