package com.yunzhou.libcommon.net.http.cookie;

import android.content.Context;
import com.yunzhou.libcommon.net.http.cookie.annotation.CookieType;
import com.yunzhou.libcommon.net.http.cookie.store.CookieStore;
import com.yunzhou.libcommon.net.http.cookie.store.MemoryCookieStore;
import com.yunzhou.libcommon.net.http.cookie.store.PersistentCookieStore;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 *  Cookie 管理
 * Created by huayunzhou on 2017/9/27.
 */

public class CookieManager implements CookieJar {
    private CookieStore mCookieStore;

    public CookieManager(Context context, @CookieType int type){
        switch (type){
            case CookieType.NONE:
                break;
            case CookieType.MEMORY:
                mCookieStore = new MemoryCookieStore();
                break;
            case CookieType.FILE:
                mCookieStore = new PersistentCookieStore(context);
                break;
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if(mCookieStore != null){
            mCookieStore.add(url, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if(mCookieStore != null){
            return mCookieStore.get(url);
        }else{
            return new ArrayList<>();
        }
    }

    public void clearCookie(){
        if(mCookieStore != null){
            mCookieStore.removeAll();
        }
    }
}
