package com.yunzhou.libcommon.glide.okhttp;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by huayunzhou on 2017/10/11.
 */

public class OKHttpGlideUrlLoader implements ModelLoader<GlideUrl, InputStream> {
    private OkHttpClient okHttpClient;

    public OKHttpGlideUrlLoader(OkHttpClient client){
        this.okHttpClient = client;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {
        return new OKHttpFetcher(okHttpClient, model);
    }

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream>{

        private OkHttpClient client;

        public Factory(){}

        private synchronized OkHttpClient getOkHttpClient(){
            if(client == null){
                client = new OkHttpClient();
            }
            return client;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OKHttpGlideUrlLoader(getOkHttpClient());
        }

        @Override
        public void teardown() {

        }
    }
}
