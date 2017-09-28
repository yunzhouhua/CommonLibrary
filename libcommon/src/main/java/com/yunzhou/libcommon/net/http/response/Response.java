package com.yunzhou.libcommon.net.http.response;

import android.util.ArrayMap;

/**
 * Created by huayunzhou on 2017/9/27.
 */

public class Response {
    private long id;
    private boolean isCanceled;
    private boolean isSuccessful;
    private int code;
    private byte[] body;
    private ArrayMap<String, String> headers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public ArrayMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayMap<String, String> headers) {
        this.headers = headers;
    }
}
