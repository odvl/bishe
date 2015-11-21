package com.iocm.freetime.wedgets;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by liubo on 15/6/22.
 */
public class RotationView {
    View mActionBarView;
    private void setUpOpeningView(final View openingView) {

        if (mActionBarView != null) {

            mActionBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                @Override

                public void onGlobalLayout() {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                        mActionBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    } else {

                        mActionBarView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    }

                    mActionBarView.setPivotX(calculatePivotX(openingView));

                    mActionBarView.setPivotY(calculatePivotY(openingView));

                }

            });

        }

    }


    private float calculatePivotX(View burger) {

        return burger.getTop() + burger.getHeight() / 2;

    }


    private float calculatePivotY(View burger) {

        return burger.getTop() + burger.getHeight() / 2;

    }
}