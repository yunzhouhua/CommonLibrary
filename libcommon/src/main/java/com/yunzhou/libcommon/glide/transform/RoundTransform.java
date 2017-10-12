package com.yunzhou.libcommon.glide.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 圆角图片的转换
 * 更多的transform,可以参考： https://github.com/wasabeef/glide-transformations
 * Created by huayunzhou on 2017/10/10.
 */

public class RoundTransform extends BitmapTransformation{

    private static final int DEFAULT_BORDER_COLOR = Color.WHITE;
    private static final int DEFAULT_BORDER_WIDTH = 10;
    private static final int DEFAULT_BORDER_RADIUS = 0;

    //类型：圆角
    public static final int TYPE_ROUND = 0;
    //类型：圆形
    public static final int TYPE_CIRCLE = 1;

    private int mType = TYPE_ROUND;
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    private int mBorderRadius = DEFAULT_BORDER_RADIUS;

    //绘制边线的画笔
    private Paint mBorderPaint;

    public RoundTransform(Context context) {
        super(context);
        init();
    }

    public RoundTransform(Context context, int type, int borderWidth, int borderColor){
        super(context);
        this.mType = type;
        this.mBorderWidth = borderWidth;
        this.mBorderColor = borderColor;
        init();
    }

    public RoundTransform(Context context, int type, int borderWidth, int borderColor, int borderRadius){
        super(context);
        this.mType = type;
        this.mBorderWidth = borderWidth;
        this.mBorderColor = borderColor;
        this.mBorderRadius = borderRadius;
        init();
    }

    private void init(){
        if(mBorderWidth > 0) {
            mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBorderPaint.setDither(true);
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        switch (mType){
            case TYPE_CIRCLE:
                return transform4Circle(pool, toTransform);
            case TYPE_ROUND:
                return transform4Round(pool, toTransform);
        }
        return null;
    }

    /**
     * 圆形变换
     * @param pool
     * @param toTransform
     * @return
     */
    private Bitmap transform4Circle(BitmapPool pool, Bitmap toTransform) {
        if(toTransform == null){
            return null;
        }
        int squareWidth = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int startX = (toTransform.getWidth() - squareWidth) / 2;
        int startY = (toTransform.getHeight() - squareWidth) / 2;
        //截取中间方块
        Bitmap squareBitmap = Bitmap.createBitmap(toTransform, startX, startY, squareWidth, squareWidth);
        Bitmap result = pool.get(squareWidth, squareWidth, Bitmap.Config.ARGB_8888);
        if(result == null){
            result = Bitmap.createBitmap(squareWidth, squareWidth, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint shaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shaderPaint.setDither(true);
        shaderPaint.setShader(new BitmapShader(squareBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawCircle(squareWidth / 2, squareWidth / 2, squareWidth / 2, shaderPaint);
        //绘制边框
        if(mBorderPaint != null){
            canvas.drawCircle(squareWidth / 2, squareWidth / 2, squareWidth / 2 - mBorderWidth / 2, mBorderPaint);
        }
        if(result != null){
            pool.put(result);
        }
        squareBitmap.recycle();
        return result;
    }

    /**
     * 圆角变换
     * @param pool
     * @param toTransform
     * @return
     */
    private Bitmap transform4Round(BitmapPool pool, Bitmap toTransform) {
        if(toTransform == null){
            return null;
        }
        // border 要居中即要压在图片上
        final int border = (int) (mBorderWidth / 2);
        final int width = (int) (toTransform.getWidth() - mBorderWidth);
        final int height = (int) (toTransform.getHeight() - mBorderWidth);

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(border, border, width - border, height - border);
        canvas.drawRoundRect(rectF, mBorderRadius, mBorderRadius, paint);

        if (mBorderPaint != null) {
            canvas.drawRoundRect(rectF, mBorderRadius, mBorderRadius, mBorderPaint);
        }
        if(result != null){
            pool.put(result);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
