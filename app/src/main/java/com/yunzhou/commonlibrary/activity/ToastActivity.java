package com.yunzhou.commonlibrary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.libcommon.utils.ToastUtils;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_normal:
                ToastUtils.show(this, null, Gravity.CENTER);
                break;
        }
    }
}
