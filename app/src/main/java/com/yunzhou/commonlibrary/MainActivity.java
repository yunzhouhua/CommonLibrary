package com.yunzhou.commonlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.yunzhou.libcommon.storage.SharedPreferenceUtils;
import com.yunzhou.libcommon.utils.PackageInfoUtils;
import com.yunzhou.libcommon.utils.PixelUtils;
import com.yunzhou.libcommon.utils.ScreenUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("aa", "" + ScreenUtils.getStatusBarHeight(this));

        Log.e("aa", "=======================单位变换=========================");
        Log.e("aa", "5dp =>" + PixelUtils.dip2px(5) + "px");
        Log.e("aa", "5px =>" + PixelUtils.px2dip(5) + "dp");
        Log.e("aa", "5sp =>" + PixelUtils.sp2px(5) + "px");
        Log.e("aa", "5px =>" + PixelUtils.px2sp(5) + "sp");

        Log.e("aa", "=======================应用信息=========================");
        Log.e("aa", "versionCode : " + PackageInfoUtils.getVersionCode(this));
        Log.e("aa", "versionName : " + PackageInfoUtils.getVersionName(this));
        Log.e("aa", "meta-data : " + PackageInfoUtils.getMetaDataValue(this, "data_name"));

        Log.e("aa", "=======================SharedPreference=========================");
        SharedPreferenceUtils.save(this, "demaxiya", "demaxiya");
        SharedPreferenceUtils.save(this, "demaxiya", "demaxiya_global", true);
        Log.e("aa", "SharedPreference : " + SharedPreferenceUtils.get(this, "demaxiya", "123"));
        Log.e("aa", "SharedPreference_global : " + SharedPreferenceUtils.get(this, "demaxiya", "123", true));
        SharedPreferenceUtils.clean(this);
        SharedPreferenceUtils.remove(this, "demaxiya", true);
        Log.e("aa", "删除SharedPreference");
        Log.e("aa", "SharedPreference : " + SharedPreferenceUtils.get(this, "demaxiya", "123"));
        Log.e("aa", "SharedPreference_global : " + SharedPreferenceUtils.get(this, "demaxiya", "123", true));
    }
}
