package com.iocm.freetime.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.cache.Cache;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.iocm.freetime.wedgets.dialog.CommonDialog;

/**
 * Created by liubo on 15/11/12.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener,
        CommonToolBar.OnCommonToolBarClickListener {

    protected CommonDialog mDialog;

    protected Context mContext;

    protected Cache cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new CommonDialog(this);
        mContext = this;
        cache = Cache.getInstance(mContext);
        initView(savedInstanceState);
        initView();
        initListener();
        loadData();
    }

    public void initView(Bundle savedInstanceState){}

    abstract void initView();

    abstract void initListener();

    abstract void loadData();

    public void jumpActivity(Class clazz) {
        this.startActivity(new Intent(this, clazz));
    }

    public void jumpActivityForResult(Class clazz, int code) {
        Intent intent = new Intent(this, clazz);
        this.startActivityForResult(intent, code);
    }


    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 获取焦点
     *
     * @param v
     */
    public void getFocus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
    }

    /**
     * 隐藏键盘
     */
    public void hideSoftInputMethod() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (null == view) {
            return;
        }
        inputManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    public boolean isEmpty(String res) {
        if (TextUtils.isEmpty(res)) {
            return true;
        } else {
            return false;
        }
    }

}
