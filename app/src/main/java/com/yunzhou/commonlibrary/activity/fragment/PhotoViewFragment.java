package com.yunzhou.commonlibrary.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.yunzhou.commonlibrary.R;

/**
 * Created by huayunzhou on 2017/11/27.
 */

public class PhotoViewFragment extends Fragment {

    private PhotoView mPhotoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_view, container, false);

        mPhotoView = (PhotoView) view.findViewById(R.id.photo_view);
        String imgUrl = getArguments().getString("imgUrl");
        Glide.with(this).load(imgUrl).into(mPhotoView);
        return view;
    }
}
