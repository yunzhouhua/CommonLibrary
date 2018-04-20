package com.yunzhou.libcommon.utils;

import android.content.Context;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created with Android Studio.
 * Description: Toast工具类
 * User: huayunzhou
 * Date: 2018-04-19
 * Time: 16:30
 */

public class ToastUtils {

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

    /**
     * 显示自定义动画的Toast
     * @param context       context
     * @param msg           消息
     * @param animStyle     动画
     */
    public static void showAnim(Context context, String msg, int animStyle){
        if(context == null){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        initAnimForToast(toast, animStyle);
        toast.show();
    }

    /**
     * 显示自定义动画的Toast
     * @param context       context
     * @param msg           消息
     * @param gravity       gravity
     * @param animStyle     动画
     */
    public static void showAnim(Context context, String msg, int gravity, int animStyle){
        showAnim(context, msg, gravity, 0, 0, animStyle);
    }

    /**
     * 显示自定义动画的Toast
     * @param context       context
     * @param msg           消息
     * @param gravity       gravity
     * @param xOffset       X轴偏移量
     * @param yOffset       Y轴偏移量
     * @param animStyle     动画
     */
    public static void showAnim(Context context, String msg, int gravity, int xOffset, int yOffset, int animStyle){
        if(context == null){
            return;
        }
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, xOffset, yOffset);
        initAnimForToast(toast, animStyle);
        toast.show();
    }

    /**
     * 为Toast设置进入/离开动画
     * 通过反射，获取WindowManager.LayoutParams对象，设置其windowAnimations属性，从而实现了修改动画的功能
     * @param toast         Toast对象
     * @param animStyle     动画style
     */
    @SuppressWarnings("unchecked")
    private static void initAnimForToast(Toast toast, int animStyle){
        if(toast == null){
            return;
        }
        Class<Toast> clazz = (Class<Toast>) toast.getClass();
        try {
            Field mTN = clazz.getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object obj = mTN.get(toast);

            Field mParamsField = obj.getClass().getDeclaredField("mParams");
            if(mParamsField != null){
                mParamsField.setAccessible(true);
                Object mParams = mParamsField.get(obj);
                if(mParams != null && mParams instanceof WindowManager.LayoutParams){
                    WindowManager.LayoutParams lp = (WindowManager.LayoutParams) mParams;
                    lp.windowAnimations = animStyle;
                }
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
