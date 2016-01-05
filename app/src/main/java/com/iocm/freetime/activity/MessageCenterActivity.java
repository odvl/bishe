package com.iocm.freetime.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.base.ItemData;
import com.iocm.freetime.base.RecyclerArray;
import com.iocm.freetime.bean.MessageModel;
import com.iocm.freetime.wedgets.CommonToolBar;

import java.util.List;

/**
 * Created by liubo on 15/7/19.
 */
public class MessageCenterActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "MessageCenterActivity";

    private static final int TYPE_FROM_APPLY = 0;
    private static final int TYPE_TO_APPLY = 1;

    private SwipeRefreshLayout messageCenterSwipeRefreshLayout;

    private CommonToolBar mToolbar;
    private RecyclerArray mItemArray;
    private RecyclerView mRecyclerView;
    private TextView emptyTextView;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_message_center);

        if (savedInstanceState == null) {
            final View _view = findViewById(R.id.message_center_content);
            _view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    _view.getViewTreeObserver().removeOnPreDrawListener(this);
                    loadActivityAnimation(_view);
                    return false;
                }
            });
        }

    }

    @Override
    void initView() {
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        emptyTextView = (TextView) findViewById(R.id.empty);

        messageCenterSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.messageCenterSwipeRefreshLayout);

    }

    @Override
    void initListener() {
        mToolbar.setOnCommonToolBarClickListener(this);

        messageCenterSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    void loadData() {
        mItemArray = new RecyclerArray();
        mRecyclerView.setAdapter(new MessageCenterAdapter());
        getMessage();
    }

    private void getMessage() {
        AVQuery<AVObject> query = new AVQuery<>("Apply");

        messageCenterSwipeRefreshLayout.setRefreshing(true);

        query.whereEqualTo("publishUserId", AVUser.getCurrentUser().getObjectId());
        query.whereEqualTo("state", 0);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                messageCenterSwipeRefreshLayout.setRefreshing(false);
                if (list != null) {
                    mItemArray.clear();
                    for (AVObject object : list) {
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
                        mItemArray.add(new ItemData(TYPE_FROM_APPLY, model));
                    }
                    getToApply();
                }
            }
        });
    }

    private void getToApply() {
        AVQuery<AVObject> query = new AVQuery<>("Apply");

        messageCenterSwipeRefreshLayout.setRefreshing(true);
        query.whereEqualTo("joinUserId", AVUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                messageCenterSwipeRefreshLayout.setRefreshing(false);
                if (list != null) {
                    for (AVObject object : list) {
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
                        mItemArray.add(new ItemData(TYPE_TO_APPLY, model));
                    }
                }else {
                    emptyTextView.setVisibility(View.VISIBLE);
                }
                if (mItemArray.size() == 0) {
                    emptyTextView.setVisibility(View.VISIBLE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                }
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    private void loadActivityAnimation(View _view) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(_view, "translationX", _view.getWidth(), 0),
                ObjectAnimator.ofFloat(_view, "alpha", 0, 1.0f));
        set.setDuration(300);
        set.start();

    }

    @Override
    public void onRefresh() {
        getMessage();

    }

    class MessageCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_apply_layout, parent, false);
            return new MessageApplyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageApplyViewHolder vh = (MessageApplyViewHolder) holder;
            ItemData data = mItemArray.get(position);
            MessageModel model = (MessageModel) data.getData();
            switch (getItemViewType(position)) {
                case TYPE_FROM_APPLY:
                    vh.userName.setText(model.getJoinUserName() + "申请加入! ");
                    vh.taskName.setText(model.getTaskName());
                    vh.leaveMessage.setText(model.getLeaveMessage());
                    break;
                case TYPE_TO_APPLY:
                    vh.taskName.setText(model.getTaskName());
                    if (model.getState() == -1) {
                        vh.userName.setText(model.getPublishUserName() + "拒绝了您的申请！");
                        vh.leaveMessage.setText("您暂时不符合要求!");
                        vh.btnContent.setVisibility(View.GONE);
                    } else if (model.getState() == 0) {
                        vh.userName.setText(model.getPublishUserName() + "正在审核中....");
                        vh.leaveMessage.setText("正在审核中....");
                        vh.btnContent.setVisibility(View.GONE);
                    } else if (model.getState() == 1) {
                        vh.userName.setText(model.getPublishUserName() + "同意您的申请！");
                        vh.leaveMessage.setText("欢迎您的加入，稍后可以电话联系！！");
                        vh.btnContent.setVisibility(View.GONE);
                    }
                    break;
            }

        }

        @Override
        public int getItemViewType(int position) {
            return mItemArray.get(position).getType();
        }

        @Override
        public int getItemCount() {
            return mItemArray.size();
        }

        class MessageApplyViewHolder extends RecyclerView.ViewHolder {

            private TextView userName;
            private TextView taskName;
            private TextView leaveMessage;
            private Button yes;
            Button no;
            private LinearLayout btnContent;


            public MessageApplyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(MessageCenterActivity.this);
                itemView.setTag(this);

                userName = (TextView) itemView.findViewById(R.id.user_name);
                taskName = (TextView) itemView.findViewById(R.id.task_name);
                leaveMessage = (TextView) itemView.findViewById(R.id.user_message_content);
                btnContent = (LinearLayout) itemView.findViewById(R.id.btn_content);

                yes = (Button) itemView.findViewById(R.id.yes);
                no = (Button) itemView.findViewById(R.id.no);
                yes.setTag(this);
                no.setTag(this);
                yes.setOnClickListener(MessageCenterActivity.this);
                no.setOnClickListener(MessageCenterActivity.this);

            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.yes) {
            MessageCenterAdapter.MessageApplyViewHolder vh = (MessageCenterAdapter.MessageApplyViewHolder) view.getTag();
            update(true, vh.getAdapterPosition());
        } else if (id == R.id.no) {
            MessageCenterAdapter.MessageApplyViewHolder vh = (MessageCenterAdapter.MessageApplyViewHolder) view.getTag();
            update(false, vh.getAdapterPosition());
        }

    }

    AVObject object = new AVObject("Apply");

    private void update(boolean b, int p) {

        AVQuery<AVObject> query = new AVQuery<>("Apply");

        MessageModel model;
        model = (MessageModel) mItemArray.get(p).getData();
        object = AVObject.createWithoutData("Apply", model.getObjectId());
        if (b) {
            object.put("state", 1);
            AVObject task = AVObject.createWithoutData("TaskTable", model.getTaskId());
            task.increment("upvotes");
            task.saveInBackground();
        } else {
            object.put("state", -1);
        }
        object.saveInBackground();
        mItemArray.remove(p);
        mRecyclerView.getAdapter().notifyItemRemoved(p);


    }

    @Override
    public void leftClickListener() {
        finish();

    }

    @Override
    public void rightClickListener() {

    }
}
