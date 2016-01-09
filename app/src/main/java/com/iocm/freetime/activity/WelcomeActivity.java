package com.iocm.freetime.activity;

import android.os.Handler;
import android.view.View;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 16/1/5.
 */
public class WelcomeActivity extends BaseActivity{
    @Override
    void initView() {
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                jumpActivity(MainActivity.class);
            }
        } ,300);

    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
