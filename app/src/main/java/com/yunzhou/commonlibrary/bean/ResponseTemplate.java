package com.yunzhou.commonlibrary.bean;

/**
 * Created by huayunzhou on 2017/9/28.
 */

public class ResponseTemplate<T> {
    private long error_code;
    private String error_msg;
    private T data;

    public long getError_code() {
        return error_code;
    }

    public void setError_code(long error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseTemplate{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", data=" + data +
                '}';
    }
}
