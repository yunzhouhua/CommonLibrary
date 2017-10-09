package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.cookie.CookieManager;
import com.yunzhou.libcommon.net.http.exception.CanceledException;
import com.yunzhou.libcommon.net.http.request.Request;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by huayunzhou on 2017/9/27.
 */
public class OKHttpExecutor extends Executor {

    private OkHttpClient mOkHttpClient;
    private CookieManager mCookieManager;

    @Override
    public void init(@NonNull Context context) {
        HttpConfig config = Http.getConfig();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时时间
        builder.connectTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        builder.readTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        builder.writeTimeout(config.getConnectTimeOut(), TimeUnit.MILLISECONDS);
        //设置Cookie
        mCookieManager = new CookieManager(context, config.getCookieType());
        builder.cookieJar(mCookieManager);

        //设置Https
        if(config.getSslParams() != null &&
                config.getSslParams().getmTrustManager() != null &&
                config.getSslParams().getmSSLSocketFactory() != null){
            builder.sslSocketFactory(config.getSslParams().getmSSLSocketFactory(),
                    config.getSslParams().getmTrustManager());
        }

        mOkHttpClient = builder.build();
    }

    @Override
    public <T> void execute(Request request, Callback<T> callback) {
        executeOkHttp(request, request.generateRequest(callback), callback);
    }

    private <T> void executeOkHttp(@NonNull final Request request,
                                   @NonNull final okhttp3.Request okHttpRequest,
                                   @NonNull final Callback<T> callback){
        Call call;

        if(request.needNewClient()){
            OkHttpClient.Builder newBuilder = mOkHttpClient.newBuilder();
            newBuilder.writeTimeout(request.getWriteTimeout(true), TimeUnit.MILLISECONDS)
                    .readTimeout(request.getReadTimeout(true), TimeUnit.MILLISECONDS)
                    .connectTimeout(request.getConnectTimeout(true), TimeUnit.MILLISECONDS);
            if(request.getSSL() != null){
                newBuilder.sslSocketFactory(request.getSSL().getmSSLSocketFactory(),
                        request.getSSL().getmTrustManager());
            }
            call = newBuilder.build().newCall(okHttpRequest);
        }else {
            call = mOkHttpClient.newCall(okHttpRequest);
        }

        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailCallback(request.getId(), call, e, callback, null);
            }

            @Override
            public void onResponse(Call call, okhttp3.Response okHttpResponse) throws IOException {
                if(call.isCanceled()){
                    sendFailCallback(request.getId(), call,
                            new IOException("Call is canceled!"), callback, okHttpResponse);
                }
                if(!okHttpResponse.isSuccessful()){
                    sendFailCallback(request.getId(), call,
                            new IOException("Request failed, response's code is " + okHttpResponse.code()),
                            callback, okHttpResponse);
                }
                try {
                    T t = callback.parseResponse(request.getId(), okHttpResponse);
                    sendSuccessCallback(request.getId(), t, callback);
                }catch (Exception e){
                    sendFailCallback(request.getId(), call, e, callback, okHttpResponse);
                }finally {
                    if(okHttpResponse.body() != null) {
                        okHttpResponse.body().close();
                    }
                }
            }
        });
    }

    private <T> void sendSuccessCallback(long id, T t, Callback<T> callback) {
        if(callback != null){
            callback.runOnUIThreadSuccess(id, t);
        }
    }

    private <T> void sendFailCallback(long id, Call call, Exception e, Callback<T> callback, okhttp3.Response response) {
        HttpError error = new HttpError();
        error.setId(id);
        if(call.isCanceled()){
            error.setCode(HttpError.ERROR_CANCELED);
            error.setMessage("Call is canceled");
            error.setException(new CanceledException());
        }else {
            if (response != null) {
                error.setCode(response.code());
            }
            error.setMessage(e.getMessage());
            error.setException(e);
        }
        if(callback != null){
            callback.runOnUIThreadFailed(error);
        }

    }

    @Override
    public void clearCookie(Context context) {
        mCookieManager.clearCookie();
    }

    @Override
    public void cancel(@NonNull Object tag) {
        try{
            for(Call call : mOkHttpClient.dispatcher().queuedCalls()){
                if(tag.equals(call.request().tag())){
                    call.cancel();
                }
            }
            for(Call call : mOkHttpClient.dispatcher().runningCalls()){
                if(tag.equals(call.request().tag())){
                    call.cancel();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void cancelAll() {
        try{
            for(Call call : mOkHttpClient.dispatcher().queuedCalls()){
                call.cancel();
            }
            for(Call call : mOkHttpClient.dispatcher().runningCalls()){
                call.cancel();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
