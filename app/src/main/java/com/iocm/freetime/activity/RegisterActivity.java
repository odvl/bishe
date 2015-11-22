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
public class RegisterActivity extends BaseActivity{

    private InputEditText mMobileInput;
    private InputEditText mUsernameInput;
    private InputEditText mPasswordInput;
    private Button mRegisterBtn;
    private CommonToolBar mToolbar;
    private View mRegisterLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        init();
        setupViews();

//        userName = (EditText) findViewById(R.id.register_userName_EditText);
//        pwd = (EditText) findViewById(R.id.register_pwd_EditText);
//        confirPwd = (EditText) findViewById(R.id.register_confirPwd_EditText);
//        phone_number = (EditText) findViewById(R.id.register_phone_EditText);
//        register = (Button) findViewById(R.id.register_register_Button);
//        reset = (Button) findViewById(R.id.register_reset_Button);
//
//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                userName.setText("");
//                pwd.setText("");
//                confirPwd.setText("");
//                phone_number.setText("");
//                userName.requestFocus();
//                userName.setFocusable(true);
//                userName.setFocusableInTouchMode(true);
//            }
//        });
//
//        register.setOnClickListener(new ResgisterLis());
    }

    @Override
    void initView() {

    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

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
        return true;
    }

    private void setupViews() {
        mMobileInput = (InputEditText) findViewById(R.id.mobile);
        mUsernameInput = (InputEditText) findViewById(R.id.username);
        mPasswordInput = (InputEditText) findViewById(R.id.password);
        mRegisterBtn = (Button) findViewById(R.id.register);
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);

        mToolbar.setOnCommonToolBarClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    private void init() {

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

    }

    @Override
    public void rightClickListener() {
        finish();
    }

//    class ResgisterLis implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            boolean flag = true;
//            String user_name = userName.getText().toString().trim();
//            if(user_name.equals("")||user_name == null) {
//                flag = false;
//                Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
//            }
//            String pwd1 = pwd.getText().toString().trim();
//            String conpwd = confirPwd.getText().toString().trim();
//            if(!pwd1.equals(conpwd)){
//                flag = false;
//                confirPwd.setFocusable(true);
//                confirPwd.setFocusableInTouchMode(true);
//                confirPwd.requestFocus();
//                Toast.makeText(getApplicationContext(),"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
//            }
//
//
//
//            if(flag) {
////                Toast.makeText(RegisterActivity.this,"开始注册",Toast.LENGTH_SHORT).show();
//
//                UserLoginApp userLoginApp = (UserLoginApp) getApplication();
//                userLoginApp.setUserName(user_name);
//                userLoginApp.setPassWord(pwd1);
//                userLoginApp.setPhoneNumber(phone_number.getText().toString());
//                Reg(user_name, pwd1, phone_number.getText().toString());
//            }
//
//        }
//    }
//
//    private void Reg(final String username1, final String password1,String phonenumber) {
//
//        String url = getResources().getString(R.string.regurl);
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("username",username1);
//        params.add("password",password1);
//        params.add("phonenumber",phonenumber);
//
//
//        client.post(url,params,new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String get = new String(bytes);
//                if(get.equals("2")){
//                    Toast.makeText(RegisterActivity.this,"恭喜["+username1+"]注册成功",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent();
//                    intent.setClass(RegisterActivity.this, MainActivity.class);
//                    intent.putExtra("USERNAME", username1);
//                    intent.putExtra("PASSWORD",password1);
//                    setResult(6, intent);
//                    finish();
//                } if(get.equals("1")) {
//                    Toast.makeText(RegisterActivity.this,"该用户已被注册",Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
//
//    }
}
