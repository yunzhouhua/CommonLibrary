package com.yunzhou.libcommon.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * 获取安装包相关的信息，比如版本号，版本名称，meta-data信息等
 * Created by huayunzhou on 2017/9/14.
 */

public class PackageInfoUtils {

    /**
     * 获取当前App VersionCode
     */
    public static int getVersionCode(Context context){
        if(context == null){
            return 0;
        }
        try {
            return context.getPackageManager().
                    getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA).
                    versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取当前App版本名称
     */
    public static String getVersionName(Context context){
        if(context == null){
            return null;
        }
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA)
                    .versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key，获取AndroidManifest.xml中meta-data的值
     */
    public static String getMetaDataValue(Context context, String key){
        if(context == null){
            return null;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return applicationInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }


}
