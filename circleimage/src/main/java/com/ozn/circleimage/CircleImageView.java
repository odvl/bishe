package com.ozn.circleimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by liubo on 15/7/20.
 */
public class CircleImageView extends View {

    //图片的类型
    private static final int TYPE_ROUND = 1;
    private static final int TYPE_CIRCLE = 0;

    //图片的宽高，半径
    private int mCircleType;
    private int mHeight;
    private int mWidth;
    private int mRadius;

    //图片资源
    private Bitmap mSrc;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleImageView, 0, 0);
        try {
            mCircleType = a.getInt(R.styleable.CircleImageView_circleType, 0);
            mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.CircleImageView_src, 0));
            mRadius = a.getDimensionPixelSize(R.styleable.CircleImageView_borderRadius,
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, getResources().getDisplayMetrics()));
        } finally {
            a.recycle();
        }

    }

    public void setSrc(Bitmap bitmap) {
        this.mSrc = bitmap;
        invalidate();
    }

    /**
     * 测量图片的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量宽度
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        //match_parent exactly
        if (modeWidth == MeasureSpec.EXACTLY) {
            mWidth = sizeWidth;
        } else {
            int desireByImg = mSrc.getWidth() + getPaddingLeft() + getPaddingRight();
            if (modeWidth == MeasureSpec.AT_MOST) {
                mWidth = Math.min(desireByImg, sizeWidth);
            } else {
                mWidth = desireByImg;
            }
        }

        //测量高度
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        if (modeHeight == MeasureSpec.EXACTLY) {
            mHeight = sizeHeight;
        } else {
            int desireByImg = mSrc.getHeight() + getPaddingTop() + getPaddingBottom();
            if (modeHeight == MeasureSpec.AT_MOST) {
                mHeight = Math.min(desireByImg, sizeHeight);
            } else {
                mHeight = desireByImg;
            }
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCircleType) {
            case TYPE_CIRCLE: {
                int min = Math.min(mWidth, mHeight);
                mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);
                canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
                break;
            }
            case TYPE_ROUND: {
                canvas.drawBitmap(createRoundImge(mSrc), 0, 0, null);
                break;
            }
            default:
        }
    }


    /**
     * 根据原图和边长绘制图形
     */
    private Bitmap createCircleImage(Bitmap source, int min) {
        //set paint;
        final Paint paint = new Paint();
        paint.setAntiAlias(true);

        //set a draw nothing square canvas
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);


        //draw a circle on square
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        //set paint mode
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     *
     */
    private Bitmap createRoundImge(Bitmap source) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(target);

        RectF rectF = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(source, 0, 0, paint);
        return target;

    }
}
