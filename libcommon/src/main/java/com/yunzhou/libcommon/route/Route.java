package com.yunzhou.libcommon.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by huayunzhou on 2017/9/29.
 */

public class Route {
    private Intent mIntent;
    private Bundle mBundle;

    //是否设置了过场动画
    private boolean animChanged;
    private int mAnimIn;
    private int mAnimOut;

    /**
     * startActivityForResult相关
     */
    private boolean isForResult;
    private int mRequestCode;


    public Route(Context context, Class<?> cls){
        this.mIntent = new Intent(context, cls);
        init();
    }

    public Route(Uri uri){
        this.mIntent = new Intent(Intent.ACTION_VIEW, uri);
        init();
    }

    private void init(){
        this.mBundle = null;
        this.animChanged = false;
        this.isForResult = false;
        this.mRequestCode = 0;
    }

    public Intent getIntent() {
        return mIntent;
    }

    public void setIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

    public Bundle getBundle() {
        if(mBundle == null){
            mBundle = new Bundle();
        }
        return mBundle;
    }

    public void setBundle(Bundle mBundle) {
        this.mBundle = mBundle;
    }

    public int getAnimIn() {
        return mAnimIn;
    }

    public void setAnimIn(int mAnimIn) {
        this.mAnimIn = mAnimIn;
    }

    public int getAnimOut() {
        return mAnimOut;
    }

    public void setAnimOut(int mAnimOut) {
        this.mAnimOut = mAnimOut;
    }

    public boolean isForResult() {
        return isForResult;
    }

    public void setForResult(boolean forResult) {
        isForResult = forResult;
    }

    public int getRequestCode() {
        return mRequestCode;
    }

    public void setRequestCode(int requestCode) {
        this.mRequestCode = requestCode;
    }

    public boolean isAnimChanged() {
        return animChanged;
    }

    public void setAnimChanged(boolean animChanged) {
        this.animChanged = animChanged;
    }
}
