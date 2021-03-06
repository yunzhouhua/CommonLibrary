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

package com.yunzhou.libcommon.net.http.request;


import com.yunzhou.libcommon.net.http.Method;
import okhttp3.RequestBody;

/**
 * Created by Bright.Yu on 2017/2/16.
 * Get Request
 */

@SuppressWarnings("unused")
public class GetRequest extends Request<GetRequest> {
    public GetRequest() {
        super(Method.GET);
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

    @Override
    protected okhttp3.Request buildRequest(RequestBody requestBody) {
        return getBuilder().get().build();
    }
}
