package com.yunzhou.libcommon.route;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yunzhou.libcommon.R;

import java.io.Serializable;

/**
 *  路由跳转
 * Created by huayunzhou on 2017/9/29.
 */

public class RouteManager {
    private Context mContext;
    private Route mRoute;

    /**
     * 如果需要跳转动画，context需要传入Activity实例
     * 如果需要startActivityForResult,context需要传入Activity实例
     * @param context
     * @param route
     */
    public RouteManager(@NonNull Context context, @NonNull Route route){
        this.mContext = context;
        this.mRoute = route;
    }

    /**
     * 如果需要跳转动画，context需要传入Activity实例
     * 如果需要startActivityForResult,context需要传入Activity实例
     * @param context
     * @param cls
     * @return
     */
    public static RouteManager target(@NonNull  Context context, Class<?> cls){
        Route route = new Route(context, cls);
        return new RouteManager(context, route);
    }

    public static  RouteManager target(@NonNull Context context, @NonNull Uri uri){
        Route route = new Route(uri);
        return new RouteManager(context, route);
    }

    public RouteManager target(Context context, String scheme, String host, String path){
        if(TextUtils.isEmpty(scheme) || TextUtils.isEmpty(host) || TextUtils.isEmpty(path)){
            throw new IllegalArgumentException("Scheme route error; sechme/host/path can't be null!");
        }
        StringBuilder uriBuilder = new StringBuilder(scheme);
        uriBuilder.append("://").append(host).append(path);
        Uri uri = Uri.parse(uriBuilder.toString());
        return target(context, uri);
    }

    public RouteManager addInt(String key, int value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putInt(key, value);
        }
        return this;
    }

    public RouteManager addLong(String key, long value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putLong(key, value);
        }
        return this;
    }

    public RouteManager addFloat(String key, float value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putFloat(key, value);
        }
        return this;
    }

    public RouteManager addDouble(String key, double value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putDouble(key, value);
        }
        return this;
    }

    public RouteManager addBoolean(String key, boolean value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putBoolean(key, value);
        }
        return this;
    }

    public RouteManager addString(String key, String value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putString(key, value);
        }
        return this;
    }

    public RouteManager addSerializable(String key, Serializable value){
        if(!TextUtils.isEmpty(key)) {
            this.getRouteBundle().putSerializable(key, value);
        }
        return this;
    }

    public RouteManager addBundle(Bundle bundle){
        if(bundle != null){
            for(String key : bundle.keySet()){
                if(bundle.get(key) == null){
                    //如果key对应的值为null时，把他看作为Serializable
                    this.getRouteBundle().putSerializable(key, null);
                }else if(bundle.get(key) instanceof  Integer){
                    this.getRouteBundle().putInt(key, bundle.getInt(key, 0));
                }else if(bundle.get(key) instanceof Long){
                    this.getRouteBundle().putLong(key, bundle.getLong(key, 0L));
                }else if(bundle.get(key) instanceof Float){
                    this.getRouteBundle().putFloat(key, bundle.getFloat(key, 0.0F));
                }else if(bundle.get(key) instanceof Double){
                    this.getRouteBundle().putDouble(key, bundle.getDouble(key, 0D));
                }else if(bundle.get(key) instanceof Boolean){
                    this.getRouteBundle().putBoolean(key, bundle.getBoolean(key, false));
                }else if(bundle.get(key) instanceof String){
                    this.getRouteBundle().putString(key, bundle.getString(key, null));
                }else if(bundle.get(key) instanceof Serializable){
                    this.getRouteBundle().putSerializable(key, bundle.getSerializable(key));
                }
            }
            bundle.clear();
            bundle = null;
        }
        return this;
    }

    public RouteManager addFlags(int flags){
        this.getRoute().getIntent().addFlags(flags);
        return this;
    }

    public RouteManager setFlags(int flags){
        this.getRoute().getIntent().setFlags(flags);
        return this;
    }

    public RouteManager forResult(int requestCode){
        this.getRoute().setForResult(true);
        this.getRoute().setRequestCode(requestCode);
        return this;
    }

    /**
     * Activity过场动画必须mContext为Activity时设置才有效，
     * 否则无法生效
     * @return
     */
    public RouteManager pendingTransition(int anim_in, int anim_out){
        if(this.mContext != null && this.mContext instanceof Activity){
            this.getRoute().setAnimIn(anim_in);
            this.getRoute().setAnimOut(anim_out);
            this.getRoute().setAnimChanged(true);
        }
        return this;
    }

    public void go(){
        this.getRoute().getIntent().putExtras(this.getRoute().getBundle());
        if(mContext != null){
            boolean routeSuccess = false;
            if(this.getRoute().isForResult() && mContext instanceof Activity){
                routeSuccess = true;
                ((Activity)mContext).startActivityForResult(this.getRoute().getIntent(),
                        this.getRoute().getRequestCode());
            }else{
                routeSuccess = true;
                mContext.startActivity(this.getRoute().getIntent());
            }
            //成功跳转，且设置了过场动画
            if(routeSuccess && mContext instanceof Activity &&
                    this.getRoute().isAnimChanged()){
                ((Activity)mContext).overridePendingTransition(this.getRoute().getAnimIn(),
                        this.getRoute().getAnimOut());
            }
        }
    }

    /**
     * 实现从右往左平移Activity跳转
     */
    public void goRightSlide(){
        this.pendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        go();
    }

    /**
     * 实现从左往右平移Activity跳转
     */
    public void goLeftSlide(){
        this.pendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        go();
    }

    /**
     * 无任务过场进行Activity跳转
     */
    public void goSilence(){
        this.pendingTransition(0, 0);
        go();
    }


    private Bundle getRouteBundle(){
        return this.getRoute().getBundle();
    }
    private Route getRoute(){
        return mRoute;
    }


}
