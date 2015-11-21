package com.ozn.mylibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 15/7/18.
 */
public class FlowLayout extends ViewGroup {

    //new的时候调用
    public FlowLayout(Context context) {
        this(context, null);
    }

    //布局文件中调用，没有使用自定义属性
    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //布局文件中调用，自定义属性
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //获取当前layout的params
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    //测量模式＋测量值
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取屏幕宽度以及对应的测量模式
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);


        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;
        //纪录当前行的宽度
        int lineWidth = 0;
        int lineHeight = 0;

        int _count = getChildCount();
        for (int i = 0; i < _count; i++) {
            View _child = getChildAt(i);
            measureChild(_child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) _child.getLayoutParams();

            int _childWidth = _child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int _childHeight = _child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //行宽加上view的宽度如果大于整个布局的宽度
            if (lineWidth + _childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {

                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                lineWidth = _childWidth;
                height += lineHeight;
                lineHeight = _childHeight;
            } else {
                lineWidth += _childWidth;
                lineHeight = Math.max(lineHeight, _childHeight);
            }

            //the last one item view
            if (i == _count - 1) {
                width = Math.max(lineWidth, _childHeight);
                height += lineHeight;
            }
        }
        //wrap_content
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width, modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);


    }

            //存储所有的view
            private List<List<View>> mAllViews = new ArrayList<>();
            //行的高度
            private List<Integer> mLineHeight = new ArrayList<>();
            private List<Integer> mLastWidth = new ArrayList<>();

            @Override
            protected void onLayout(boolean changed, int l, int t, int r, int b) {
                mAllViews.clear();
                mLineHeight.clear();
                mLastWidth.clear();
                //当前viewGroup的宽度
                int width = getWidth();

                int lineHeight = 0;
                int lineWidth = 0;
                int lastWidth = 0;

                List<View> lineViews = new ArrayList<>();
                int count = getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = getChildAt(i);
                    MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();

                    //换行
                    if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() + getPaddingRight()) {
                        // 纪录line height
                        lastWidth = width - getPaddingRight() - getPaddingLeft() - lineWidth;
                        mLastWidth.add(lastWidth);

                        Log.i("tag" , " save last" + lastWidth);
                        mLineHeight.add(lineHeight);
                        mAllViews.add(lineViews);

                        //重置行宽高
                        lineWidth = 0;
                        lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                        lineViews = new ArrayList<>();
                    }

                    lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
                    lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.rightMargin);
                    lineViews.add(child);
                }

                //处理最后一行
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lastWidth = width - getPaddingRight() - getPaddingLeft() - lineWidth;
                mLastWidth.add(lastWidth);

                Log.i("tag", " save last" + lastWidth);


                //设置子View的位置
                int left = 0;
                int top = 0;
                int lineNum = mAllViews.size();
                out:
                for (int i = 0; i < lineNum; i++) {
                    //当前行所有的view
                    lineViews = mAllViews.get(i);
                    lineHeight = mLineHeight.get(i);

                    for (int j = 0; j < lineViews.size(); j++) {
                        View child = lineViews.get(j);
                        if (child.getVisibility() == View.GONE) {
                            continue;
                        }


                        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        //                for (int k = 0;k < mLastWidth.size();k ++) {
        //                    lastWidth = mLastWidth.get(k);
        //                    if (lastWidth >= child.getMeasuredWidth()) {
        //                        int _left = width - lastWidth;
        //                        int _top = mLineHeight.get(k);
        //                        int lc = _left + lp.leftMargin;
        //                        int tc = _top + lp.topMargin;
        //                        int rc = lc + child.getMeasuredWidth();
        //                        int bc = tc + child.getMeasuredHeight();
        //
        //                        //子view布局
        //                        child.layout(lc, tc, rc, bc);
        //                        Log.i("Tag" ,  "last" + lastWidth);
        //                        mLastWidth.add(k, -1);
        //                        continue out;
        //                    } else {
        //                        continue ;
        //                    }
        //                }

                        int lc = left + lp.leftMargin;
                        int tc = top + lp.topMargin;
                        int rc = lc + child.getMeasuredWidth();
                        int bc = tc + child.getMeasuredHeight();

                        //子view布局
                        child.layout(lc, tc, rc, bc);
                        left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                    }


                    top += lineHeight;
                    left = 0;

                }

            }
}
