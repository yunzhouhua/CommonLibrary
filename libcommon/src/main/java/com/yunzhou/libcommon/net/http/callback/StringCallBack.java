/*
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yunzhou.libcommon.net.http.callback;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yunzhou.libcommon.net.http.exception.HttpErrorException;
import com.yunzhou.libcommon.net.http.request.Request;
import com.yunzhou.libcommon.net.http.response.Response;


/**
 * Created by bright.yu on 2017/2/28.
 * String CallBack
 */
public abstract class StringCallBack extends Callback<String> {
    @SuppressWarnings("WeakerAccess")
    public StringCallBack() {
        super();
    }

    public StringCallBack(Context context) {
        super(context);
    }

    @Override
    public String parseResponse(@NonNull final Request request, @NonNull final Response response) throws HttpErrorException {
        String body = new String(response.getBody());
        return body;
    }
}
