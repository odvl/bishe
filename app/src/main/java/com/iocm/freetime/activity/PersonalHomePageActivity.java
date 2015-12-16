package com.iocm.freetime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.base.ItemData;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
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


    @Override
    void initView() {
        setContentView(R.layout.activity_personal_homepage);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        back = (ImageView) findViewById(R.id.back);

        name = (TextView) findViewById(R.id.name);
        mobile = (TextView) findViewById(R.id.mobile);


    }

    @Override
    void initListener() {

        back.setOnClickListener(this);

    }

    @Override
    void loadData() {
        taskRecyclerView.setAdapter(new PersonalHomePageAdapter());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mUserId = bundle.getString(Constant.Key.UserId);

        mList = new ArrayList<>();
        AVQuery<AVUser> userQ = new AVQuery("_User");

        userQ.whereEqualTo("objectId", mUserId);
        TLog.d(Constant.TAG, "size userId " + mUserId);

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
                    tasks.setLatitude(object.getNumber(Constant.LeancloundTable.TaskTable.taskLatitude).doubleValue());
                    tasks.setLongitude(object.getNumber(Constant.LeancloundTable.TaskTable.taskLongitude).doubleValue());
                    tasks.setPhoneNumber(object.getString(Constant.LeancloundTable.TaskTable.taskMobile));
                    tasks.setName(object.getString(Constant.LeancloundTable.TaskTable.username));
                    tasks.setJoinedNum(object.getNumber(Constant.LeancloundTable.TaskTable.joinedNum).intValue());
                    mList.add(tasks);
                }
                taskRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.back) {
            onBackPressed();
        }

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
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recnetly_released, null);
            return new PersonalHomePageViewHolder(root);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PersonalHomePageViewHolder vh = (PersonalHomePageViewHolder) holder;
            Tasks tasks = mList.get(position);

            vh.titleTextView.setText(tasks.getTitle());
            vh.bodyTextView.setText(tasks.getBody());
            vh.endTimeTextView.setText(tasks.getEndTime());
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

                titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
                bodyTextView = (TextView) itemView.findViewById(R.id.bodyTextView);
                endTimeTextView = (TextView) itemView.findViewById(R.id.endTimeTextView);
                joinedNumTextView = (TextView) itemView.findViewById(R.id.joinedNumTextView);
            }
        }
    }


}
