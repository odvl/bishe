package com.iocm.freetime.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * 用户发布的任务
 * Created by Administrator on 2015/2/4.
 */
public class CreatedTaskListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "CreatedTaskListActivity";

    private CommonToolBar toolBar;

    private RecyclerView taskRecyclerView;

    private SwipeRefreshLayout userTaskSwipeRefreshLayout;

    @Override
    void initView() {
        setContentView(R.layout.user_task_layout);

        toolBar = (CommonToolBar) findViewById(R.id.toolbar);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userTaskSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.userTaskSwipeRefreshLayout);
    }

    @Override
    void initListener() {
        toolBar.setOnCommonToolBarClickListener(this);

        userTaskSwipeRefreshLayout.setOnRefreshListener(this);

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

    @Override
    public void onRefresh() {

    }
}
