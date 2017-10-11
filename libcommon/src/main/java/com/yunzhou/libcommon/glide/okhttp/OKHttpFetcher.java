package com.yunzhou.libcommon.glide.okhttp;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 更改Glide的网络请求引擎为OKHttp
 * Created by huayunzhou on 2017/10/11.
 */

public class OKHttpFetcher implements DataFetcher<InputStream> {

    private final OkHttpClient mOkHttpClient;
    private final GlideUrl mUrl;
    private InputStream mStream;
    private ResponseBody mResponseBody;
    private volatile boolean isCancelled;

    public OKHttpFetcher(OkHttpClient client, GlideUrl url){
        this.mOkHttpClient = client;
        this.mUrl = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder requestBuilder = new Request.Builder()
                .url(mUrl.toStringUrl());
        for(Map.Entry<String, String> headerEntry : mUrl.getHeaders().entrySet()){
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
        requestBuilder.addHeader("httpEngine", "OkHttp");
        Request request = requestBuilder.build();
        if(isCancelled){
            return null;
        }
        Response response = mOkHttpClient.newCall(request).execute();
        mResponseBody = response.body();
        if(!response.isSuccessful() || mResponseBody == null){
            throw new IOException("Request failed with code: " + response.code());
        }
        mStream = ContentLengthInputStream.obtain(mResponseBody.byteStream(),
                mResponseBody.contentLength());
        return mStream;
    }

    @Override
    public void cleanup() {
        try {
            if(mStream != null){
                mStream.close();
            }
            if(mResponseBody != null){
                mResponseBody.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return mUrl.getCacheKey();
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}
