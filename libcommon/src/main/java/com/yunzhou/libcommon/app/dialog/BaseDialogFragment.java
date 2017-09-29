package com.yunzhou.libcommon.app.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * 所有弹窗都继承这个类，
 * 该类再显示/取消Dialog时，有效避免系统奔溃
 * Created by huayunzhou on 2017/9/28.
 */
public class BaseDialogFragment extends DialogFragment {

    @Override
    public void show(FragmentManager manager, String tag) {
        /**
         * 重写show方法，拦截show可能抛出的异常
         * 避免Can not perform this action after onSaveInstanceState
         */
        try {
            super.show(manager, tag);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        /**
         * 使用允许状态丢失的方式取消Fragment
         * 避免Can not perform this action after onSaveInstanceState
         */
        dismissAllowingStateLoss();
    }
}
