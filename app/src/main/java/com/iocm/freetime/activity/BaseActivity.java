package com.iocm.freetime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * Created by liubo on 15/11/12.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener, CommonToolBar.OnCommonToolBarClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
