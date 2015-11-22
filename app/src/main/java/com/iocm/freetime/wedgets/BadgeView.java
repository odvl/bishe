package com.iocm.freetime.wedgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/11/13.
 */
public class BadgeView extends TextView {

    private Paint mPaint;

    public BadgeView(Context context) {
        this(context, null);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(getResources().getColor(R.color.red));
        Paint paint = new Paint();
        canvas.drawCircle(0, 0, 4f, paint);
        canvas.drawText("1", 0, 0, mPaint);
    }
}
