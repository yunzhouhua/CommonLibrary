package com.yunzhou.commonlibrary.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yunzhou.commonlibrary.R;

public class HttpTestActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        findViewById(R.id.get_sync).setOnClickListener(this);
        findViewById(R.id.get_async).setOnClickListener(this);
        findViewById(R.id.post_sync).setOnClickListener(this);
        findViewById(R.id.post_async).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_sync:
                getSync();
                break;
            case R.id.get_async:
                getAsync();
                break;
            case R.id.post_sync:
                postSync();
                break;
            case R.id.post_async:
                postAsync();
                break;
        }
    }

    /**
     * get 同步
     */
    public void getSync() {

    }

    /**
     * get异步
     */
    public void getAsync() {
    }

    /**
     * post 同步
     */
    private void postSync() {

    }

    /**
     * post 异步
     */
    private void postAsync() {

    }
}
