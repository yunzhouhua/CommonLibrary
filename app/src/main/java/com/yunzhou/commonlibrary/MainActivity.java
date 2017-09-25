package com.yunzhou.commonlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yunzhou.commonlibrary.bean.Son;
import com.yunzhou.libcommon.net.NetStatusUtils;
import com.yunzhou.libcommon.storage.ACache;
import com.yunzhou.libcommon.storage.SharedPreferenceUtils;
import com.yunzhou.libcommon.utils.DateFormatUtils;
import com.yunzhou.libcommon.utils.PackageInfoUtils;
import com.yunzhou.libcommon.utils.PixelUtils;
import com.yunzhou.libcommon.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("aa", "" + ScreenUtils.getStatusBarHeight(this));

        Log.e("aa", "=======================单位变换=========================");
        Log.e("aa", "5dp =>" + PixelUtils.dip2px(5) + "px");
        Log.e("aa", "5px =>" + PixelUtils.px2dip(5) + "dp");
        Log.e("aa", "5sp =>" + PixelUtils.sp2px(5) + "px");
        Log.e("aa", "5px =>" + PixelUtils.px2sp(5) + "sp");

        Log.e("aa", "=======================应用信息=========================");
        Log.e("aa", "versionCode : " + PackageInfoUtils.getVersionCode(this));
        Log.e("aa", "versionName : " + PackageInfoUtils.getVersionName(this));
        Log.e("aa", "meta-data : " + PackageInfoUtils.getMetaDataValue(this, "data_name"));

        Log.e("aa", "=======================SharedPreference=========================");
        SharedPreferenceUtils.save(this, "demaxiya", "demaxiya");
        SharedPreferenceUtils.save(this, "demaxiya", "demaxiya_global", true);
        Log.e("aa", "SharedPreference : " + SharedPreferenceUtils.get(this, "demaxiya", "123"));
        Log.e("aa", "SharedPreference_global : " + SharedPreferenceUtils.get(this, "demaxiya", "123", true));
        SharedPreferenceUtils.clean(this);
        SharedPreferenceUtils.remove(this, "demaxiya", true);
        Log.e("aa", "删除SharedPreference");
        Log.e("aa", "SharedPreference : " + SharedPreferenceUtils.get(this, "demaxiya", "123"));
        Log.e("aa", "SharedPreference_global : " + SharedPreferenceUtils.get(this, "demaxiya", "123", true));

        Log.e("aa", "=======================ACache=========================");
        ACache cache = ACache.get(this);
        cache.put("acache", "acache");
        Log.e("aa", "结果 ： " + cache.getAsString("acache"));

        Log.e("aa", "=======================时间格式化=========================");
        Log.e("aa", Thread.currentThread().getId() + " | " + DateFormatUtils.format(new Date(), DateFormatUtils.FORMAT_YMD_HMS_1));
        Log.e("aa", Thread.currentThread().getId() + " | " + DateFormatUtils.format(new Date(), DateFormatUtils.FORMAT_YMD_HMS_2));
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                Log.e("aa", Thread.currentThread().getId() + " | " + DateFormatUtils.format(new Date(), DateFormatUtils.FORMAT_YMD_HMS_1));
//                Log.e("aa", Thread.currentThread().getId() + " | " + DateFormatUtils.format(new Date(), DateFormatUtils.FORMAT_YMD_HMS_2));
//            }
//        }.start();

        Log.e("aa", "=======================fastJson=========================");
        String jsonSon = "{\"name\":\"demaxiya\",\"birthday\":\"2017-09-23 12:36:22\"}";
        Son son = JSON.parseObject(jsonSon, Son.class);
        Log.e("aa", "son : " + son.toString());
        List<Son> sons = new ArrayList<>();
        Son son1 = new Son("makeboluo", new Date());
        sons.add(son1);
        sons.add(son1);
        sons.add(son1);
        Log.e("aa", "sons : " + JSON.toJSONString(sons, SerializerFeature.DisableCircularReferenceDetect));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(new Son("zhagnsan", new Date()));
        jsonArray.add(new Son("lisi", new Date()));
        jsonArray.add(new Son("wangwu", new Date()));
        Log.e("aa", "jsonArray : " + jsonArray.toJSONString());

        String jsonSonList = "[{\"birthday\":1506140869111,\"name\":\"zhagnsan\"},{\"birthday\":1506140869112,\"name\":\"lisi\"},{\"birthday\":1506140869112,\"name\":\"wangwu\"}]";
        List<Son> sonList = JSON.parseObject(jsonSonList, List.class);
        sonList.size();

        Log.e("aa", "=======================net connect status=========================");
        Log.e("aa", NetStatusUtils.getConnectionType(this));
    }
}
