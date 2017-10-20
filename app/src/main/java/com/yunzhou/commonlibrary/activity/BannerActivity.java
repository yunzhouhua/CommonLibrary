package com.yunzhou.commonlibrary.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.yunzhou.commonlibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Banner
 * 地址 ： https://github.com/youth5201314/banner
 *
 * 混淆：
    -keep class com.youth.banner.** {
        *;
    }
 */
public class BannerActivity extends AppCompatActivity {
    @Override
    public ClassLoader getClassLoader() {
        return super.getClassLoader();
    }

    private Banner mBannerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        mBannerView = (Banner) findViewById(R.id.banner);
        mBannerView.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        mBannerView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(BannerActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });
        List<String> urls = new ArrayList<>();
        urls.add("http://img.daimg.com/uploads/allimg/171018/3-1G01R35H6.jpg");
        urls.add("http://img.daimg.com/uploads/allimg/171018/3-1G01R33Q9.jpg");
        urls.add("http://img.daimg.com/uploads/allimg/171018/3-1G01QQ231.jpg");
        mBannerView.setImages(urls);
        mBannerView.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBannerView.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBannerView.stopAutoPlay();
    }

}
