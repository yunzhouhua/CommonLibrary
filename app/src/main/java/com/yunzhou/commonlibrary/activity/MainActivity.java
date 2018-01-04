package com.yunzhou.commonlibrary.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.bean.Son;
import com.yunzhou.commonlibrary.tab.activity.TabHostActivity;
import com.yunzhou.libcommon.net.NetStatusUtils;
import com.yunzhou.libcommon.route.RouteManager;
import com.yunzhou.libcommon.storage.ACache;
import com.yunzhou.libcommon.storage.SharedPreferenceUtils;
import com.yunzhou.libcommon.utils.DateFormatUtils;
import com.yunzhou.libcommon.utils.PackageInfoUtils;
import com.yunzhou.libcommon.utils.PixelUtils;
import com.yunzhou.libcommon.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.round_img).setOnClickListener(this);
        findViewById(R.id.http).setOnClickListener(this);
        findViewById(R.id.glide).setOnClickListener(this);
        findViewById(R.id.refresh_load_more).setOnClickListener(this);
        findViewById(R.id.banner).setOnClickListener(this);
        findViewById(R.id.img_compress).setOnClickListener(this);
        findViewById(R.id.tab_host).setOnClickListener(this);
        findViewById(R.id.tab_home).setOnClickListener(this);
        findViewById(R.id.photoView).setOnClickListener(this);
        findViewById(R.id.photoview_viewpager).setOnClickListener(this);
        findViewById(R.id.btn_flowlayout).setOnClickListener(this);

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

        Log.e("aa", "=======================ArrayMap=========================");
        ArrayMap<Integer, String> map = new ArrayMap<>();
        map.put(1, "Macgrady");
        map.put(3, "Wade");
        map.put(23, "Jordan");
        map.put(24, "Kobe");
        Log.e("aa", "ArrayMap Size : " + map.size());
        for(int i = 0; i < map.size(); i++){
            Log.e("aa", map.keyAt(i) + " : " + map.valueAt(i));
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.http:
//                intent = new Intent(this, HttpTestActivity.class);
//                startActivity(intent);
                RouteManager.target(this, HttpTestActivity.class)
                        .addInt("int", 12)
                        .addFloat("float", 12.6f)
                        .addLong("long", 123l)
                        .addDouble("dounle", 345d)
                        .addBoolean("boolean", true)
                        .addSerializable("serializable", null)
                        .addString("string", null)
                        .goLeftSlide();
                break;
            case R.id.round_img:
                RouteManager.target(this, RoundImgActivity.class)
                        .goSilence();
                break;
            case R.id.glide:
                RouteManager.target(this, GlideActivity.class)
                        .goSilence();
                break;
            case R.id.refresh_load_more:
                RouteManager.target(this, RefreshListActivity.class)
                        .goRightSlide();
                break;
            case R.id.banner:
                RouteManager.target(this, BannerActivity.class)
                        .goRightSlide();
                break;
            case R.id.img_compress:
                RouteManager.target(this, ImageCompressActivity.class)
                        .goRightSlide();
                break;
            case R.id.tab_host:
                RouteManager.target(this, TabHostActivity.class)
                        .goRightSlide();
                break;
            case R.id.tab_home:
                RouteManager.target(this, HomeTabActivity.class)
                        .goRightSlide();
                break;
            case R.id.photoView:
                RouteManager.target(this, PhotoViewActivity.class)
                        .goRightSlide();
                break;
            case R.id.photoview_viewpager:
                RouteManager.target(this, PhotoViewPagerActivity.class)
                        .goRightSlide();
                break;
            case R.id.btn_flowlayout:
                RouteManager.target(this, FlowLayoutActivity.class)
                        .goRightSlide();
                break;
        }
    }
}
