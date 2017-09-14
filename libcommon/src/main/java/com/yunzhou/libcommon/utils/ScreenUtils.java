package com.yunzhou.libcommon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * 屏幕尺寸相关，获取屏幕宽度，高度等等
 * Created by huayunzhou on 2017/9/14.
 */

@SuppressWarnings("unused")
public class ScreenUtils {

    /**
     * 获取屏幕的宽和高
     */
    public static int[] getScreenSize() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return new int[]{displayMetrics.widthPixels, displayMetrics.heightPixels};
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(){
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(){
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context){
        if(context == null){
            return 0;
        }
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    /**
     * 获取除去状态栏高度的屏幕高度
     */
    public static int getScreenHeightWithoutStatusbar(Context context){
        return context != null ?
                (getScreenHeight() - getStatusBarHeight(context)) : 0;
    }

}
