package com.iocm.freetime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.CreateTaskActivity;
import com.iocm.freetime.activity.MessageCenterActivity;
import com.iocm.freetime.activity.PersonalHomePageActivity;
import com.iocm.freetime.activity.SearchTaskActivity;
import com.iocm.freetime.activity.TaskDetailActivity;
import com.iocm.freetime.base.ItemData;
import com.iocm.freetime.base.RecyclerArray;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.TLog;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liubo on 15/7/13.
 */
public class TaskCenterFragments extends TaskFragments
        implements View.OnClickListener {

    private static final String TAG = TaskCenterFragments.class.getSimpleName();

    private Context mContext;

    private TextView mSearchView;
    private ImageView mScanQRView;
    private ImageView mMessageCenterView;

    private RecyclerView mRecyclerView;
    private RecyclerArray mItemArray;

    private List<Tasks> mLikeList;

    private FloatingActionButton fabtn_show_action;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _root = inflater.inflate(R.layout.fragment_task_center, container, false);
        mContext = getActivity();
        init();
        setupViews(_root);
        loadData();
        return _root;
    }

    private void loadData() {
        mLikeList = new Select().from(Tasks.class).where("like = ?", true).execute();
        Log.d("liubo", "mLikeList size" + mLikeList.size());
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

            case R.id.task_user_name:
            case R.id.task_user_photo: {
                TaskCenterRecyclerAdapter.TaskCenterItemViewHolder vh = (TaskCenterRecyclerAdapter.TaskCenterItemViewHolder) view.getTag();
                ItemData<Tasks> itemData = mItemArray.get(vh.getAdapterPosition());
                Tasks tasks = itemData.getData();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.Key.UserId, tasks.getUserId());
                jumpActivity(PersonalHomePageActivity.class, bundle);
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

            case R.id.task_head_content: {
                TaskCenterRecyclerAdapter.TaskCenterItemViewHolder vh = (TaskCenterRecyclerAdapter.TaskCenterItemViewHolder) view.getTag();
                int position = vh.getAdapterPosition();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.Key.Task, (Serializable) mItemArray.get(position).getData());
                jumpActivity(TaskDetailActivity.class, bundle);
                break;
            }

            case R.id.fabtn_show_action: {
                jumpActivity(CreateTaskActivity.class);
                break;
            }

            case R.id.shareImageView: {
                //分享
                break;
            }

            case R.id.task_follow: {
                //收藏
                TaskCenterRecyclerAdapter.TaskCenterItemViewHolder viewHolder =
                        (TaskCenterRecyclerAdapter.TaskCenterItemViewHolder) view.getTag();

                ItemData<Tasks> itemData = mItemArray.get(viewHolder.getAdapterPosition());
                Tasks tasks = itemData.getData();
                if (tasks.isLike()) {
                    tasks.setLike(false);
                    //更新数据库
                    updateDatabase(tasks);
                    //更新列表
                    viewHolder.mTaskFollow.setImageResource(R.drawable.unfollow);
                    mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                } else {
                    tasks.setLike(true);
                    updateDatabase(tasks);
                    viewHolder.mTaskFollow.setImageResource(R.drawable.follow);
                    mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                }
                break;
            }



        }
    }

    /**
     * 更新数据库
     *
     * @param tasks
     */
    private void updateDatabase(Tasks tasks) {
        tasks.save();
        if (tasks.isLike()) {
            mLikeList.add(tasks);
        } else {
            mLikeList.remove(tasks);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void jumpActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
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

            if (mLikeList.contains(_task)) {
                _vh.mTaskFollow.setImageResource(R.drawable.follow);
            } else {
                _vh.mTaskFollow.setImageResource(R.drawable.unfollow);
            }
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
            private ImageView mTaskFollow;
            private ImageView shareImageView;

            private TextView mUsername;
            private TextView mTaskName;
            private TextView mTaskContent;
            private TextView mTaskPeopleJoinNum;

            private RelativeLayout taskHeadContent;

            public TaskCenterItemViewHolder(View itemView) {
                super(itemView);

                mTaskFollow = (ImageView) itemView.findViewById(R.id.task_follow);
                mUserPhoto = (ImageView) itemView.findViewById(R.id.task_user_photo);
                shareImageView = (ImageView) itemView.findViewById(R.id.shareImageView);

                mUsername = (TextView) itemView.findViewById(R.id.task_user_name);
                mTaskName = (TextView) itemView.findViewById(R.id.task_name);
                mTaskContent = (TextView) itemView.findViewById(R.id.task_content);
                mTaskPeopleJoinNum = (TextView) itemView.findViewById(R.id.task_people_joined_num);

                taskHeadContent = (RelativeLayout) itemView.findViewById(R.id.task_head_content);

                mUserPhoto.setOnClickListener(TaskCenterFragments.this);
                mUsername.setOnClickListener(TaskCenterFragments.this);
                mUserPhoto.setTag(this);
                mUsername.setTag(this);

                taskHeadContent.setOnClickListener(TaskCenterFragments.this);
                taskHeadContent.setTag(this);

                shareImageView.setOnClickListener(TaskCenterFragments.this);

                mTaskFollow.setOnClickListener(TaskCenterFragments.this);
                mTaskFollow.setTag(this);
            }
        }
    }
}
