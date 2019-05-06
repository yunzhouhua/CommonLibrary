package com.yunzhou.libcommon.app.annotation;

import android.graphics.Color;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with Android Studio.
 * Description: Activity, Fragment 初始化常规页面配置
 * User: huayunzhou
 * Date: 2019-04-12
 * Time: 16:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ActivityInjection {

    /**
     * 是否使用模板，如果使用模板，自带标题栏
     * 且标题栏的标题，背景，字体颜色可配置
     * @return
     */
    boolean useTemplate() default true;

    /**
     * 标题
     * @return
     */
    String title() default "";

    /**
     * 标题颜色
     * @return
     */
    int titleTextColorId() default android.R.color.white;

    /**
     * 标题，是否显示返回键
     * @return
     */
    boolean showBackIcon() default true;

    /**
     * 返回按钮，箭头颜色
     * @return
     */
    int backIconColorId() default android.R.color.white;

    /**
     * 标题背景资源
     * @return
     */
    int bgDrawableResId() default android.R.drawable.title_bar;

    /**
     * 页面使用的布局文件Id
     * @return
     */
    int contentViewId() default -1;
}
