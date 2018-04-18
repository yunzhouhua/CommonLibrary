package com.yunzhou.commonlibrary.activity;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.bean.ResponseTemplate;
import com.yunzhou.libcommon.net.http.Http;
import com.yunzhou.libcommon.net.http.HttpError;
import com.yunzhou.libcommon.net.http.MediaType;
import com.yunzhou.libcommon.net.http.callback.FileCallBack;
import com.yunzhou.libcommon.net.http.callback.JsonCallBack;
import com.yunzhou.libcommon.net.http.callback.StringCallBack;

import org.json.JSONException;

import java.io.File;


public class HttpTestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HttpTestActivity";
    private static final String HOST = "http://192.168.2.109:8080/webapp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_test);
        findViewById(R.id.get).setOnClickListener(this);
        findViewById(R.id.post).setOnClickListener(this);
        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);

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
            case R.id.login:
                Http.get()
                        .url(HOST + "login")
                        .execute(new StringCallBack() {
                            @Override
                            protected void onFailed(@NonNull HttpError error) {
                                Log.e(TAG, "onFailed: ");
                            }

                            @Override
                            protected void onSuccess(long id, String result) {
                                Log.e(TAG, "onSuccess: " + result);
                            }
                        });
                break;
            case R.id.logout:
                Http.get()
                        .url(HOST + "logout")
                        .execute(null);
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
                    protected void onSuccess(long id, ResponseTemplate<Object> result) {
                        Log.e(TAG, "result : " + result );
                    }
                });
    }

    public void post() {
//        Http.post()
//                .url("https://accountv3-api.fclassroom.cn/checkVersion.json")
//                .addHeader("Charset", "UTF-8")
//                .addHeader("Accept-Encoding", "gzip,deflate")
//                .addHeader("Accept-Language", "zh-cn")
//                .addParam("jike-client-from", "APP")
//                .addParam("versionType", "21")
//                .addParam("category", "20")
//                .addParam("versionNo", "355")
//                .execute(new JsonCallBack<ResponseTemplate<Object>>() {
//                    @Override
//                    protected void onFailed(@NonNull HttpError error) {
//                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
//                    }
//
//                    @Override
//                    protected void onSuccess(long id, ResponseTemplate<Object> result) {
//                        Log.e(TAG, "result : " + result );
//                    }
//                });

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "dfadfasd");
        jsonObject.put("age", "12");
        Http.post()
                .url("http://localhost:8080/queryItems")
                .addHeader("Charset", "UTF-8")
                .addHeader("Accept-Encoding", "gzip,deflate")
                .addHeader("Accept-Language", "zh-cn")
                .stream(MediaType.JSON, jsonObject.toJSONString())
                .execute(new JsonCallBack<ResponseTemplate<Object>>() {
                    @Override
                    protected void onFailed(@NonNull HttpError error) {
                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
                    }

                    @Override
                    protected void onSuccess(long id, ResponseTemplate<Object> result) {
                        Log.e(TAG, "result : " + result );
                    }
                });
    }

    private void upload() {
        String path= Environment.getExternalStorageDirectory() + File.separator + "jike" + File.separator + "commons-io-2.5.jar";
        File file = new File(path);
        Http.post()
                .url(HOST + "fileUploadPage")
                .addPart("file", file)
                .setConnectTimeout(60000)
                .setReadTimeout(60000)
                .setWriteTimeout(60000)
                .execute(new StringCallBack() {
                    @Override
                    protected void onFailed(@NonNull HttpError error) {
                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
                    }

                    @Override
                    protected void onSuccess(long id, String result) {
                        Log.e(TAG, "Success : " + result);
                    }

                    @Override
                    public void updateProgress(long id, long current, long total) {
                        Log.e(TAG, "Thread : " + Thread.currentThread().getId() + " ;; " + current + " / " + total);
                    }
                });
    }

    private void download() {
        String path= Environment.getExternalStorageDirectory() + File.separator + "jike";
        Log.e(TAG, "download: " + Thread.currentThread().getId());
        Http.get()
                .url("http://192.168.2.109:8080/okhttpdemo/spring-framework.zip")
                .execute(new FileCallBack(path, "spring-framework.zip") {
                    @Override
                    protected void onFailed(@NonNull HttpError error) {
                        Log.e(TAG, "Failed : " + error.getCode() + " : " + error.getMessage());
                    }

                    @Override
                    protected void onSuccess(long id, File result) {
                        Log.e(TAG, "Success : " + result.getAbsolutePath());
                    }

                    @Override
                    public void updateProgress(long id, long current, long total) {
                        Log.e(TAG, "Thread : " + Thread.currentThread().getId() + " ;; " + current + " / " + total);
                    }
                });
    }
}
