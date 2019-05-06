package com.yunzhou.libcommon.app.activity;

import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yunzhou.libcommon.R;
import com.yunzhou.libcommon.app.annotation.ActivityInjection;

/**
 * Created with Android Studio.
 * Description:
 * User: huayunzhou
 * Date: 2019-05-06
 * Time: 15:51
 */
public abstract class InjectionActivity extends BaseActivity {

    private RelativeLayout mTitleGroupView = null;
    private TextView mTitleView = null;
    private ImageView mTitleBackView = null;
    private FrameLayout mContentView = null;

    @Override
    protected void loadViewLayout() {
        if (!getClass().isAnnotationPresent(ActivityInjection.class)) {
            throw new RuntimeException("You must add annotation of ActivityInjection.class to this activity");
        }
        ActivityInjection annotation = getClass().getAnnotation(ActivityInjection.class);
        boolean useTemplate = annotation.useTemplate();
        int layoutResId = annotation.contentViewId();
        if (useTemplate) {
            // 使用模板，将用户设置的布局资源添加至模板布局中，并做对应的设置操作
            setContentView(R.layout.activity_cmn_template);
            mTitleGroupView = (RelativeLayout) findViewById(R.id.cmn_title_group);
            mTitleView = (TextView) findViewById(R.id.cmn_title);
            mTitleBackView = (ImageView) findViewById(R.id.cmn_title_ic_back);
            mContentView = (FrameLayout) findViewById(R.id.cmn_content);

            LayoutInflater.from(this).inflate(layoutResId, mContentView, true);

            mTitleGroupView.setBackgroundResource(annotation.bgDrawableResId());
            mTitleView.setText(annotation.title());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTitleView.setTextColor(getResources().getColor(annotation.titleTextColorId(), getTheme()));
            } else {
                mTitleView.setTextColor(getResources().getColor(annotation.titleTextColorId()));
            }

            boolean showBackIcon = annotation.showBackIcon();
            if (showBackIcon) {
                mTitleBackView.setVisibility(View.VISIBLE);
                int titleBackColor = annotation.backIconColorId();
                VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.cmn_ic_back_dark, getTheme());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    vectorDrawableCompat.setTint(getResources().getColor(titleBackColor, getTheme()));
                } else {
                    vectorDrawableCompat.setTint(getResources().getColor(titleBackColor));
                }
                mTitleBackView.setImageDrawable(vectorDrawableCompat);
                mTitleBackView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickBackIcon();
                    }
                });

            } else {
                mTitleBackView.setVisibility(View.GONE);
            }

        } else {
            // 不使用模板，直接加载用户设置的布局资源
            setContentView(layoutResId);
        }
    }

    protected void clickBackIcon() {
        finish();
    }
}
