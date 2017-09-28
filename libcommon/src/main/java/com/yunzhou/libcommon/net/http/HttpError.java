package com.yunzhou.libcommon.net.http;

/**
 * 网络请求异常封装
 * Created by huayunzhou on 2017/9/27.
 */
public class HttpError {

    /**
     * IOException
     */
    public static final int ERROR_IO = -100;
    /**
     * call is Canceled
     */
    public static final int ERROR_CANCELED = -101;
    /**
     * 泛型转换异常
     */
    public static final int ERROR_TYPE = -102;

    /**
     * 请求Id
     */
    private long id;

    /**
     * 异常错误编码
     */
    private int code;

    /**
     * 异常信息
     */
    private String message;
    /**
     * 异常
     */
    private Exception exception;

    public HttpError(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
