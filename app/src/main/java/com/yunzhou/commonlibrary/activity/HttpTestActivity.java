package com.yunzhou.commonlibrary.activity;

import android.os.Environment;
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

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpTestActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "HttpTestActivity";
    private static final String HOST = "http://192.168.1.106:8080/webapp/";

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
                            protected void onSuccess(String result) {
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
        String path= Environment.getExternalStorageDirectory() + File.separator + "jike" + File.separator + "commons-io-2.5.jar";
        File file = new File(path);
        Http.post()
                .url(HOST + "fileUploadPage")
                .file("file", file)
                .setConnectTimeout(60000)
                .setReadTimeout(60000)
                .setWriteTimeout(60000)
                .execute(new StringCallBack() {
            @Override
            protected void onFailed(@NonNull HttpError error) {
                int a = 0;
            }

            @Override
            protected void onSuccess(String result) {
                int a = 0;
            }
        });
    }

    private void download() {
        String path= Environment.getExternalStorageDirectory() + File.separator + "jike" + File.separator + "commons-io-2.5.jar";
        File file = new File(path);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"username\""),
                        RequestBody.create(null, "HGR"))
                .addPart(Headers.of(
                        "Content-Disposition",
                        "form-data; name=\"mFile\"; filename=\"" + file.getName() + "\""), fileBody)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.2.110:8080/webapp/upload3")
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.e(TAG, "failure upload!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i(TAG, "success upload!");
            }
        });
    }
}
