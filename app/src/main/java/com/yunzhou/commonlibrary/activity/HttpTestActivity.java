package com.yunzhou.commonlibrary.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.bean.ResponseTemplate;
import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.callback.JsonCallBack;
import com.yunzhou.libcommon.net.http.callback.StringCallBack;

public class HttpTestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HttpTestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        findViewById(R.id.get).setOnClickListener(this);
        findViewById(R.id.post).setOnClickListener(this);
        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);

        Http.init(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                get();
                break;
            case R.id.post:
                post();
                break;
            case R.id.upload:
                upload();
                break;
            case R.id.download:
                download();
                break;
        }
    }

    public void get() {
        Http.get()
                .url("https://accountv3-api.fclassroom.cn/checkVersion.json")
                .addHeader("Charset", "UTF-8")
                .addHeader("Accept-Encoding", "gzip,deflate")
                .addHeader("Accept-Language", "zh-cn")
                .addParam("jike-client-from", "APP")
                .addParam("versionType", "21")
                .addParam("category", "20")
                .addParam("versionNo", "355")
                .execute(new JsonCallBack<ResponseTemplate<Object>>() {
                    @Override
                    protected void onFailed(@NonNull HttpError error) {
                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
                    }

                    @Override
                    protected void onSuccess(ResponseTemplate<Object> result) {
                        Log.e(TAG, "result : " + result );
                    }
                });
//                .execute(new StringCallBack() {
//                    @Override
//                    protected void onFailed(@NonNull HttpError error) {
//                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
//                    }
//
//                    @Override
//                    protected void onSuccess(String result) {
//                        Log.e(TAG, "result : " + result );
//                    }
//                });
    }

    public void post() {
        Http.post()
                .url("https://accountv3-api.fclassroom.cn/checkVersion.json")
                .addHeader("Charset", "UTF-8")
                .addHeader("Accept-Encoding", "gzip,deflate")
                .addHeader("Accept-Language", "zh-cn")
                .addParam("jike-client-from", "APP")
                .addParam("versionType", "21")
                .addParam("category", "20")
                .addParam("versionNo", "355")
                .execute(new JsonCallBack<ResponseTemplate<Object>>() {
                    @Override
                    protected void onFailed(@NonNull HttpError error) {
                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
                    }

                    @Override
                    protected void onSuccess(ResponseTemplate<Object> result) {
                        Log.e(TAG, "result : " + result );
                    }
                });
    }

    private void upload() {
    }

    private void download() {

    }
}
