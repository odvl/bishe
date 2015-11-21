package com.iocm.freetime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.yunxuan.R;
import com.iocm.freetime.activity.CreateTaskActivity;
import com.iocm.freetime.activity.MessageCenterActivity;
import com.iocm.freetime.activity.SearchTaskActivity;
import com.iocm.freetime.activity.TaskDetailActivity;
import com.iocm.freetime.base.ItemData;
import com.iocm.freetime.base.RecyclerArray;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;

import java.io.Serializable;

/**
 * Created by liubo on 15/7/13.
 */
public class TaskCenterFragments extends TaskFragments
        implements View.OnClickListener{

    private static final String TAG = TaskCenterFragments.class.getSimpleName();

    private Context mContext;

    private TextView mSearchView;
    private ImageView mScanQRView;
    private ImageView mMessageCenterView;

    private RecyclerView mRecyclerView;
    private RecyclerArray mItemArray;

    private FloatingActionButton fabtn_show_action;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _root = inflater.inflate(R.layout.fragment_task_center, container, false);
        mContext = getActivity();
        init();
        setupViews(_root);
        return _root;
    }

    public void move2Top() {
        mRecyclerView.scrollToPosition(0);
    }

    private void setupViews(View root) {

        mSearchView = (TextView) root.findViewById(R.id.input_edit_text);
        mSearchView.setOnClickListener(TaskCenterFragments.this);

        mMessageCenterView = (ImageView) root.findViewById(R.id.message_center);
        mMessageCenterView.setOnClickListener(TaskCenterFragments.this);

        mScanQRView = (ImageView) root.findViewById(R.id.scan_qr_code);
        mScanQRView.setOnClickListener(TaskCenterFragments.this);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TaskCenterRecyclerAdapter());

        fabtn_show_action = (FloatingActionButton) root.findViewById(R.id.fabtn_show_action);
        fabtn_show_action.setOnClickListener(this);
    }

    private void init() {
        mItemArray = new RecyclerArray();
        for (int i = 0; i < 100; i++) {
            Tasks _task = new Tasks("title", "body", "begintime", "endeTime", "pho", "name", Tasks.Type.MATCH, "msg", 22.22, 33.22, "build");
            mItemArray.add(new ItemData<Tasks>(0, _task));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.input_edit_text: {
                jumpActivity(SearchTaskActivity.class);
                break;
            }

            case R.id.scan_qr_code: {
                break;
            }

            case R.id.message_center: {
                Intent _intent = new Intent();
                _intent.setClass(getActivity(), MessageCenterActivity.class);
                startActivity(_intent);
                break;
            }

            case R.id.task_center_content: {
                TaskCenterRecyclerAdapter.TaskCenterItemViewHolder vh = (TaskCenterRecyclerAdapter.TaskCenterItemViewHolder) view.getTag();
                int position = vh.getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.Key.Task, (Serializable) mItemArray.get(position).getData());
                jumpActivity(TaskDetailActivity.class, Constant.Key.Task, bundle);
                break;
            }

            case R.id.fabtn_show_action: {
                jumpActivity(CreateTaskActivity.class);
                break;
            }

        }
    }


    @Override
    public void onPause() {
        super.onPause();
        TLog.i(TAG, "onPause");
    }

    @Override
    public void onResume() {
        super.onResume();
        TLog.i(TAG, "onResume");
    }

    private void jumpActivity(Class clazz, String key, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtra(key, bundle);
        startActivity(intent);
    }

    class TaskCenterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View _view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_center_item_layout, null);
            return new TaskCenterItemViewHolder(_view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TaskCenterItemViewHolder _vh = (TaskCenterItemViewHolder) holder;
            ItemData<Tasks> _data = mItemArray.get(position);
            Tasks _task = _data.getData();

            _vh.mUsername.setText(_task.getName());
            _vh.mTaskName.setText(_task.getTitle());
            _vh.mTaskContent.setText(_task.getBody());

        }

        @Override
        public int getItemCount() {
            return mItemArray.size();
        }

        class TaskCenterItemViewHolder extends RecyclerView.ViewHolder {
            private ImageView mUserPhoto;
            private TextView mUsername;
            private ImageView mTaskFollow;
            private TextView mTaskName;
            private TextView mTaskContent;
            private TextView mTaskPeopleJoinNum;
            private View mContent;

            public TaskCenterItemViewHolder(View itemView) {
                super(itemView);

                mUserPhoto = (ImageView) itemView.findViewById(R.id.task_user_photo);
                mUsername = (TextView) itemView.findViewById(R.id.task_user_name);
                mTaskFollow = (ImageView) itemView.findViewById(R.id.task_follow);
                mTaskName = (TextView) itemView.findViewById(R.id.task_name);
                mTaskContent = (TextView) itemView.findViewById(R.id.task_content);
                mTaskPeopleJoinNum = (TextView) itemView.findViewById(R.id.task_people_joined_num);
                mContent = itemView.findViewById(R.id.task_center_content);


                mContent.setOnClickListener(TaskCenterFragments.this);
                mContent.setTag(this);
                mUserPhoto.setTag(this);
                mUserPhoto.setOnClickListener(TaskCenterFragments.this);
                mUsername.setTag(this);
                mUsername.setOnClickListener(TaskCenterFragments.this);
                mTaskFollow.setTag(this);
                mTaskFollow.setOnClickListener(TaskCenterFragments.this);
                mTaskName.setTag(this);
                mTaskName.setOnClickListener(TaskCenterFragments.this);
                mTaskContent.setTag(this);
                mTaskContent.setOnClickListener(TaskCenterFragments.this);
            }
        }
    }
}
