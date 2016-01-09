package com.iocm.freetime.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.wedgets.dialog.CommonDialog;

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
    private LocationClient locationClient;

    private boolean isFirst = true;
    private AVGeoPoint point = new AVGeoPoint();

    private Button distanceBtn;
    private Button hotBtn;
    private SwipeRefreshLayout swipe;

    private boolean isLoad = false;


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

        locationClient = new LocationClient(getActivity());
        // 设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 是否打开GPS
        option.setCoorType("bd09ll"); // 设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
        // option.setProdName("LocationDemo"); //
        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        // option.setScanSpan(UPDATE_TIME);// 设置定时定位的时间间隔。单位毫秒
        locationClient.setLocOption(option);

        // 注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                // TODO Auto-generated method stub
                if (location == null) {
                    return;
                }
                if (isFirst) {
                    point.setLatitude(location.getLatitude());
                    point.setLongitude(location.getLongitude());
                    isFirst = false;
                }
            }
        });


        final CommonDialog dialog = new CommonDialog(getActivity());
        dialog.show();
        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        query.whereNear("point", point);
        query.setLimit(50);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                dialog.dismiss();
                if (null != list) {
                    mItemArray.clear();
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
                        tasks.setJoinedNum(object.getNumber("upvotes").intValue());
                        tasks.setBuild(object.getString(Constant.LeancloundTable.TaskTable.build));
                        mItemArray.add(new ItemData<Tasks>(0, tasks));

                    }
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
        mLikeList = new Select().from(Tasks.class).execute();
    }

    public void move2Top() {
        mRecyclerView.scrollToPosition(0);
    }

    private void setupViews(View root) {

        swipe = (SwipeRefreshLayout) root.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                getDistance();
            }
        });

        distanceBtn = (Button) root.findViewById(R.id.distance);
        hotBtn = (Button) root.findViewById(R.id.hot);

        hotBtn.setOnClickListener(TaskCenterFragments.this);
        distanceBtn.setOnClickListener(TaskCenterFragments.this);

        mSearchView = (TextView) root.findViewById(R.id.input_edit_text);
        mSearchView.setOnClickListener(TaskCenterFragments.this);

        mMessageCenterView = (ImageView) root.findViewById(R.id.message_center);
        mMessageCenterView.setOnClickListener(TaskCenterFragments.this);

        mScanQRView = (ImageView) root.findViewById(R.id.scan_qr_code);
        mScanQRView.setOnClickListener(TaskCenterFragments.this);

        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(onScrollListener);
        mRecyclerView.setAdapter(new TaskCenterRecyclerAdapter());

        fabtn_show_action = (FloatingActionButton) root.findViewById(R.id.fabtn_show_action);
        fabtn_show_action.setOnClickListener(this);

    }

    private void init() {
        mItemArray = new RecyclerArray();
    }

    @Override
    public void onClick(View view) {
        if (!CustomUtils.checkUserLogin(getActivity())) {
            CustomUtils.showToast(getActivity(), "请先登陆。");
            return;
        }

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
                Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
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
                    tasks.delete();
                    //更新数据库
                    updateDatabase(tasks);
                    //更新列表
                    viewHolder.mTaskFollow.setImageResource(R.drawable.unfollow);
                    mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                } else {
                    tasks.save();
                    updateDatabase(tasks);
                    viewHolder.mTaskFollow.setImageResource(R.drawable.follow);
                    mRecyclerView.getAdapter().notifyItemChanged(viewHolder.getAdapterPosition());

                }
                break;
            }

            case R.id.hot: {
                getHot();
                break;
            }
            case R.id.distance: {
                getDistance();
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
        loadData();
    }

    private void jumpActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void getHot() {
        final CommonDialog dialog = new CommonDialog(getActivity());
        dialog.show();
        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        query.orderByDescending("upvotes");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                dialog.dismiss();
                if (null != list) {
                    mItemArray.clear();
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
                        tasks.setJoinedNum(object.getNumber("upvotes").intValue());
                        tasks.setBuild(object.getString(Constant.LeancloundTable.TaskTable.build));
                        mItemArray.add(new ItemData<Tasks>(0, tasks));

                    }
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    public void getDistance() {
        final CommonDialog dialog = new CommonDialog(getActivity());
        dialog.show();
        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        query.whereNear("point", point);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                dialog.dismiss();
                if (null != list) {
                    mItemArray.clear();
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
                        tasks.setJoinedNum(object.getNumber("upvotes").intValue());
                        tasks.setBuild(object.getString(Constant.LeancloundTable.TaskTable.build));
                        mItemArray.add(new ItemData<Tasks>(0, tasks));

                    }
                    mRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    class TaskCenterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private int mPosition = -1;

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
            if (position > mPosition) {
                mPosition = position;
                _vh.set.start();
            } else {
                mPosition = position;
            }

            for (Tasks tasks : mLikeList) {
                if (tasks.getObjectId().equals(_task.getObjectId())) {
                    _vh.mTaskFollow.setImageResource(R.drawable.follow);
                } else {
                    _vh.mTaskFollow.setImageResource(R.drawable.unfollow);
                }
            }
            _vh.mUsername.setText(_task.getName());
            _vh.mTaskName.setText(_task.getTitle());
            _vh.mTaskContent.setText(_task.getBody());
            _vh.mTaskPeopleJoinNum.setText(String.format(getString(R.string.joined_person_num), _task.getJoinedNum()));

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

            AnimatorSet set;

            private RelativeLayout taskHeadContent;

            public TaskCenterItemViewHolder(View itemView) {
                super(itemView);

                set = new AnimatorSet();
                set.playTogether(ObjectAnimator.ofFloat(itemView, View.TRANSLATION_Y, -250, 0),
                        ObjectAnimator.ofFloat(itemView, View.ALPHA, 0f, 1f));
                set.setDuration(300);

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
                shareImageView.setTag(this);

                mTaskFollow.setOnClickListener(TaskCenterFragments.this);
                mTaskFollow.setTag(this);
            }
        }
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        int position = 0;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            position = manager.findLastVisibleItemPosition();
            if (position == mItemArray.size() - 1 && newState == RecyclerView.SCROLL_STATE_IDLE) {
                CustomUtils.showToast(mContext, "暂无新数据！");
            }

        }
    };
    @Override
    public void onStart() {
        super.onStart();
        locationClient.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationClient.stop();
    }
}

