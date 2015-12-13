package com.iocm.freetime.wedgets.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/12/11.
 */
public class VerifiedCodeDialog extends AlertDialog {

    AnimatorSet set;
    ViewGroup root;
    private boolean isAnimator = false;

    public VerifiedCodeDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_verified_code);
        root = (ViewGroup) findViewById(R.id.root);

        set = new AnimatorSet();
    }

    public void showDialog() {
        if (isAnimator) {
            return;
        }


        final int top = root.getTop();
        final int bottom = root.getBottom();
        final int middle = (root.getTop() + root.getBottom()) / 2;

        root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                root.getViewTreeObserver().removeOnPreDrawListener(this);

                set.playTogether(ObjectAnimator.ofInt(root, "top", middle, top),
                        ObjectAnimator.ofInt(root, "bottom", middle, bottom),
                        ObjectAnimator.ofFloat(root, View.ALPHA, 0f, 1f));

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isAnimator = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isAnimator = false;
                        VerifiedCodeDialog.this.show();
                    }
                });

                set.setDuration(500);
                set.start();
                return false;
            }
        });


    }

    public void hideDialog() {
        if (isAnimator) {
            return;
        }

        int top = root.getTop();
        int bottom = root.getBottom();
        int middle = (top + bottom) / 2;

        set.playTogether(ObjectAnimator.ofInt(root, "top", top, middle),
                ObjectAnimator.ofInt(root, "bottom", bottom, middle),
                ObjectAnimator.ofFloat(root, View.ALPHA, 1f, 0f));
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimator = false;
                VerifiedCodeDialog.this.dismiss();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimator = true;
            }
        });

        set.setDuration(300);
        set.start();
    }
}
