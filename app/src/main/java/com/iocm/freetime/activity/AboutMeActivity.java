package com.iocm.freetime.activity;

import android.view.View;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * Created by liubo on 15/12/28.
 */
public class AboutMeActivity extends BaseActivity {
    CommonToolBar toolBar ;
    @Override
    void initView() {
        setContentView(R.layout.activity_about_me);
        toolBar = (CommonToolBar) findViewById(R.id.toolbar);

    }

    @Override
    void initListener() {
        toolBar.setOnCommonToolBarClickListener(this);
    }

    @Override
    void loadData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClickListener() {
        finish();

    }

    @Override
    public void rightClickListener() {

    }
}
