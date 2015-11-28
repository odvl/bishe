package com.iocm.freetime.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * 用户发布的任务
 * Created by Administrator on 2015/2/4.
 */
public class CreatedTaskListActivity extends BaseActivity {

    private static final String TAG = "CreatedTaskListActivity";

    private CommonToolBar toolBar;

    private RecyclerView taskRecyclerView;

    @Override
    void initView() {
        setContentView(R.layout.user_issue_layout);

        toolBar = (CommonToolBar) findViewById(R.id.toolbar);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
