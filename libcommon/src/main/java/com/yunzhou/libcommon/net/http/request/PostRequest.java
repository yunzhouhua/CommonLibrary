package com.yunzhou.libcommon.net.http.request;


import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import com.yunzhou.libcommon.net.http.MediaType;
import com.yunzhou.libcommon.net.http.Method;
import com.yunzhou.libcommon.net.http.callback.Callback;
import com.yunzhou.libcommon.net.http.progress.CountingRequestBody;
import java.io.File;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 *
 * Created by huayunzhou on 2017/9/28.
 */

public class PostRequest extends Request<PostRequest> {

    /**
     * POST请求体种类
     */
    //没有请求数据
    private final static int TYPE_NONE = 0;
    //只有基本类型的请求数据
    private final static int TYPE_FORM = 1;
    //单一数据流的请求数据
    private final static int TYPE_SINGLE_STREAM = 2;
    //复合类型的请求数据
    private final static int TYPE_MULTIPART = 3;

    private SingleStream mSingleStream;
    private ArrayMap<String, SingleStream> multiPatams;

    public PostRequest() {
        super(Method.POST);
    }

    /**
     * 单一数据流，使用stream系列方法
     * @param stream
     * @return
     */
    public PostRequest stream(String stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(stream);
        return this;
    }

    public PostRequest stream(String type, String stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(type, stream);
        return this;
    }

    public PostRequest stream(File stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(MediaType.STREAM, stream);
        return this;
    }

    public PostRequest stream(String type, File stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(type, stream);
        return this;
    }

    public PostRequest stream(byte[] stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(stream);
        return this;
    }

    public PostRequest stream(String type, byte[] stream){
        if(mSingleStream != null){
            throw new IllegalStateException("Post Single Stream has been initialed!");
        }
        mSingleStream = new SingleStream(type, stream);
        return this;
    }


    /**
     * 复合数据流中的流数据，使用addPart系列方法
     * @return
     */
    public PostRequest addPart(String key, File value){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(MediaType.STREAM, value));
        return this;
    }

    public PostRequest addPart(String key, File value, String type){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(type, value));
        return this;
    }

    public PostRequest addPart(String key, String value){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(MediaType.TEXT, value));
        return this;
    }

    public PostRequest addPart(String key, String value, String type){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(type, value));
        return this;
    }

    public PostRequest addPart(String key, byte[] value){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(MediaType.STREAM, value));
        return this;
    }

    public PostRequest addPart(String key, byte[] value, String type){
        if(multiPatams == null){
            multiPatams = new ArrayMap<>();
        }
        multiPatams.put(key, new SingleStream(type, value));
        return this;
    }


    /**
     * post请求请求参数主要分三种情况：
     * 1.基本数据类型，使用FormBody生成即可
     * 2.单一数据流，如单一文件/字符串/byte[]，使用RequestBody.create
     * 3.复合数据流，即基本数据+数据流，使用MultiPart
     * @return RequestBody
     */
    @Override
    protected RequestBody buildRequestBody() {
        switch (getPostBodyType()){
            case TYPE_FORM:
                return generateFormBody();
            case TYPE_SINGLE_STREAM:
                return generateStreamBody();
            case TYPE_MULTIPART:
                return generateMultipartBody();
            case TYPE_NONE:
            default:
                return null;
        }
    }

    /**
     * 创建基础表单请求体
     * @return
     */
    private RequestBody generateFormBody() {
        FormBody.Builder body = new FormBody.Builder();
        ArrayMap<String, String> tmpParams = getParams();
        for(int i = 0; i < tmpParams.size(); i++){
            body.add(tmpParams.keyAt(i), tmpParams.valueAt(i));
        }
        return body.build();
    }

    /**
     * 创建单一数据流请求体
     * @return
     */
    private RequestBody generateStreamBody() {
        okhttp3.MediaType type = okhttp3.MediaType.parse(mSingleStream.getMimtType());
        if(mSingleStream.getStream() instanceof String){
            return RequestBody.create(type, (String)mSingleStream.getStream());
        }else if(mSingleStream.getStream() instanceof File){
            return RequestBody.create(type, (File)mSingleStream.getStream());
        }else if(mSingleStream.getStream() instanceof byte[]){
            return RequestBody.create(type, (byte[])mSingleStream.getStream());
        }
        return null;
    }


    /**
     * 创建复合数据类型请求体
     * @return
     */
    private RequestBody generateMultipartBody() {
        MultipartBody.Builder body = new MultipartBody.Builder();
        for(int i = 0; i < multiPatams.size(); i++){
            SingleStream ss = multiPatams.valueAt(i);
            if(ss.getStream() instanceof String){
                body.addFormDataPart(multiPatams.keyAt(i), (String)ss.getStream());
            }else if(ss.getStream() instanceof byte[]){
                body.addFormDataPart(multiPatams.keyAt(i), new String((byte[])ss.getStream()));
            }else if(ss.getStream() instanceof File){
                RequestBody fileBody = RequestBody.create(okhttp3.MediaType.parse(ss.getMimtType()),
                        (File)ss.getStream());
                body.addFormDataPart(multiPatams.keyAt(i), ((File) ss.getStream()).getName(),fileBody);
            }
        }
        ArrayMap<String, String> tmpParams = getParams();
        if(tmpParams != null && tmpParams.size() > 0){
            for(int i = 0; i < tmpParams.size(); i++){
                body.addFormDataPart(tmpParams.keyAt(i), tmpParams.valueAt(i));
            }
        }
        return body.build();
    }



    @Override
    protected okhttp3.Request buildRequest(RequestBody requestBody) {
        return this.getBuilder().post(requestBody).build();
    }

    /**
     * 若需要监听上传数据进度，可重写该方法，对RequestBody进一步嵌套封装
     * @param requestBody
     * @param callback
     * @return
     */
    @Override
    public RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        int postType = getPostBodyType();
        //如果回调不为空(如果回调为null，没有监听的必要了)，并且请求体关系到数据流，添加上传进度监听
        if(callback != null && (postType == TYPE_SINGLE_STREAM || postType == TYPE_MULTIPART)){
            final Handler handler = new Handler(Looper.getMainLooper());
            CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
                @Override
                public void onRequestProgress(final long bytesWritten, final long contentLength) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.updateProgress(getId(), bytesWritten, contentLength);
                        }
                    });
                }
            });
            return countingRequestBody;
        }else{
            return requestBody;
        }
    }

    private int getPostBodyType(){
        if(this.mSingleStream != null){
            return TYPE_SINGLE_STREAM;
        }else{
            if(this.multiPatams != null && this.multiPatams.size() > 0){
                return TYPE_MULTIPART;
            }else{
                if(this.getParams() != null && this.getParams().size() > 0){
                    return TYPE_FORM;
                }
            }
        }
        return TYPE_NONE;
    }

    private final static class SingleStream{
        private String mimtType;
        private Object stream;

        public SingleStream(Object stream) {
            this.mimtType = MediaType.TEXT;
            this.stream = stream;
        }

        public SingleStream(String mimtType, Object stream) {
            this.mimtType = mimtType;
            this.stream = stream;
        }

        public String getMimtType() {
            return mimtType;
        }

        public void setMimtType(String mimtType) {
            this.mimtType = mimtType;
        }

        public Object getStream() {
            return stream;
        }

        public void setStream(Object stream) {
            this.stream = stream;
        }
    }
}
