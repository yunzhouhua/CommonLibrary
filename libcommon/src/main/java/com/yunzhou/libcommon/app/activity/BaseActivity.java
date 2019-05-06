package com.yunzhou.libcommon.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(savedInstanceState);
    }

    private void initActivity(Bundle savedInstanceState) {
        loadViewLayout();
        bindViews();
        setListener();
        processLogic(savedInstanceState);

    }

    /**
     * 加载布局
     */
    protected abstract void loadViewLayout();

    /**
     * 绑定views，
     * eg: findViewById
     */
    protected void bindViews(){}

    /**
     * 设置事件监听
     */
    protected void setListener(){}

    /**
     * 处理数据
     * @param savedInstanceState    savedInstanceState
     */
    protected abstract void processLogic(Bundle savedInstanceState);

}
