package com.iocm.freetime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.cache.Cache;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.Setting;
import com.iocm.freetime.wedgets.InputEditText;
import com.ozn.circleimage.CircleImageView;

import java.io.IOException;


/**
 * user login activity
 *
 * @version 1.0
 *          Created by Administrator on 2015/1/21.
 * @date 02015-07-11
 */
public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private static final int REQ_LOGIN_REGISTER_CODE = 100;

    private Cache cache;

    private CircleImageView userPhotoCircleImageView;

    private InputEditText usernameInputEditText;
    private InputEditText passwordInputEditText;

    private Button loginButton;

    private TextView registerTextView;


    @Override
    void initView() {
        setContentView(R.layout.activity_login);

        userPhotoCircleImageView = (CircleImageView) findViewById(R.id.user_photo);

        usernameInputEditText = (InputEditText) findViewById(R.id.username);
        passwordInputEditText = (InputEditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.login);

        registerTextView = (TextView) findViewById(R.id.register);

    }

    @Override
    void initListener() {
        loginButton.setOnClickListener(this);

        registerTextView.setOnClickListener(this);
    }

    @Override
    void loadData() {
        cache = Cache.getInstance(mContext);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == loginButton.getId()) {
            login();
        } else if (id == registerTextView.getId()) {
            startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQ_LOGIN_REGISTER_CODE);
        }

    }

    private void login() {


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_LOGIN_REGISTER_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            User user = (User) bundle.getSerializable(Constant.Key.UserInfo);
            usernameInputEditText.setText(user.getPhoneNumber());
            passwordInputEditText.setText(user.getPassword());
        }
    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}






