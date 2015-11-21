package com.iocm.freetime.activity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.iocm.administrator.yunxuan.R;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.fragment.LoginFragment;
import com.iocm.freetime.fragment.MeFragment;
import com.iocm.freetime.fragment.TaskCenterFragments;
import com.iocm.freetime.util.NotificationHelper;
import com.iocm.freetime.util.Setting;
import com.iocm.freetime.wedgets.BottomBar;

/**
 * main activity contain two fragments
 *
 * @author liu
 */
public class MainActivity extends BaseActivity implements LoginFragment.OnLoginBtnClickListener,
        BottomBar.OnBottomBarClickListener, BottomBar.OnBottomBarDoubleClickListener,
        MeFragment.LogoutClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    
    /**
     * 定义任务中心，个人中心的fragment
     */
    private TaskCenterFragments mTaskCenterFragments;
    private MeFragment mMeFragments;
    private TaskFragments[] mTaskFragments = new TaskFragments[2];

    private Setting mSetting;

    /**
     * 获取到显示fragment的FrameLayout
     * 以及BottomBar
     */

    private FrameLayout mFragmentContent;
    private BottomBar mBottomBar;

    //纪录按下后退键使的时间
    private long mCurrentTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
        setupViews();
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


    private void setupViews() {
        mFragmentContent = (FrameLayout) findViewById(R.id.fragment_content);
        mBottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        mBottomBar.setOnBottomBarClickListener(this);
        mBottomBar.setOnBottomBarDoubleClickListener(this);

    }



    private void init() {
        mSetting = Setting.getInstance(MainActivity.this);
    }

    @Override
    public void onBottomClick(int position) {
        TaskFragments fragment = obtainFragment(position);
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }

    }

    private TaskFragments obtainFragment(int position) {
        TaskFragments _fragment = mTaskFragments[position];
        if (_fragment == null) {
            switch (position) {
                case 0: {
                    _fragment = new TaskCenterFragments();
                    break;
                }
                case 1: {
                    if (mSetting.getCache() == null) {
                        _fragment = new LoginFragment();
                        LoginFragment loginFragment = (LoginFragment) _fragment;
                        loginFragment.setOnLoginBtnClickListener(MainActivity.this);
                    }else {
                        _fragment = new MeFragment();
                        MeFragment meFragment = (MeFragment) _fragment;
                        meFragment.setLogoutClickListener(this);
                    }
                    break;
                }
            }
        }
        mTaskFragments[position] = _fragment;
        return _fragment;
    }

    /**
     * 连续点击back键两次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mCurrentTimes > 500) {
                NotificationHelper.toast(MainActivity.this, "连续按两次退出");
                mCurrentTimes = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return false;
    }

    /**
     * 点击底部两次返回顶部
     */
    @Override
    public void onBottomBarDoubleClick() {
        TaskCenterFragments _fragment = (TaskCenterFragments) mTaskFragments[0];
        _fragment.move2Top();
    }

    @Override
    public void onClick(View view) {

    }


    /**
     * 登录后重新加载fragment
     */
    @Override
    public void onLoginBtnClick() {
        Log.i(TAG, "onLoginClick");
        MeFragment fragment = new MeFragment();
        fragment.setLogoutClickListener(this);
        mTaskFragments[1] = fragment;
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }

    }

    /**
     * 退出后重新加载fragment
     */
    @Override
    public void logoutClick() {
        Log.i(TAG, "logoutClick");
        LoginFragment fragment = new LoginFragment();
        fragment.setOnLoginBtnClickListener(this);
        mTaskFragments[1] = fragment;
        if (fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
