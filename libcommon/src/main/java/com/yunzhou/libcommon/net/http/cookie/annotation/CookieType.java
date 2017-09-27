package com.yunzhou.libcommon.net.http.cookie.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * cookie存储类型注解
 * Created by huayunzhou on 2017/9/27.
 */
@IntDef({CookieType.NONE, CookieType.MEMORY, CookieType.FILE})
@Retention(RetentionPolicy.SOURCE)
public @interface CookieType {

    /**
     * 不使用Cookie
     */
    int NONE = 0;
    /**
     * Cookie保存在内存，退出后移除
     */
    int MEMORY = 1;
    /**
     * Cookie保存在文件(目前保存于SharedPreferences), 退出后移除
     */
    int FILE = 2;
}
