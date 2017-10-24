package com.yunzhou.commonlibrary.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.libcommon.utils.DataCleanUtils;
import com.yunzhou.libcommon.utils.ImageUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCompressActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ImageCompressActivity";

    private String mPath = Environment.getExternalStorageDirectory() + File.separator + "jike";

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_compress);

        mImageView = (ImageView) findViewById(R.id.imageView);


        findViewById(R.id.img_compress).setOnClickListener(this);

        mImageView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = ImageUtils.getSampleBitmap(getApplicationContext(), R.mipmap.timg);
//                mImageView.setImageResource(R.mipmap.timg);
                mImageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_compress:
                compress();
                break;
        }
    }

    private void compress() {
        File origin = new File(mPath, "super_large_image.jpg");
        if(origin.exists()){
            Log.e(TAG, "原始文件大小 ： " + DataCleanUtils.getFormatSize(origin.length()));
            Bitmap originBitmap = BitmapFactory.decodeFile(origin.getAbsolutePath());
            Log.e(TAG, "原始Bitmap尺寸 ： " + originBitmap.getWidth() + " x " + originBitmap.getHeight());
            Log.e(TAG, "原始Bitmap大小 ： " + originBitmap.getByteCount());
            //compressBitmap(originBitmap, 200 * 1024);

            Log.e(TAG, "原图旋转角度" + ImageUtils.getExifOrientation(origin.getAbsolutePath()));
            Log.e(TAG, "压缩图图旋转角度" + ImageUtils.getExifOrientation(new File(mPath, "super_large_img_bak.jpg").getAbsolutePath()));
        }
    }

    private void compressBitmap(Bitmap originBitmap, int size) {
        if (originBitmap.getByteCount() < size) {
            return ;
        }
        File output = new File(mPath, "super_large_img_bak.jpg");
        int quality = (int) (100 * 0.9);
        ByteArrayOutputStream baos = null;
        do {
                baos = new ByteArrayOutputStream();
                originBitmap.compress(Bitmap.CompressFormat.WEBP, quality, baos);
                Log.e(TAG, "压缩质量 : " + quality);
                Log.e(TAG, "压缩后文件大小 ： " + baos.size());
                quality *= 0.9;
        } while (baos.size() > size && quality > 0/*false*/) ;

        //存储文件
        try {
            FileOutputStream fos = new FileOutputStream(output);
            fos.write(baos.toByteArray());
            fos.flush();
            Log.e(TAG, "文件写入成功");
            baos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
