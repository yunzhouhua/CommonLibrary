package com.yunzhou.commonlibrary.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.libcommon.utils.PixelUtils;
import com.yunzhou.libcommon.views.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class FlowLayoutActivity extends AppCompatActivity {

    private static final String TAG = "FlowLayoutActivity";

    private FlowLayout mFlowLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        mFlowLayout = (FlowLayout) findViewById(R.id.flowlayout);

        List<String> keys = new ArrayList<>();
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");
        keys.add("园林一卡通");
        keys.add("美食");
        keys.add("东北滑雪");

        for(String key : keys){
            if(!TextUtils.isEmpty(key)){
                mFlowLayout.addView(getSearchRecordView(this, key));
            }
        }
    }

    private TextView getSearchRecordView(Context context, final String searchKey){
        TextView searchRecord = new TextView(context);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, PixelUtils.dip2px(32));
        searchRecord.setLayoutParams(lp);
        searchRecord.setGravity(Gravity.CENTER);
        searchRecord.setPadding(PixelUtils.dip2px(15), 0, PixelUtils.dip2px(15), 0);
        searchRecord.setTextColor(Color.BLACK);
        searchRecord.setTextSize(14);
        searchRecord.setText(searchKey);
        searchRecord.setBackgroundResource(R.drawable.bg_grey_radius_999);
        searchRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 历史点击事件
                Log.e(TAG, "onClick: " + searchKey);
            }
        });

        return searchRecord;
    }
}
