package com.iocm.freetime.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.wedgets.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户发布的任务
 * Created by Administrator on 2015/2/4.
 */
public class CreatedTaskListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "CreatedTaskListActivity";

    private CommonToolBar toolBar;

    private RecyclerView taskRecyclerView;

    private SwipeRefreshLayout userTaskSwipeRefreshLayout;

    private ArrayList<Tasks> tList = new ArrayList<>();

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

        taskRecyclerView.setAdapter(new TAdapter());

        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        userTaskSwipeRefreshLayout.setRefreshing(true);
        query.orderByDescending("createdAt");
        query.whereEqualTo(Constant.LeancloundTable.TaskTable.userId, AVUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (userTaskSwipeRefreshLayout.isRefreshing()) {
                    userTaskSwipeRefreshLayout.setRefreshing(false);
                }
                if (null != list) {
                    tList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        AVObject object = list.get(i);
                        Tasks tasks = new Tasks();
                        tasks.setObjectId(object.getObjectId());
                        tasks.setUserId(object.getString(Constant.LeancloundTable.TaskTable.userId));
                        tasks.setTitle(object.getString(Constant.LeancloundTable.TaskTable.taskTitle));
                        tasks.setBody(object.getString(Constant.LeancloundTable.TaskTable.taskDetail));
                        tasks.setBeginTime(object.getString(Constant.LeancloundTable.TaskTable.taskBeginTime));
                        tasks.setEndTime(object.getString(Constant.LeancloundTable.TaskTable.taskEndTime));
                        tasks.setLatitude(object.getAVGeoPoint(Constant.LeancloundTable.TaskTable.point).getLatitude());
                        tasks.setLongitude(object.getAVGeoPoint(Constant.LeancloundTable.TaskTable.point).getLongitude());
                        tasks.setPhoneNumber(object.getString(Constant.LeancloundTable.TaskTable.taskMobile));
                        tasks.setName(object.getString(Constant.LeancloundTable.TaskTable.username));
                        tasks.setJoinedNum(object.getNumber(Constant.LeancloundTable.TaskTable.joinedNum).intValue());
                        tList.add(tasks);
                    }
                    taskRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.task_content) {
            TAdapter.TViewHolder vh = (TAdapter.TViewHolder) v.getTag();
            int position = vh.getAdapterPosition();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.Key.Task, tList.get(position));
            jumpActivity(TaskDetailActivity.class, bundle);
        }


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
        loadData();
    }

    class TAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_about_tasks, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Tasks tasks = tList.get(position);
            TViewHolder vh = (TViewHolder) holder;
            vh.nameTextView.setText(tasks.getTitle());
            vh.joinedPerNumTextView.setText(String.format(getString(R.string.joined_person_num), tasks.getJoinedNum()));


        }

        @Override
        public int getItemCount() {
            return tList.size();
        }

        class TViewHolder extends RecyclerView.ViewHolder {
            private TextView nameTextView;
            private TextView joinedPerNumTextView;

            public TViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(CreatedTaskListActivity.this);
                itemView.setTag(this);

                nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
                joinedPerNumTextView = (TextView) itemView.findViewById(R.id.joinedNumTextView);

            }
        }
    }
}
