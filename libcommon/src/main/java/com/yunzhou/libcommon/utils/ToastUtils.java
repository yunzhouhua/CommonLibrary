package com.yunzhou.libcommon.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created with Android Studio.
 * Description: Toast工具类
 * User: huayunzhou
 * Date: 2018-04-19
 * Time: 16:30
 */

public class ToastUtils {

    // todo 动画

    /**
     * 显示普通的Toast
     * @param context   context
     * @param msg       消息
     */
    public static void show(Context context, String msg){
        if(context == null){
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示可配置gravity的TOAST
     * @param context   context
     * @param msg       消息
     * @param gravity   gravity
     */
    public static void show(Context context, String msg, int gravity){
        show(context, msg, gravity, 0, 0);
    }


    /**
     * 显示可配置gravity的TOAST
     * @param context   context
     * @param msg       消息
     * @param gravity   gravity
     * @param xOffset   X轴偏移量
     * @param yOffset   Y轴偏移量
     */
    public static void show(Context context, String msg, int gravity, int xOffset, int yOffset){
        if(context == null){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.show();
    }
}
