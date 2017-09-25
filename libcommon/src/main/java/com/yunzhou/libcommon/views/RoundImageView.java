package com.yunzhou.libcommon.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yunzhou.libcommon.R;

/**
 * Created by huayunzhou on 2017/9/25.
 */

public class RoundImageView extends ImageView {

    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    private static final int DEFAULT_BORDER_RADIUS = 0;
    private static final int DEFAULT_BORDER_WIDTH = 10;
    private static final int DEFAULT_BORDER_COLOR = 0xFFFFFF;

    /**
     * 图片类型
     */
    private int type;
    /**
     * 边框颜色
     */
    private int mBorderColor;
    /**
     * 圆角
     */
    private int mBorderRadius;
    /**
     * 边框宽度
     */
    private int mBorderWidth;

    private BitmapShader mBitmapShader;
    /**
     * 矩阵，用于放大/缩小图片
     */
    private Matrix mMatrix;

    /**
     * 绘制图片的画笔
     */
    private Paint mBitmapPaint;
    /**
     * 边框的画笔
     */
    private Paint mBorderPaint;
    private RectF mRoundRect;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        type = typedArray.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);
        mBorderColor = typedArray.getColor(R.styleable.RoundImageView_borderColor, DEFAULT_BORDER_COLOR);
        mBorderRadius = (int) typedArray.getDimension(R.styleable.RoundImageView_borderRadius, DEFAULT_BORDER_RADIUS);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.RoundImageView_borderWidth, DEFAULT_BORDER_WIDTH);
        typedArray.recycle();
        typedArray = null;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(type == TYPE_CIRCLE){
            int width = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mBorderRadius = width / 2;
            //边框边界，取小的，避免边界宽度大于View的大小
            mBorderWidth = Math.min(width/2, mBorderWidth);
            setMeasuredDimension(width, width);
        }else{
            int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
            //边框边界，取小的，避免边界宽度大于View的大小
            mBorderWidth = Math.min(size/2, mBorderWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(drawable == null){
            return ;
        }
        setupShader(drawable);
        if(mBorderWidth > 0){
            if(mBorderPaint == null){
                mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            }
            mBorderPaint.setColor(mBorderColor);
            mBorderPaint.setStrokeWidth(mBorderWidth);
            mBorderPaint.setStyle(Paint.Style.STROKE);
        }
        if(type == TYPE_CIRCLE){
            canvas.drawCircle(mBorderRadius, mBorderRadius, mBorderRadius, mBitmapPaint);
            if(mBorderWidth > 0) {
                canvas.drawCircle(mBorderRadius, mBorderRadius, mBorderRadius - mBorderWidth/2, mBorderPaint);
            }
        }else{
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius, mBitmapPaint);
            if(mBorderWidth > 0) {
                RectF newRound = new RectF();
                newRound.left = mRoundRect.left + mBorderWidth / 2;
                newRound.top = mRoundRect.top + mBorderWidth / 2;
                newRound.right = mRoundRect.right - mBorderWidth / 2;
                newRound.bottom = mRoundRect.bottom - mBorderWidth / 2;
                canvas.drawRoundRect(newRound, mBorderRadius, mBorderRadius, mBorderPaint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(type == TYPE_ROUND){
            mRoundRect = new RectF(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * 初始化BitmapShader
     * @param drawable
     */
    private void setupShader(Drawable drawable) {
        Bitmap bmp = drawableToBitmap(drawable);
        mBitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if(type == TYPE_CIRCLE){
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = getWidth() * 1.0f / bSize;
        }else{
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
                    getHeight() * 1.0f / bmp.getHeight());
        }
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    /**
     * drawable 转换成 bitmap
     * @param drawable
     * @return
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
