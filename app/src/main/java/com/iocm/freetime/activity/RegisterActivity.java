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

import com.activeandroid.util.Log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;
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
    private InputEditText codeInput;

    private Button getCode;
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
        codeInput = (InputEditText) findViewById(R.id.verityCode);

        getCode = (Button) findViewById(R.id.getCode);
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
        getCode.setOnClickListener(this);
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
                break;
            }

            case R.id.getCode: {
                getCode();

                break;
            }

        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        if (TextUtils.isEmpty(mMobileInput.getText().toString().trim())) {
            return;
        }
//        if (!CustomUtils.isMobileNO(mMobileInput.getText().toString().trim())) {
//            return;
//        }
        TLog.d("liubo", mMobileInput.getText().toString());
        //发送验证码
        AVUser.requestMobilePhoneVerifyInBackground(mMobileInput.getText().trim(), new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    CustomUtils.showToast(RegisterActivity.this, "发送成功，注意查收短信");
                } else {
                    TLog.e("liubo", e.getCode() + "" + " message " + e.getMessage());
                    CustomUtils.showToast(RegisterActivity.this, "发送失败，请稍后重试");
                }
            }
        });
    }

    boolean canRegister = false;

    private void register() {

        canRegister = true;
//        if (TextUtils.isEmpty(mUsernameInput.getText()) || TextUtils.isEmpty(mMobileInput.getText()) ||
//                TextUtils.isEmpty(mPasswordInput.getText())) {
//            return;
//        }
//
//        if (TextUtils.isEmpty(codeInput.getText().toString().trim())) {
//            CustomUtils.showToast(RegisterActivity.this, "请输入验证码!");
//            return;
//        }
//
//
//        AVUser.verifyMobilePhoneInBackground(codeInput.getText().toString().trim(), new AVMobilePhoneVerifyCallback() {
//            @Override
//            public void done(AVException e) {
//                if (null == e) {
//                    AVOSCloud.verifyCodeInBackground(codeInput.getText().toString().trim(),
//                            mMobileInput.getText().toString().trim(),
//                            new AVMobilePhoneVerifyCallback() {
//                                @Override
//                                public void done(AVException e) {
//                                    if (e == null) {
//                                        canRegister = true;
//                                    } else {
//                                        canRegister = false;
//                                    }
//                                }
//                            });
//                } else {
//                    canRegister = false;
//                    CustomUtils.showToast(RegisterActivity.this, "验证码有误，请重新获取");
//                }
//            }
//        });

        if (canRegister) {
            AVUser user = new AVUser();
            user.setUsername(mUsernameInput.getText().toString().trim());
            user.setMobilePhoneNumber(mMobileInput.getText().toString().trim());
            user.setPassword(mPasswordInput.getText().toString().trim());
            user.put("phone", mMobileInput.getText().toString().trim());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (null == e) {
                        CustomUtils.showToast(RegisterActivity.this, "恭喜您，注册成功!");
                        registerSuccessful();

                    } else {
                        CustomUtils.showToast(RegisterActivity.this, "很遗憾，注册失败，请稍后再试。");
                    }
                }
            });
        }

    }

    /**
     * 注册成功
     */
    private void registerSuccessful() {

        saveCache();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        User user = new User();
        user.setName(mUsernameInput.getText());
        user.setPassword(mPasswordInput.getText());
        user.setPhoneNumber(mMobileInput.getText());
        bundle.putSerializable(Constant.Key.UserInfo, (Serializable) user);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        onBackPressed();

    }

    /**
     * 保存账号密码到cache
     */
    public void saveCache() {


        NameValue<String> value = new NameValue<>(Constant.User.username,
                mUsernameInput.getText().toString().trim());
        cache.saveValue(value);

        NameValue<String> pvalue = new NameValue<>(Constant.User.password,
                mPasswordInput.getText().toString().trim());
        cache.saveValue(pvalue);

        NameValue<String> mValue = new NameValue<>(Constant.User.mobile,
                mMobileInput.getText().toString().trim());
        cache.saveValue(mValue);
    }


    @Override
    public void leftClickListener() {
        onBackPressed();
    }

    @Override
    public void rightClickListener() {

    }

}
