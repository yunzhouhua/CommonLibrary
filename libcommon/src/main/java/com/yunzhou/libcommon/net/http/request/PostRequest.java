package com.yunzhou.libcommon.net.http.request;

import com.yunzhou.libcommon.net.http.Method;


/**
 * Created by huayunzhou on 2017/9/28.
 */

public class PostRequest extends Request<PostRequest> {
    public PostRequest() {
        super(Method.POST);
    }
}
