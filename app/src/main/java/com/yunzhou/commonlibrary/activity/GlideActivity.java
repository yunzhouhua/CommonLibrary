package com.yunzhou.commonlibrary.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yunzhou.commonlibrary.R;
import com.yunzhou.libcommon.glide.transform.RoundTransform;
import com.yunzhou.libcommon.views.RoundImageView;

public class GlideActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        mImageView = (ImageView) findViewById(R.id.img);

        findViewById(R.id.load).setOnClickListener(this);
        findViewById(R.id.load_round).setOnClickListener(this);
        findViewById(R.id.load_circle).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Glide.with(this)
////                        .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
//                .load("http://diy.qqjay.com/u2/2012/0618/ed6982355b1340095aeaf79072bdc1cc.jpg")
////                .transform(new RoundTransform(this, RoundTransform.TYPE_CIRCLE, 10, Color.GREEN))
//                .into(mImageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.load:
                Glide.with(this)
                        .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                        .into(mImageView);
                break;
            case R.id.load_circle:
                Glide.with(this)
//                        .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                        .load("http://diy.qqjay.com/u2/2012/0618/ed6982355b1340095aeaf79072bdc1cc.jpg")
                        .transform(new RoundTransform(this, RoundTransform.TYPE_CIRCLE, 10, Color.GREEN))
                        .into(mImageView);
                break;
            case R.id.load_round:
                Glide.with(this)
                        .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                        .transform(new RoundTransform(this, RoundTransform.TYPE_ROUND, 10, Color.RED, 20))
                        .into(mImageView);
                break;
        }
    }
}
