package com.iocm.freetime.wedgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 15/6/14.
 */
public class BottomBar extends LinearLayout implements View.OnClickListener {
    private static final String TAG = BottomBar.class.getName();

    private List<BottomBarItem> mItems = new ArrayList<>();
    private int mCurrentPosition = -1;
    private long mCurrentTime;

    public OnBottomBarClickListener mOnBottomBarClickListener;
    public OnBottomBarDoubleClickListener mOnBottomBarDoubleClickListener;


    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        for (int i = 0; i < getChildCount(); i++) {
            View _view = getChildAt(i);
            if (_view instanceof BottomBarItem) {
                mItems.add((BottomBarItem) _view);
            }
        }

        for (int i = 0; i < mItems.size(); i++) {
            BottomBarItem _item = mItems.get(i);
            _item.setOnClickListener(this);
            _item.setTag(i);
        }

        if (mItems.size() > 0) {
            initClick(0);
        }

    }

    public void setOnBottomBarClickListener(OnBottomBarClickListener mOnBottomBarClickListener) {
        this.mOnBottomBarClickListener = mOnBottomBarClickListener;
    }

    public void setOnBottomBarDoubleClickListener(OnBottomBarDoubleClickListener mOnBottomBarDoubleClickListener) {
        this.mOnBottomBarDoubleClickListener = mOnBottomBarDoubleClickListener;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        initClick(position);
    }

    private void initClick(int position) {

        //连续点击两次调用
        long _currentTimes = System.currentTimeMillis();
        if (_currentTimes - mCurrentTime < 200) {
            if (mOnBottomBarDoubleClickListener != null) {
                mOnBottomBarDoubleClickListener.onBottomBarDoubleClick();
            }

        } else {
            mCurrentTime = _currentTimes;


            //恢复曾经的按钮
            if (mCurrentPosition > -1) {
                BottomBarItem _item = mItems.get(mCurrentPosition);
                _item.changeColor(true);
            }

            if (mCurrentPosition != position) {
                mCurrentPosition = position;
                BottomBarItem _item = mItems.get(mCurrentPosition);
                if (mOnBottomBarClickListener != null) {
                    mOnBottomBarClickListener.onBottomClick(position);
                }
                _item.changeColor(false);
            } else if (mCurrentPosition == position) {
                BottomBarItem _item = mItems.get(mCurrentPosition);
                _item.changeColor(false);
                return;
            }
        }


    }

    public interface OnBottomBarClickListener {
        void onBottomClick(int position);

    }

    public interface OnBottomBarDoubleClickListener {
        void onBottomBarDoubleClick();
    }

}
