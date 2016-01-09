package com.iocm.freetime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CountCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 15/12/5.
 */
public class PersonalHomePageActivity extends BaseActivity {

    private static final String TAG = "PersonalHomePageActivity";

    private RecyclerView taskRecyclerView;

    private ArrayList<Tasks> mList;

    private ImageView back;

    private String mUserId;

    private TextView name;
    private TextView mobile;

    private Button followBtn;
    private TextView followTextview;
    private TextView fansTextView;


    @Override
    void initView() {
        setContentView(R.layout.activity_personal_homepage);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        back = (ImageView) findViewById(R.id.back);

        name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.mobile);

        followBtn = (Button) findViewById(R.id.followBtn);

        followTextview = (TextView) findViewById(R.id.followTextView);
        fansTextView = (TextView) findViewById(R.id.fansTextView);


    }

    @Override
    void initListener() {

        back.setOnClickListener(this);
        followBtn.setOnClickListener(this);

        followTextview.setOnClickListener(this);
        fansTextView.setOnClickListener(this);

    }

    @Override
    void loadData() {
        taskRecyclerView.setAdapter(new PersonalHomePageAdapter());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUserId = bundle.getString(Constant.Key.UserId);

        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            followBtn.setVisibility(View.GONE);
        }

        mList = new ArrayList<>();
        AVQuery<AVUser> userQ = new AVQuery("_User");

        userQ.whereEqualTo("objectId", mUserId);

        userQ.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                name.setText(list.get(0).getUsername());
            }
        });
        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        query.orderByDescending("createdAt");
        query.whereEqualTo(Constant.LeancloundTable.TaskTable.userId, mUserId);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
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
                    mList.add(tasks);
                }
                taskRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });


        getFollow();
        getFans();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            onBackPressed();
        } else if (id == R.id.followBtn) {
            follow();
        } else if (id == R.id.recycler_item) {
            PersonalHomePageAdapter.PersonalHomePageViewHolder vh = (PersonalHomePageAdapter.PersonalHomePageViewHolder) v.getTag();
            Tasks tasks = mList.get(vh.getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.Key.Task, tasks);
            jumpActivity(TaskDetailActivity.class, bundle);
        } else if (id == R.id.fansTextView) {
            Intent intent = new Intent(PersonalHomePageActivity.this, FansOrFollowActivity.class);
            intent.putExtra(Constant.User.userId, mUserId);
            intent.putExtra("fansOrfollow", 1);
            startActivity(intent);
        } else if (id == R.id.followTextView) {
            Intent intent = new Intent(PersonalHomePageActivity.this, FansOrFollowActivity.class);
            intent.putExtra(Constant.User.userId, mUserId);
            intent.putExtra("fansOrfollow", 2);
            startActivity(intent);
        }

    }

    private void follow() {

        AVObject object = new AVObject("Follow");
        object.put("username", AVUser.getCurrentUser().getUsername());
        object.put("userId", AVUser.getCurrentUser().getObjectId());
        object.put("followId", mUserId);
        object.put("followName", name.getText().toString());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    CustomUtils.showToast(mContext, "关注成功");
                    followBtn.setEnabled(false);
                    followBtn.setText("已关注");
                } else {
                    CustomUtils.showToast(mContext, "关注失败!");
                }
            }
        });
    }


    private void getFollow() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("userId", mUserId);

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    followTextview.setText("关注: " + list.size() + "人");
                }
            }
        });
    }


    private void getFans() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("followId", mUserId);

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    fansTextView.setText("粉丝: " + list.size() + "人");
                    for (AVObject object : list) {
                        String id = object.getString("userId");
                        if (id.equals(AVUser.getCurrentUser().getObjectId())) {
                            followBtn.setEnabled(false);
                            followBtn.setText("已关注");

                            break;
                        }
                    }
                }
            }
        });

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }

    class PersonalHomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recnetly_released, parent, false);
            return new PersonalHomePageViewHolder(root);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PersonalHomePageViewHolder vh = (PersonalHomePageViewHolder) holder;
            Tasks tasks = mList.get(position);

            vh.titleTextView.setText("标题:" + tasks.getTitle());
            vh.bodyTextView.setText("内容:" + tasks.getBody());
            vh.endTimeTextView.setText("止于:" + tasks.getEndTime());
            vh.joinedNumTextView.setText(String.format(getResources().getString(R.string.joined_person_num), tasks.getJoinedNum()));


        }

        @Override
        public int getItemCount() {
            return null == mList ? 0 : mList.size();
        }

        class PersonalHomePageViewHolder extends RecyclerView.ViewHolder {

            public TextView titleTextView;
            public TextView bodyTextView;
            public TextView endTimeTextView;
            public TextView joinedNumTextView;

            public PersonalHomePageViewHolder(View itemView) {
                super(itemView);
                itemView.setTag(this);
                itemView.setOnClickListener(PersonalHomePageActivity.this);
                titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
                bodyTextView = (TextView) itemView.findViewById(R.id.bodyTextView);
                endTimeTextView = (TextView) itemView.findViewById(R.id.endTimeTextView);
                joinedNumTextView = (TextView) itemView.findViewById(R.id.joinedNumTextView);
            }
        }
    }


}
