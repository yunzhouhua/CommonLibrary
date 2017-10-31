package com.yunzhou.commonlibrary.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunzhou.commonlibrary.R;

/**
 * Created by huayunzhou on 2017/10/30.
 */

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        String title = getArguments().getString("title");
        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText(title);
        return view;
    }
}
