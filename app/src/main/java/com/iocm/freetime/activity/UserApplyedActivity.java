package com.iocm.freetime.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.adapter.UserjoinAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户申请的任务
 * Created by Administrator on 2015/2/4.
 */
public class UserApplyedActivity extends BaseActivity {
    private ListView listView;
    private TextView loading;
    private ExpandableListView expandableListView;
    private UserjoinAdapter userjoinAdapter = null;
    private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
    @Override
    void initView() {
        setContentView(R.layout.user_join_layout);

        mDialog.show();
        initViews();
        initDatas();
    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {

    }



    private void initDatas() {

        userjoinAdapter = new UserjoinAdapter(getApplicationContext(), list, getResources().getString(R.string.deleteJoinurl));
        listView.setAdapter(userjoinAdapter);
    }

    private void initViews() {
        loading = (TextView) findViewById(R.id.loading1);
        listView = (ListView) findViewById(R.id.ltv_userJoin_showActivity);
        expandableListView = (ExpandableListView) findViewById(R.id.eplv_userJoin_showLike);
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
