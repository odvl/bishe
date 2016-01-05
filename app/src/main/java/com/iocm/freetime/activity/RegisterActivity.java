package com.iocm.freetime.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

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
import com.iocm.freetime.fragment.VerifyMobileFragment;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.iocm.freetime.wedgets.InputEditText;
import com.iocm.freetime.wedgets.dialog.VerifiedCodeDialog;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/1/21.
 */
public class RegisterActivity extends BaseActivity {

    private InputEditText mUsernameInput;
    private InputEditText mPasswordInput;

    private Button mRegisterBtn;

    private CommonToolBar mToolbar;

    private View mRegisterLayoutView;
    private Dialog dialog;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new Dialog(mContext);

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
                break;
            }


        }
    }



    private void register() {

        if (TextUtils.isEmpty(mUsernameInput.getText()) || TextUtils.isEmpty(mPasswordInput.getText())) {
            CustomUtils.showToast(mContext, "请填写完整注册信息!");
            return;
        }

            AVUser user = new AVUser();
            user.setUsername(mUsernameInput.getText().toString().trim());
            user.setPassword(mPasswordInput.getText().toString().trim());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (null == e) {
                        registerSuccessful();
                    } else {
                        TLog.d("liubo", "err" + e.getMessage());
                        CustomUtils.showToast(RegisterActivity.this, "很遗憾，注册失败，请稍后再试。");
                    }
                }
            });


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
        bundle.putSerializable(Constant.Key.UserInfo, (Serializable) user);
        intent.putExtras(bundle);

        setResult(Constant.ResultCode.ResultOk, intent);
        onBackPressed();
//
//        dialog.setContentView(R.layout.dialog_confirm_verify_mobile);
//        dialog.show();
//
//        Button no = (Button) dialog.findViewById(R.id.no);
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//
//        Button yes = (Button) dialog.findViewById(R.id.yes);
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                VerifyMobileFragment fragment = new VerifyMobileFragment();
//                getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment_content, fragment).commit();
//
//            }
//        });
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



    }


    @Override
    public void leftClickListener() {
        onBackPressed();
    }

    @Override
    public void rightClickListener() {

    }

}
