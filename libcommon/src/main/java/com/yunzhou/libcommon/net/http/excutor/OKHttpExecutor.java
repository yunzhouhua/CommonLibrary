package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.ArrayMap;

import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.Method;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.config.HttpConfig;
import com.yunzhou.libcommon.net.http.cookie.CookieManager;
import com.yunzhou.libcommon.net.http.exception.CanceledException;
import com.yunzhou.libcommon.net.http.exception.HttpErrorException;
import com.yunzhou.libcommon.net.http.request.Request;
import com.yunzhou.libcommon.net.http.response.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

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
        //初始化FinalUrl
        String url = formatUrl(request);
        request.setFinalUrl(url);
        //初始化 OKHttp3的headers
        Headers headers = transformHeaders(request);
        //初始化 OkHttp3的Request
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .headers(headers);
        if(request.getMethod() == Method.POST){
            //POST请求，添加body参数
            RequestBody requestBody = makeBody(request);
            requestBuilder.post(requestBody);
        }
        executeOkHttp(request, requestBuilder.build(), callback);
    }

    /**
     * 生成Post请求的RequestBody
     * @param request
     * @return
     */
    private RequestBody makeBody(Request request) {
        ArrayMap<String, String> params = request.getParams();
        if(request.getFile() != null){
            MediaType mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.STREAM);
            RequestBody fileBody=RequestBody.create(mediaType,request.getFile());
            if(TextUtils.isEmpty(request.getKeyFile())){
                //单文件上传
                return fileBody;
            }else{
                //复合数据上传MultiBody
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart(request.getKeyFile(), request.getFile().getName(), fileBody);
                if(params != null && params.size() > 0){
                    for(int i = 0; i < params.size(); i++){
                        builder.addFormDataPart(params.keyAt(i), params.valueAt(i));
                    }
                }
                return builder.build();
            }
        }else{
            if(params == null && params.size() <= 0){
                return null;
            }
            //只存在键值对
            FormBody.Builder builder = new FormBody.Builder();
            for(int i = 0; i < params.size(); i++){
                builder.add(params.keyAt(i), params.valueAt(i));
            }
            return builder.build();
        }
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
                if(call.isCanceled()){
                    sendCanceledResult(request, callback);
                }else{
                    HttpError error = new HttpError();
                    error.setId(request.getId());
                    error.setCode(HttpError.ERROR_IO);
                    error.setMessage("IOException");
                    error.setException(e);
                    if(callback != null) {
                        callback.runOnUIThreadFailed(error);
                    }
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response okHttpResponse) throws IOException {
                Response response = new Response();
                response.setId(request.getId());
                response.setCanceled(call.isCanceled());
                response.setSuccessful(okHttpResponse.isSuccessful());
                response.setCode(okHttpResponse.code());
                response.setBody(okHttpResponse.body().bytes());
                response.setHeaders(parseHeaders(okHttpResponse.headers()));
                if(!isCanceled(request, response, callback) && isSuccessful(request, response, callback)){
                    try {
                        if(callback != null) {
                            T t = callback.parseResponse(request, response);
                            callback.runOnUIThreadSuccess(response, t);
                        }
                    } catch (HttpErrorException e) {
                        HttpError error = e.getHttpError();
                        if(callback != null) {
                            callback.runOnUIThreadFailed(error);
                        }
                    }catch (Exception e){
                        HttpError error = new HttpError();
                        error.setId(request.getId());
                        error.setCode(HttpError.ERROR_TYPE);
                        error.setMessage(e.getMessage());
                        error.setException(e);
                        if(callback != null) {
                            callback.runOnUIThreadFailed(error);
                        }
                    }
                }
            }
        });
    }

    /**
     * 判断网络请求是否已经被取消，取消则调用onFail
     * @param request
     * @param response
     * @param callback
     * @return
     */
    protected boolean isCanceled(@NonNull Request request,
                                 @NonNull Response response,
                                 @NonNull Callback callback){
        boolean isCanceled = response.isCanceled();
        if(isCanceled){
            sendCanceledResult(request, callback);
        }
        return isCanceled;
    }

    protected boolean isSuccessful(@NonNull Request request,
                                   @NonNull Response response,
                                   @NonNull Callback callback){
        boolean isSuccessful = response.isSuccessful();
        if(!isSuccessful){
            sendResponseCodeErrorResult(request, response, callback);
        }
        return isSuccessful;
    }

    private void sendResponseCodeErrorResult(Request request, Response response, Callback callback) {
        HttpError error = new HttpError();
        error.setId(request.getId());
        error.setCode(response.getCode());
        error.setMessage("request failed , reponse's code is :" + response.getCode());
        error.setException(new IllegalStateException());
        if(callback != null) {
            callback.runOnUIThreadFailed(error);
        }
    }

    /**
     * okHttp3中的Headers转换成ArryMap
     * @param headers
     * @return
     */
    private ArrayMap<String, String> parseHeaders(okhttp3.Headers headers) {
        if(headers == null || headers.size() <= 0){
            return null;
        }
        ArrayMap<String, String> map = new ArrayMap<>();
        for(int i = 0; i < headers.size(); i++){
            map.put(headers.name(i), headers.value(i));
        }
        return map;
    }

    /**
     * 当请求被取消时，请求失败回调
     * @param request
     * @param callback
     */
    private void sendCanceledResult(Request request, Callback callback) {
        HttpError error = new HttpError();
        error.setId(request.getId());
        error.setCode(HttpError.ERROR_CANCELED);
        error.setMessage("call is canceled");
        error.setException(new CanceledException());
        if(callback != null) {
            callback.runOnUIThreadFailed(error);
        }
    }

    /**
     *  Request中header参数转换成OKHttp的Headers
     * @param request
     * @return
     */
    private Headers transformHeaders(Request request) {
        ArrayMap<String, String> headers = request.getHeaders();
        Headers.Builder builder = new Headers.Builder();
        if(headers != null && headers.size() > 0){
            for(int i = 0; i < headers.size(); i++){
                builder.add(headers.keyAt(i), headers.valueAt(i));
            }
        }
        return builder.build();
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
