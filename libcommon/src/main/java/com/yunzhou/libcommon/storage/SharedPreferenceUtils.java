package com.yunzhou.libcommon.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * 使用Android中SharedPreference机制实现本地数据存储
 * Created by huayunzhou on 2017/9/14.
 */

@SuppressWarnings("unused")
public class SharedPreferenceUtils {

    /**
     * 正常的SharedPreference名称
     * 存储跟版本信息有关的信息，App版本升级后可用于清空
     */
    private static final String SHARED_DATA = "shared_data";

    /**
     * 正常的SharedPreference名称
     * 存储跟版本信息无关的信息，App版本升级还需要其数据有价值
     */
    private static final String SHARED_GLOBAL_DATA = "shared_global_data";

    /**
     * 对应SHARED_DATA
     */
    private static WeakReference<SharedPreferences> mSharedData;
    /**
     * 对应SHARED_GLOBAL_DATA
     */
    private static WeakReference<SharedPreferences> mSharedGlobalData;

    @SuppressWarnings("unused")
    public static SharedPreferences getSharedPreferences(Context context){
        return getSharedPreferences(context, false);
    }

    @SuppressWarnings("WeakerAccess")
    public static SharedPreferences getSharedPreferences(Context context, boolean isGlobal){
        SharedPreferences sharedPreferences;
        if(isGlobal){
            //获取 SHARED_GLOBAL_DATA
            if(mSharedGlobalData == null || mSharedGlobalData.get() == null){
                sharedPreferences = context.getSharedPreferences(SHARED_GLOBAL_DATA, Context.MODE_PRIVATE);
                mSharedGlobalData = new WeakReference<>(sharedPreferences);
            }else{
                sharedPreferences = mSharedGlobalData.get();
            }
        }else{
            //获取 SHARED_DATA
            if(mSharedData == null || mSharedData.get() == null){
                sharedPreferences = context.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE);
                mSharedData = new WeakReference<>(sharedPreferences);
            }else{
                sharedPreferences = mSharedData.get();
            }
        }
        return sharedPreferences;
    }

    /**
     * 存储数据
     * @param context   context
     * @param key       键
     * @param value     值
     */
    @SuppressWarnings("unused")
    public void save(Context context, String key, Object value){
        save(context, key, value, false);
    }

    /**
     * 存储数据
     * @param context   context
     * @param key       键
     * @param value     值
     * @param isGlobal    该数据是否是全局存储
     */
    @SuppressWarnings(value = {"WeakerAccess", "unchecked"})
    public void save(Context context, String key, Object value, boolean isGlobal){
        if(context == null){
            return ;
        }
        if(TextUtils.isEmpty(key)){
            throw new IllegalArgumentException("SharedPreference key can't be null");
        }
        SharedPreferences.Editor editor = getSharedPreferences(context, isGlobal).edit();
        if(value == null || value instanceof String){
            editor.putString(key, (String)value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean)value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer)value);
        }else if(value instanceof Float){
            editor.putFloat(key, (Float)value);
        }else if(value instanceof Long){
            editor.putLong(key, (Long)value);
        } else if (value instanceof Set) {
            editor.putStringSet(key, (Set<String>)value);
        } else{
            throw new IllegalArgumentException("SharedPreference value is illegal");
        }

        editor.apply();
    }


    /**
     * 获取数据
     * @param context           context
     * @param key               键
     * @param defaultValue      默认值
     * @return  T
     */
    @SuppressWarnings("unused")
    public static <T> T get(Context context, String key, T defaultValue){
        return get(context, key, defaultValue, false);
    }

    /**
     * 获取数据
     * @param context           context
     * @param key               键
     * @param defaultValue      默认值
     * @param isGlobal          是否是全局
     * @return  T
     */
    @SuppressWarnings(value = {"WeakerAccess", "unchecked"})
    public static <T> T get(Context context, String key, T defaultValue, boolean isGlobal){
        if(context == null){
            return null;
        }
        if(TextUtils.isEmpty(key)){
            throw new IllegalArgumentException("SharedPreference key can't be null");
        }
        SharedPreferences sp = getSharedPreferences(context, isGlobal);
        Object result = null;
        if(defaultValue == null || defaultValue instanceof String){
            result = sp.getString(key, (String)defaultValue);
        }else if(defaultValue instanceof Boolean){
            result = sp.getBoolean(key, (Boolean)defaultValue);
        }else if(defaultValue instanceof Integer){
            result = sp.getInt(key, (Integer)defaultValue);
        }else if(defaultValue instanceof Float){
            result = sp.getFloat(key, (Float)defaultValue);
        }else if(defaultValue instanceof Long){
            result = sp.getLong(key, (Long)defaultValue);
        }else if(defaultValue instanceof Set){
            result = sp.getStringSet(key, (Set<String>)defaultValue);
        }
        if(result != null){
            return (T)result;
        }
        return null;
    }

    /**
     * 删除某条记录
     * @param context       context
     * @param key           键
     */
    public void remove(Context context, String key){
        remove(context, key, false);
    }

    /**
     * 删除某条记录
     * @param context       context
     * @param key           键
     * @param isGlobal      是否全局
     */
    private void remove(Context context, String key, boolean isGlobal) {
        if(context == null){
            return;
        }
        if(TextUtils.isEmpty(key)){
            throw new IllegalArgumentException("SharedPreference key can't be null");
        }
        SharedPreferences.Editor editor = getSharedPreferences(context, isGlobal).edit();
        editor.remove(key).apply();
    }


    /**
     * 清空所有
     * @param context   context
     */
    public static void clean(Context context){
        clean(context, false);
    }


    /**
     * 清空所有
     * @param context       context
     * @param isGlobal      是否全局
     */
    private static void clean(Context context, boolean isGlobal) {
        if(context == null){
            return ;
        }
        SharedPreferences.Editor editor = getSharedPreferences(context, isGlobal).edit();
        editor.clear().apply();

    }

}
