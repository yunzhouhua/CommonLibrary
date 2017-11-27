package com.yunzhou.commonlibrary.activity.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.adapter.PhotoViewAdapter;

/**
 * Created by huayunzhou on 2017/11/27.
 */

public class PhotoDialogFragment extends DialogFragment {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));    // 设置背景透明
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        View view = inflater.inflate(R.layout.dialog_fragment_photo, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new PhotoViewAdapter(getChildFragmentManager(), IMGS));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
