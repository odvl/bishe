package com.iocm.freetime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.Calendar;

/**
 * Created by Administrator on 2015/1/22.
 */
public class TaskDetailActivity extends BaseActivity {


    private int activity_id = -1;
    private String aname, adate, atitle;
    private String build;
    private double longitude;
    private double latitude;
    private Calendar calendar = Calendar.getInstance();
    private int beginYear = calendar.get(Calendar.YEAR);
    private int beginMonth = calendar.get(Calendar.MONTH);
    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);

    private CommonToolBar mToolbar;

    private TextView tv_username;
    private TextView tv_title;
    private TextView tv_detail;
    private TextView tv_mobile;
    private TextView tv_time;
    private TextView tv_location;

    private ImageView iv_call;
    private ImageView iv_location;

    private Button btn_add_collection;
    private Button btn_apply;

    private Context mContext;


    @Override
    void initView() {

        setContentView(R.layout.activity_task_detail);

        mContext = TaskDetailActivity.this;

        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);
        mToolbar.setOnCommonToolBarClickListener(this);

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_detail = (TextView) findViewById(R.id.tv_task_detail);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_location = (TextView) findViewById(R.id.tv_location);

        iv_call = (ImageView) findViewById(R.id.iv_call);
        iv_call.setOnClickListener(this);

        iv_location = (ImageView) findViewById(R.id.iv_location);
        iv_location.setOnClickListener(this);

        btn_add_collection = (Button) findViewById(R.id.add_collect_btn);
        btn_add_collection.setOnClickListener(this);

        btn_apply = (Button) findViewById(R.id.apply_btn);
        btn_apply.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Tasks tasks = (Tasks) bundle.getSerializable(Constant.Key.Task);
        latitude = tasks.getLatitude();
        longitude = tasks.getLongitude();
        build = tasks.getBuild();

        tv_username.setText(tasks.getName());
        tv_time.setText(tasks.getBeginTime() +"-" + tasks.getEndTime());
        tv_title.setText(tasks.getTitle());
        tv_detail.setText(tasks.getBody());
        tv_mobile.setText(tasks.getPhoneNumber());
        tv_location.setText(tasks.getBuild());

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == iv_call.getId()) {
            CustomUtils.makeCall(mContext, tv_mobile.getText().toString().trim());
        } else if (id == iv_location.getId()) {
            TLog.d("liubo", "build +" + latitude);

            Bundle bundle = new Bundle();
            bundle.putDouble(Constant.LeancloundTable.TaskTable.taskLatitude, latitude);
            bundle.putDouble(Constant.LeancloundTable.TaskTable.taskLongitude, longitude);
            bundle.putString(Constant.LeancloundTable.TaskTable.build, build);
            jumpActivity(MapActivity.class, bundle);

        } else if (id == btn_add_collection.getId()) {

        }

    }

    @Override
    public void leftClickListener() {
        finish();
    }

    @Override
    public void rightClickListener() {

    }
}
