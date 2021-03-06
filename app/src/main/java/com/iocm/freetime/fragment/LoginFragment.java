package com.iocm.freetime.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.activeandroid.util.Log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.RegisterActivity;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.cache.Cache;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.Setting;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.InputEditText;

/**
 * Created by liubo on 15/7/22.
 */
public class LoginFragment extends TaskFragments implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.getName();


    private View mLoginContent;

    private InputEditText mUsernameInput;
    private InputEditText mPasswordInput;

    private Button mLoginBtn;
    private TextView mRegisterBtn;
    private String mUsername;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_login, null);
        init();
        setupViewInLogin(root);
        return root;
    }

    private void init() {

    }

    private void setupViewInLogin(View root) {
        mLoginContent = root.findViewById(R.id.login_content);
        mUsernameInput = (InputEditText) root.findViewById(R.id.username);
        mPasswordInput = (InputEditText) root.findViewById(R.id.password);

        mLoginBtn = (Button) root.findViewById(R.id.login);
        mLoginBtn.setOnClickListener(LoginFragment.this);
        mRegisterBtn = (TextView) root.findViewById(R.id.register);
        mRegisterBtn.setOnClickListener(LoginFragment.this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadLayoutAnimation();
    }

    private void loadLayoutAnimation() {
        mLoginContent.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mLoginContent.getViewTreeObserver().removeOnPreDrawListener(this);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(mLoginContent, View.TRANSLATION_Y, mLoginContent.getHeight(), 0),
                        ObjectAnimator.ofFloat(mLoginContent, View.ALPHA, 0, 1.0f));
                set.setDuration(500);
                set.start();
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login: {
                login();
                break;
            }
            case R.id.register: {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, Constant.RequestCode.RegisterCode);
            }


        }
    }

    private void login() {
        accessNetWork();
        AVUser.logInInBackground(mUsernameInput.getText().toString(), mPasswordInput.getText().toString(), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (null != avUser) {
                    mUsername = mUsernameInput.getText().toString().trim();
                    saveCache();
                    CustomUtils.showToast(getActivity(), "登陆成功");
                    jumpFragment();
                } else {
                    CustomUtils.showToast(getActivity(), "用户名或密码错误");
                    return;
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.RequestCode.RegisterCode &&
                resultCode == Constant.ResultCode.ResultOk) {
            Bundle bundle = data.getExtras();
            User mUser = (User) bundle.getSerializable(Constant.Key.UserInfo);
            mUsername = mUser.getName();
            AVUser.logInInBackground(mUser.getName(), mUser.getPassword(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (null != avUser) {
                        saveCache();
                        jumpFragment();
                    } else {

                        return;
                    }
                }
            });
            if (mOnLoginBtnClickListener != null) {
                mOnLoginBtnClickListener.onLoginBtnClick();
            }
        }
    }

    private void jumpFragment() {
        if (mOnLoginBtnClickListener != null) {
            mOnLoginBtnClickListener.onLoginBtnClick();
        }
    }

    private void accessNetWork() {

    }

    private void saveCache() {
        Cache cache = Cache.getInstance(getActivity());
        NameValue nameValue = new NameValue(Constant.User.username, mUsername);
        TLog.d("liubo", "username" + mUsername);
        cache.saveValue(nameValue);

        NameValue nameValue1 = new NameValue(Constant.User.login, true);
        cache.saveValue(nameValue1);

        NameValue nameValue2 = new NameValue(Constant.User.userId, AVUser.getCurrentUser().getObjectId());
        cache.saveValue(nameValue2);

    }

    private OnLoginBtnClickListener mOnLoginBtnClickListener;

    public void setOnLoginBtnClickListener(OnLoginBtnClickListener mOnLoginBtnClickListener) {
        this.mOnLoginBtnClickListener = mOnLoginBtnClickListener;
    }

    public interface OnLoginBtnClickListener {
        void onLoginBtnClick();
    }
}
