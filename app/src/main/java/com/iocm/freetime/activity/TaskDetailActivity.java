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
import com.iocm.freetime.util.CustomUtils;
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
    private String longitude;
    private String latitude;
    private Calendar calendar = Calendar.getInstance();
    private int beginYear = calendar.get(Calendar.YEAR);
    private int beginMonth = calendar.get(Calendar.MONTH);
    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);

    private CommonToolBar mToolbar;

    private TextView tv_username;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        mContext = TaskDetailActivity.this;

        initViews();
        initDatas();
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


//    private void registerAct() {
//        UserLoginApp userLoginApp = (UserLoginApp) getApplication();
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("activity_publish_people", aname);
//        params.add("register_user", userLoginApp.getUserName());
//        params.add("activity_name", atitle);
//        params.add("register_time", beginYear + "-" + beginMonth + "-" + beginDay);
//        params.add("activity_id", "" + activity_id);
//        params.add("build", build);
//        params.add("latitude", latitude);
//        params.add("longitude", longitude);
//
//        String url = getResources().getString(R.string.regActurl);
//        client.post(url, params, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                String get = new String(bytes);
//                if (get.equals("1")) {
//                    Toast.makeText(TaskDetailActivity.this, "请求已发送，等待同意", Toast.LENGTH_SHORT).show();
//                } else if (get.equals("2")) {
//                    Toast.makeText(TaskDetailActivity.this, "您已报名，请不要重复报名", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                throwable.printStackTrace();
//                Toast.makeText(TaskDetailActivity.this, "请求失败，请稍后重试", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }
//
    private void initDatas() {
        Intent intent = getIntent();

//
//        build = bundle.getString("build");
//        latitude = bundle.getString("latitude");
//        longitude = bundle.getString("longitude");
//        aname = bundle.getString("name");
//        atitle = bundle.getString("title") + ":" + bundle.getString("body");
//        name.setText("发布人:  " + bundle.getString("name"));
//        date.setText(bundle.getString("begin_time") + "--" + bundle.getString("end_time"));
//        body.setText(bundle.getString("title") + ":" + bundle.getString("body"));
//        phonenumber.setText(bundle.getString("phonenumber"));
//        loca.setText(bundle.getString("loca"));
//        activity_id = Integer.parseInt(bundle.getString("id"));
        //  Toast.makeText(getApplicationContext(),"id" + activity_id,Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == iv_call.getId()) {
            CustomUtils.makeCall(mContext, tv_mobile.getText().toString().trim());
        } else if (id == iv_location.getId()) {
            jumpActivity(MapActivity.class);

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
