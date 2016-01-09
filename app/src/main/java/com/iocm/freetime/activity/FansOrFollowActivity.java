package com.iocm.freetime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.UserValue;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.CommonToolBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 16/1/6.
 */
public class FansOrFollowActivity extends BaseActivity {

    RecyclerView recyclerView;
    CommonToolBar commonToolBar;

    private List<UserValue> userList;
    private TextView empty;
    private String mUserId;

    @Override
    void initView() {
        setContentView(R.layout.activity_fansorfollow);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        commonToolBar = (CommonToolBar) findViewById(R.id.toolbar);

        empty = (TextView) findViewById(R.id.empty);

    }

    @Override
    void initListener() {
        commonToolBar.setOnCommonToolBarClickListener(this);
    }

    @Override
    void loadData() {
        userList = new ArrayList<>();
        recyclerView.setAdapter(new FAdapter());
        Intent intent = getIntent();
        int type = intent.getIntExtra("fansOrfollow", 1);
        mUserId = intent.getStringExtra(Constant.User.userId);

        if (type == 1) {
            getFans();
            commonToolBar.setTitleStr("粉丝");
        } else {
            getFollow();
            commonToolBar.setTitleStr("关注");
        }
    }

    private void getFollow() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("userId", mUserId);

        mDialog.show();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mDialog.dismiss();
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    userList.clear();
                    for (AVObject object : list) {
                        UserValue userValue = new UserValue(object.getString("followName"), object.getString("followId"));
                        userList.add(userValue);
                    }
                    if (userList.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }


    private void getFans() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("followId", mUserId);
        mDialog.show();
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mDialog.dismiss();
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    userList.clear();
                    for (AVObject object : list) {
                        UserValue userValue = new UserValue(object.getString("username"), object.getString("userId"));
                        userList.add(userValue);
                    }
                    if (userList.size() == 0) {
                        empty.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.recycler_item) {
            FAdapter.FViewHolder vh = (FAdapter.FViewHolder) v.getTag();
            String userId = userList.get(vh.getAdapterPosition()).getUserId();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.Key.UserId, userId);
            jumpActivity(PersonalHomePageActivity.class, bundle);
        }

    }

    class FAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new FViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fansorfollow, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            FViewHolder vh = (FViewHolder) holder;
            UserValue value = userList.get(position);
            vh.name.setText(value.getUsername());
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        private class FViewHolder extends RecyclerView.ViewHolder {

            private TextView name;

            public FViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(FansOrFollowActivity.this);
                itemView.setTag(this);
                name = (TextView) itemView.findViewById(R.id.username);
            }
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
