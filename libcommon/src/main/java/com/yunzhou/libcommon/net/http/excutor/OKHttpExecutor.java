package com.yunzhou.libcommon.net.http.excutor;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.yunzhou.libcommon.net.http.request.RequestParams;
import com.yunzhou.libcommon.net.http.response.Response;

import java.io.File;
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
            if(requestBody != null) {
                requestBuilder.post(requestBody);
            }
        }
        executeOkHttp(request, requestBuilder.build(), callback);
    }

    /**
     * 生成Post请求的RequestBody
     * @param request
     * @return
     */
    private RequestBody makeBody(Request request) {
        RequestParams params = request.getRequestParams();
        if(params == null){
            return null;
        }
        if(params.getSinglePart() != null){
            //单一数据流传递
            Object singlePart = params.getSinglePart();
            MediaType mediaType = null;
            if(singlePart instanceof String){
                mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.JSON);
                return RequestBody.create(mediaType, (String)singlePart);
            }else if(singlePart instanceof byte[]){
                mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.STREAM);
                return RequestBody.create(mediaType, (byte[])singlePart);
            }else if(singlePart instanceof File){
                mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.STREAM);
                return RequestBody.create(mediaType, (File)singlePart);
            }
        }else{
            if(params.getMultiPart() != null && params.getMultiPart().size() > 0){
                //复合数据流
                ArrayMap<String, Object> multiPart = params.getMultiPart();
                MediaType mediaType = null;
                MultipartBody.Builder multiBuilder = new MultipartBody.Builder();
                multiBuilder.setType(MultipartBody.FORM);
                for(int i = 0; i < multiPart.size(); i++){
                    if(multiPart.valueAt(i) instanceof String){
                        multiBuilder.addFormDataPart(multiPart.keyAt(i), (String)(multiPart.valueAt(i)));
                    }else if(multiPart.valueAt(i) instanceof byte[]){
                        mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.STREAM);
                        RequestBody bytesBody = RequestBody.create(mediaType, (byte[])(multiPart.valueAt(i)));
                        multiBuilder.addFormDataPart(multiPart.keyAt(i), multiPart.keyAt(i), bytesBody);
                    }else if(multiPart.valueAt(i) instanceof File){
                        mediaType = MediaType.parse(com.yunzhou.libcommon.net.http.MediaType.STREAM);
                        RequestBody fileBody = RequestBody.create(mediaType, (File)(multiPart.valueAt(i)));
                        multiBuilder.addFormDataPart(multiPart.keyAt(i), ((File)multiPart.valueAt(i)).getName(), fileBody);
                    }
                }
                if(params.getBasicParams() != null && params.getBasicParams().size() > 0){
                    //添加基本数据到表单
                    ArrayMap<String, String> basicParams = params.getBasicParams();
                    for(int i = 0; i < basicParams.size(); i++){
                        multiBuilder.addFormDataPart(basicParams.keyAt(i), basicParams.valueAt(i));
                    }
                }
                return multiBuilder.build();
            }else{
                //基本数据，或者没有
                if(params.getBasicParams() != null && params.getBasicParams().size() > 0){
                    //添加基本数据到表单
                    ArrayMap<String, String> basicParams = params.getBasicParams();
                    FormBody.Builder formBuilder = new FormBody.Builder();
                    for(int i = 0; i < basicParams.size(); i++){
                        formBuilder.add(basicParams.keyAt(i), basicParams.valueAt(i));
                    }
                    return formBuilder.build();
                }
            }
        }
        return null;
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
