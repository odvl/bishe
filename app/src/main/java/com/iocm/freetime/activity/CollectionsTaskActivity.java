package com.iocm.freetime.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.wedgets.CommonToolBar;

import java.util.List;

/**
 * Created by liubo on 15/11/26.
 */
public class CollectionsTaskActivity extends BaseActivity {

    private static final String TAG = "CollectionsTaskActivity";

    private CommonToolBar toolBar;

    private RecyclerView collectionTaskRecyclerView;

    private List<Tasks> mTaskList;


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
        mTaskList = new Select().from(Tasks.class).where("like = ?" , true).execute();
        collectionTaskRecyclerView.setAdapter(new CollectionTaskAdapter());
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

    class CollectionTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_center_item_layout, null);

            return new CollectionViewHolder(root);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            CollectionViewHolder vh = (CollectionViewHolder) holder;
            Tasks tasks = mTaskList.get(position);
            vh.mUsername.setText(tasks.getTitle());
            vh.mTaskContent.setText(tasks.getBody());
            vh.mTaskFollow.setImageResource(R.drawable.follow);
            vh.mTaskPeopleJoinNum.setText(String.format(getResources().getString(R.string.joined_person_num, 6)));


        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }

        class CollectionViewHolder extends RecyclerView.ViewHolder {

            private ImageView mUserPhoto;
            private ImageView mTaskFollow;
            private ImageView shareImageView;

            private TextView mUsername;
            private TextView mTaskName;
            private TextView mTaskContent;
            private TextView mTaskPeopleJoinNum;

            private RelativeLayout taskHeadContent;

            public CollectionViewHolder(View itemView) {
                super(itemView);

                mTaskFollow = (ImageView) itemView.findViewById(R.id.task_follow);
                mUserPhoto = (ImageView) itemView.findViewById(R.id.task_user_photo);
                shareImageView = (ImageView) itemView.findViewById(R.id.shareImageView);

                mUsername = (TextView) itemView.findViewById(R.id.task_user_name);
                mTaskName = (TextView) itemView.findViewById(R.id.task_name);
                mTaskContent = (TextView) itemView.findViewById(R.id.task_content);
                mTaskPeopleJoinNum = (TextView) itemView.findViewById(R.id.task_people_joined_num);

                taskHeadContent = (RelativeLayout) itemView.findViewById(R.id.task_head_content);

                taskHeadContent.setOnClickListener(CollectionsTaskActivity.this);
                taskHeadContent.setTag(this);

                shareImageView.setOnClickListener(CollectionsTaskActivity.this);

                mTaskFollow.setOnClickListener(CollectionsTaskActivity.this);
                mTaskFollow.setTag(this);
            }
        }
    }
}
