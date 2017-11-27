package com.yunzhou.commonlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by huayunzhou on 2017/11/27.
 */

public class PhotoViewGroup extends RelativeLayout {
    public PhotoViewGroup(Context context) {
        super(context);
    }

    public PhotoViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e){
            //e.printStackTrace();
            return false;
        }
    }
}
