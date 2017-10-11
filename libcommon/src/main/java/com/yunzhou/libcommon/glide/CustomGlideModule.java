package com.yunzhou.libcommon.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.yunzhou.libcommon.glide.okhttp.OKHttpGlideUrlLoader;

import java.io.InputStream;

/**
 * Glide自定义模块，修改Glide的默认配置
 * Created by huayunzhou on 2017/10/11.
 */

public class CustomGlideModule implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //设置网络引擎为OKHttp
        glide.register(GlideUrl.class, InputStream.class, new OKHttpGlideUrlLoader.Factory());

    }
}
