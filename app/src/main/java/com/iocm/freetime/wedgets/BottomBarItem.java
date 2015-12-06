package com.iocm.freetime.wedgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/6/14.
 */
public class BottomBarItem extends RelativeLayout {

    private static final String TAG = BottomBarItem.class.getName();

    private String mBottomBarTextStr;
    private int mBottomBarIconId;

    private TextView mBottomBarTextView;
    private ImageView mBottomBarIconView;

    public BottomBarItem(Context context) {
        this(context, null);
    }

    public BottomBarItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        View _root = LayoutInflater.from(context).inflate(R.layout.bottombar_item_layout, this, true);

        mBottomBarTextView = (TextView) _root.findViewById(R.id.bottom_bar_text);
        mBottomBarIconView = (ImageView) _root.findViewById(R.id.bottom_bar_icon);

        TypedArray _typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomBarItem, 0, 0);
        try {
            mBottomBarTextStr = _typeArray.getString(R.styleable.BottomBarItem_bottom_bar_text);
            mBottomBarIconId = _typeArray.getResourceId(R.styleable.BottomBarItem_bottom_bar_icon, R.drawable.ic_launcher);

        }finally {
            _typeArray.recycle();
        }

        if (mBottomBarTextView != null && mBottomBarTextStr != null) {
            mBottomBarTextView.setText(mBottomBarTextStr);
        }

        if (mBottomBarIconView != null) {
            mBottomBarIconView.setImageResource(mBottomBarIconId);
        }

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mBottomBarTextView.setEnabled(enabled);
        mBottomBarIconView.setEnabled(enabled);
    }

    public void changeColor(boolean enabled) {
        if (!enabled) {
            mBottomBarTextView.setTextColor(getResources().getColor(R.color.theme_light_dark_color));
        } else {
            mBottomBarTextView.setTextColor(getResources().getColor(R.color.black));
        }
    }


}
