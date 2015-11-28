package com.iocm.freetime.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * Created by liubo on 15/11/26.
 */
public class CollectionsTaskActivity extends BaseActivity {

    private static final String TAG = "CollectionsTaskActivity";

    private CommonToolBar toolBar;

    private RecyclerView collectionTaskRecyclerView;



    @Override
    void initView() {
        setContentView(R.layout.activity_task_collections);

        toolBar = (CommonToolBar) findViewById(R.id.toolbar);

        collectionTaskRecyclerView = (RecyclerView) findViewById(R.id.collectionTaskRecyclerView);
        collectionTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    void initListener() {
        toolBar.setOnCommonToolBarClickListener(this);

    }

    @Override
    void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void leftClickListener() {
        finish();
    }

    @Override
    public void rightClickListener() {

    }
}
