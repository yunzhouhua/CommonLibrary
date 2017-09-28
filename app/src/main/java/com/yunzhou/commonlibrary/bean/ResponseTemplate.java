package com.yunzhou.commonlibrary.bean;

/**
 * Created by huayunzhou on 2017/9/28.
 */

public class ResponseTemplate<T> {
    private long error_code;
    private String error_msg;
    private T data;
    private int lalal;
    private boolean xixixi;

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

    public int getLalal() {
        return lalal;
    }

    public void setLalal(int lalal) {
        this.lalal = lalal;
    }

    public boolean isXixixi() {
        return xixixi;
    }

    public void setXixixi(boolean xixixi) {
        this.xixixi = xixixi;
    }
}
