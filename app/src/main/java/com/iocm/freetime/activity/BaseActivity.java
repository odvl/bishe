package com.iocm.freetime.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iocm.freetime.wedgets.CommonToolBar;
import com.iocm.freetime.wedgets.dialog.CommonDialog;

/**
 * Created by liubo on 15/11/12.
 */
public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener,
        CommonToolBar.OnCommonToolBarClickListener{

    protected CommonDialog mDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new CommonDialog(this);
        initView();
        initListener();
        loadData();
    }

    abstract void initView();

    abstract void initListener();

    abstract void loadData();

    public void jumpActivityForResult(Class clazz, int code) {
        Intent intent = new Intent(this, clazz);
        this.startActivityForResult(intent, code);
    }

    public BaseActivity getActivity() {
        return this;
    }

    /**
     * 获取焦点
     * @param v
     */
    public void getFocus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
    }

    public void hideSoft() {
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(null, 0);
    }
}
