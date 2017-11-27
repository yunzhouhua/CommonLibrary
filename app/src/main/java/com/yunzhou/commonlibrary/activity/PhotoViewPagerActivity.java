package com.yunzhou.commonlibrary.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.activity.fragment.PhotoDialogFragment;
import com.yunzhou.commonlibrary.adapter.PhotoViewAdapter;

public class PhotoViewPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private final static String[] IMGS = {
            "http://mpic.tiankong.com/935/cce/935cce5611ee35530947666ef1201206/640.jpg",
            "http://mpic.tiankong.com/72c/6d1/72c6d1bb97c1e16fca3cfb807239216c/640.jpg",
            "http://mpic.tiankong.com/1c1/e8d/1c1e8dfa41012d294a16ebc4f88a264a/640.jpg",
            "http://mpic.tiankong.com/08d/9cf/08d9cf3e8658d875a9ec3d701d5a8790/640.jpg",
            "http://mpic.tiankong.com/95a/7e6/95a7e6c9992b13c7692aa38d2c2d2bd8/640.jpg",
            "http://mpic.tiankong.com/ef2/c6e/ef2c6e8ac1a39a6166fb838eaa936751/640.jpg",
            "http://mpic.tiankong.com/826/be1/826be1c78621447c53f1760325fe0d9b/640.jpg",
            "http://mpic.tiankong.com/b33/13c/b3313c06d1220c319b8410eb1cfced2f/east-ep-a51-3655253.jpg",
            "http://mpic.tiankong.com/e29/b6d/e29b6d725302ef29e6dac3725b4964f1/640.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view_pager);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PhotoViewAdapter(getSupportFragmentManager(), IMGS));

    }

    public void showDialog(View view) {
        PhotoDialogFragment dialogFragment = new PhotoDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "");
    }
}
