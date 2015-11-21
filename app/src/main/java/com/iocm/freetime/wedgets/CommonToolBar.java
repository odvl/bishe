package com.iocm.freetime.wedgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iocm.administrator.yunxuan.R;

/**
 * Created by liubo on 15/6/14.
 */
public class CommonToolBar extends RelativeLayout implements View.OnClickListener {

    String mTitleStr;
    int mLeftImageRes;
    int mRightImageRes;
    boolean mLeftImageVisiable;
    boolean mRightImageVisiable;

    TextView mTitleView;

    ImageView mLeftView;
    ImageView mRightView;
    public CommonToolBar(Context context) {
        super(context);
    }

    public CommonToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        View root = LayoutInflater.from(context).inflate(R.layout.common_tool_bar_layout, this, true);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CommonToolBar, 0, 0);
        try {
            mTitleStr = a.getString(R.styleable.CommonToolBar_toolbarTitle);
            mLeftImageRes = a.getResourceId(R.styleable.CommonToolBar_leftImageResource, R.drawable.school_task);
            mRightImageRes = a.getResourceId(R.styleable.CommonToolBar_rightImageResource, R.drawable.school_task);
            mLeftImageVisiable = a.getBoolean(R.styleable.CommonToolBar_isLeftImage, false);
            mRightImageVisiable = a.getBoolean(R.styleable.CommonToolBar_isRightImage, false);
        } finally {
            a.recycle();
        }

        mTitleView = (TextView) root.findViewById(R.id.toolbar_title);
        mLeftView = (ImageView) root.findViewById(R.id.toolbar_left_image);
        mRightView = (ImageView) root.findViewById(R.id.toolbar_right_image);

        mRightView.setOnClickListener(CommonToolBar.this);
        mLeftView.setOnClickListener(CommonToolBar.this);

        mTitleView.setText(mTitleStr);

        if (mLeftImageVisiable) {
            if (mLeftView.getVisibility() == GONE)
                mLeftView.setVisibility(VISIBLE);
            mLeftView.setImageResource(mLeftImageRes);
        } else {
            if (mLeftView.getVisibility() == VISIBLE)
                mLeftView.setVisibility(GONE);
        }

        if (mRightImageVisiable) {
            if (mRightView.getVisibility() == GONE) {
                mRightView.setVisibility(VISIBLE);
            }
            mRightView.setImageResource(mRightImageRes);
        } else {
            if (mRightView.getVisibility() == VISIBLE)
                mRightView.setVisibility(GONE);
        }
    }

    OnCommonToolBarClickListener mOnCommonToolBarClickListener;

    public void setOnCommonToolBarClickListener(OnCommonToolBarClickListener mOnCommonToolBarClickListener) {
        this.mOnCommonToolBarClickListener = mOnCommonToolBarClickListener;
    }

    public interface OnCommonToolBarClickListener {

        void leftClickListener();
        void rightClickListener();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_image: {
                if (mOnCommonToolBarClickListener != null) {
                    mOnCommonToolBarClickListener.leftClickListener();
                }
                break;
            }
            case R.id.toolbar_right_image: {
                if (mOnCommonToolBarClickListener != null) {
                    mOnCommonToolBarClickListener.rightClickListener();
                }
                break;
            }
        }

    }


    public String getmTitleStr() {
        return mTitleStr;
    }

    public void setmTitleStr(String mTitleStr) {
        this.mTitleStr = mTitleStr;
        this.mTitleView.setText(mTitleStr);
    }
}
