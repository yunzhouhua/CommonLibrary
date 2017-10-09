package com.yunzhou.libcommon.net.http.callback;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**文件回调
 * Created by huayunzhou on 2017/10/4.
 */

public abstract class FileCallBack extends Callback<File> {

    private static final int BUFFER_SIZE = 1024;

    private Handler mHandler;

    private String mPath;
    private String fileName;
    //是否需要进度监听
    private boolean progress;

    public FileCallBack(String path, String fileName){
        this.mPath = path;
        this.fileName = fileName;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public File parseResponse(final long id, @NonNull okhttp3.Response response) throws IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = 0;
        long sum = 0;

        is = response.body().byteStream();
        final long total = response.body().contentLength();

        File dir = new File(mPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
        File destFile = new File(dir, fileName);
        fos = new FileOutputStream(destFile);
        while((len = is.read(buffer)) != -1){
            fos.write(buffer, 0, len);
            sum += len;
            final long tmpSum = sum;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    updateProgress(id, tmpSum, total);
                }
            });
        }
        fos.flush();

        fos.close();
        is.close();
        if(response.body() != null){
            response.body().close();
        }

        return destFile;
    }
}
