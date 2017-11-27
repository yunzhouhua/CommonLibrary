package com.yunzhou.commonlibrary.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yunzhou.commonlibrary.activity.fragment.PhotoViewFragment;

/**
 * Created by huayunzhou on 2017/11/27.
 */

public class PhotoViewAdapter extends FragmentStatePagerAdapter {

    private String[] imgs;

    public PhotoViewAdapter(FragmentManager fm, String[] imgs) {
        super(fm);
        this.imgs = imgs;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoViewFragment fragment = new PhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("imgUrl", imgs[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return this.imgs.length;
    }
}
