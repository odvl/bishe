package com.iocm.freetime.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.iocm.freetime.wedgets.InputEditText;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/1/21.
 */
public class RegisterActivity extends BaseActivity {

    private InputEditText mMobileInput;
    private InputEditText mUsernameInput;
    private InputEditText mPasswordInput;
    private Button mRegisterBtn;
    private CommonToolBar mToolbar;
    private View mRegisterLayoutView;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            mRegisterLayoutView = findViewById(R.id.register_content);
            mRegisterLayoutView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mRegisterLayoutView.getViewTreeObserver().removeOnPreDrawListener(this);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(ObjectAnimator.ofFloat(mRegisterLayoutView, View.TRANSLATION_Y, mRegisterLayoutView.getHeight(), 0));
                    set.setDuration(300);
                    set.start();
                    return false;
                }
            });
        }


        mMobileInput = (InputEditText) findViewById(R.id.mobile);
        mUsernameInput = (InputEditText) findViewById(R.id.username);
        mPasswordInput = (InputEditText) findViewById(R.id.password);
        mRegisterBtn = (Button) findViewById(R.id.register);
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);
    }

    @Override
    void initView() {
    }

    @Override
    void initListener() {
        mToolbar.setOnCommonToolBarClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    void loadData() {

    }

    @Override
    public void onBackPressed() {

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(mRegisterLayoutView, View.TRANSLATION_Y, 0, mRegisterLayoutView.getHeight()));
        set.setDuration(300);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                super.onAnimationEnd(animation);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register: {
                register();
            }
        }
    }

    private void register() {
        if (TextUtils.isEmpty(mUsernameInput.getText()) || TextUtils.isEmpty(mMobileInput.getText()) ||
                TextUtils.isEmpty(mPasswordInput.getText())) {
            return;
        }

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        User user = new User();
        user.setName(mUsernameInput.getText());
        user.setPassword(mPasswordInput.getText());
        user.setPhoneNumber(mMobileInput.getText());
        bundle.putSerializable(Constant.Key.UserInfo, (Serializable) user);
        intent.putExtra(Constant.Key.UserInfo, bundle);
        setResult(Constant.ResultCode.ResultOk, intent);
        finish();
    }

    @Override
    public void leftClickListener() {
        onBackPressed();
    }

    @Override
    public void rightClickListener() {

    }

}
