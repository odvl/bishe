package com.iocm.freetime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.iocm.administrator.yunxuan.R;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.util.Setting;

import java.io.IOException;


/**
 * user login activity
 *
 * @version 1.0
 *          Created by Administrator on 2015/1/21.
 * @date 02015-07-11
 */
public class LoginActivity extends BaseActivity{
    private static final String TAG = LoginActivity.class.getName();

    private int JUG;
    private Button mLoginBtn;
    private Button mRegisterBtn;

    private EditText mUserNameEdt;
    private EditText mPasswordEdt;

    private CheckBox mRemPwdCkb = null;
    private CheckBox mAutoLoginCkb = null;
    private Setting mSetting;

    private boolean mRemPwd = false;
    private boolean mAutoLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

//        mUserNameEdt = (EditText) findViewById(R.id.login_username_EditText);
//        mPasswordEdt = (EditText) findViewById(R.id.login_password_EditText);
//        mRemPwdCkb = (CheckBox) findViewById(R.id.login_rem_pwd_CheckBox);
//        mAutoLoginCkb = (CheckBox) findViewById(R.id.login_login_CheckBox);
//        mLoginBtn = (Button) findViewById(R.id.login_LoginButton);
//        mRegisterBtn = (Button) findViewById(R.id.login_RegisterButton);


//        //判断记住密码多选框的状态
//        if (mSharedPreferences.getBoolean("ISCHECK", false)) {
//            //设置默认是记录密码状态
//            mRemPwdCkb.setChecked(true);
//            mUserNameEdt.setText(mSharedPreferences.getString("USER_NAME", ""));
//            mPasswordEdt.setText(mSharedPreferences.getString("PASSWORD", ""));
//
//            //设置默认是自动登录状态
//            if (mSharedPreferences.getBoolean("AUTO_ISCHEK", true)) {
//                mAutoLoginCkb.setChecked(true);
//            }
//        }

//        mLoginBtn.setOnClickListener(new LoginButtonLis());
//
//        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, RegisterActivity.class);
//                LoginActivity.this.startActivityForResult(intent, 5);
////                LoginActivity.this.startActivity(intent);
//
//            }
//        });

//        mRemPwdCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mRemPwd = isChecked;
//                if (mRemPwdCkb.isChecked()) {
//                    mSharedPreferences.edit().putBoolean("ISCHECK", true).commit();
//                } else {
//                    mSharedPreferences.edit().putBoolean("ISCHECK", false).commit();
//                }
//                //Toast.makeText(getApplicationContext(),"xunaze" + isChecked,Toast.LENGTH_SHORT).show();
//                //选择上为true
//            }
//        });
//
//        mAutoLoginCkb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (mAutoLoginCkb.isChecked()) {
//                    mSharedPreferences.edit().putBoolean("AUTO_ISCHECK", true).commit();
//                } else {
//                    mSharedPreferences.edit().putBoolean("AUTO_ISCHECK", false).commit();
//                }
//            }
//        });


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

    private void init() {
        mSetting = Setting.getInstance(LoginActivity.this);

    }

    /**
     * user login
     *
     * @param username1           :username
     * @param password1:password;
     * @return null;
     */

    private void Login(final String username1, final String password1) throws IOException {
        User user = new User();
        user.setName(username1);
        user.setPassword(password1);
        mSetting.setCache(user);

    }
//
//
///        String url = getResources().getString(R.string.logurl);
//        System.out.println("url= " + getResources().getString(R.string.logurl));
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("username", username1);
//        params.add("password", password1);
//
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//
//                String get = new String(bytes);
//                if (get.equals("1")) {
//                    Toast.makeText(getApplicationContext(), "欢迎" + username1 + "登录", Toast.LENGTH_SHORT).show();
//
//                    if (mRemPwdCkb.isChecked()) {
//
//                        SharedPreferences.Editor editor = mSharedPreferences.edit();
//                        editor.putString("USER_NAME", username1);
//                        editor.putString("PASSWORD", password1);
//                        UserLoginApp userLoginApp = (UserLoginApp) getApplication();
//                        userLoginApp.setUserName(username1);
//                        userLoginApp.setPassWord(password1);
//                        editor.commit();
//                    }
//
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("USER_NAME", mUserNameEdt.getText().toString());
//
//                    setResult(4, intent);
//                    //                LoginActivity.this.startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(getApplicationContext(), "账号或者密码错误", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
//
//    }

    @Override
    public void onClick(View view) {

    }

//    class LoginButtonLis implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//
//            boolean flag = true;
//            String userName = mUserNameEdt.getText().toString().trim();
//
//            if (userName.equals("") || userName == null) {
//                Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
//                mUserNameEdt.setFocusable(true);
//                mUserNameEdt.requestFocus();
//                mUserNameEdt.setFocusableInTouchMode(true);
//                flag = false;
//            }
//
//            String pwd = mPasswordEdt.getText().toString().trim();
//            if (pwd.equals("") || pwd == null) {
//                Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
//                mPasswordEdt.requestFocus();
//                mPasswordEdt.setFocusable(true);
//                mPasswordEdt.setFocusableInTouchMode(true);
//                flag = false;
//            }
//
//            //登录成功和记住密码框为选中状态才保存用户信息
//            try {
//                if (flag) {
//                    Login(userName, pwd);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == 6) {
            mUserNameEdt.setText(data.getStringExtra("USERNAME"));
            mPasswordEdt.setText(data.getStringExtra("mPasswordEdt"));
        }
    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}






