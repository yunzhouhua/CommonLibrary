package com.yunzhou.commonlibrary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import com.yunzhou.commonlibrary.R;

public class PhotoViewActivity extends AppCompatActivity {

    PhotoView photoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.mipmap.banma);
    }
}
