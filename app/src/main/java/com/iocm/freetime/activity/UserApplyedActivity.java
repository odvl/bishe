package com.iocm.freetime.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.adapter.UserjoinAdapter;
import com.iocm.freetime.bean.MessageModel;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户申请的任务
 * Created by Administrator on 2015/2/4.
 */
public class UserApplyedActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private CommonToolBar toolBar;

    private ArrayList<MessageModel> tList = new ArrayList<>();

    private RecyclerView taskRecyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    void initView() {
        setContentView(R.layout.user_join_layout);

        toolBar = (CommonToolBar) findViewById(R.id.toolbar);
        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.userTaskSwipeRefreshLayout);

    }

    @Override
    void initListener() {
        toolBar.setOnCommonToolBarClickListener(this);
        refreshLayout.setOnRefreshListener(this);

    }

    @Override
    void loadData() {
        taskRecyclerView.setAdapter(new TAdapter());
        refreshLayout.setRefreshing(true);
        AVQuery<AVObject> query = new AVQuery<>("Apply");
        query.whereEqualTo("joinUserId", AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("state", 1);

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                refreshLayout.setRefreshing(false);
                if (list != null) {

                    tList.clear();
                    for (int i = 0; i < list.size(); i++) {
                        AVObject object = list.get(i);
                        MessageModel model = new MessageModel();
                        model.setPublishUserId(object.getString("publishUserId"));
                        model.setPublishUserName(object.getString("publishUserName"));
                        model.setJoinUserId(object.getString("joinUserId"));
                        model.setJoinUserName(object.getString("joinUserName"));
                        model.setState(object.getNumber("state").intValue());
                        model.setLeaveMessage(object.getString("message"));
                        model.setTaskName(object.getString("taskName"));
                        model.setTaskId(object.getString("taskId"));
                        model.setObjectId(object.getObjectId());
                        tList.add(model);
                    }

                    taskRecyclerView.getAdapter().notifyDataSetChanged();

                }
            }
        });

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

    class TAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_about_tasks, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageModel tasks = tList.get(position);
            TViewHolder vh = (TViewHolder) holder;
            vh.nameTextView.setText("任务名称："+tasks.getTaskName());
            vh.joinedPerNumTextView.setText("发布人："+tasks.getPublishUserName());


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
                itemView.setOnClickListener(UserApplyedActivity.this);
                itemView.setTag(this);

                nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
                joinedPerNumTextView = (TextView) itemView.findViewById(R.id.joinedNumTextView);

            }
        }
    }
}
