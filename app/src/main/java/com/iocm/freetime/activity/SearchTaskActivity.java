package com.iocm.freetime.activity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.SearchHistory;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.InputEditText;
import com.ozn.mylibrary.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liubo on 15/7/27.
 */
public class SearchTaskActivity extends BaseActivity {




    private List<SearchHistory> searchHistory = new ArrayList<>();

    private FlowLayout mFlowLayout;

    private InputEditText keyEditText;
    private Button searchButton;
    private ImageView backImageView;

    private RecyclerView recyclerView;

    private ArrayList<Tasks> arrayList;


    @Override
    void initView() {
        setContentView(R.layout.activity_search_task);

        keyEditText = (InputEditText) findViewById(R.id.key);
        searchButton = (Button) findViewById(R.id.search_btn);
        backImageView = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    void initListener() {

        backImageView.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        mFlowLayout = (FlowLayout) findViewById(R.id.flowLayout);


        keyEditText.setOnClearListener(new InputEditText.OnClearListener() {
            @Override
            public void onClear() {
                recyclerView.setVisibility(View.GONE);
                mFlowLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    void loadData() {
        arrayList = new ArrayList<>();
        recyclerView.setAdapter(new SearchAdaper());

        updateHistory();

    }

    private void updateHistory() {
        searchHistory.clear();

        try {
            searchHistory = new Select().from(SearchHistory.class).execute();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        mFlowLayout.removeAllViews();

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);

        for (int i = 0; i < searchHistory.size(); i++) {
            final SearchHistory history = searchHistory.get(i);
            TextView textView = new TextView(this);

            if (!TextUtils.isEmpty(history.getKey())) {
                textView.setText(history.getKey());
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        keyEditText.setText(history.getKey());
                        search();
                    }
                });
            }

            textView.setBackgroundResource(R.drawable.clikable_text_btn_bg);
            mFlowLayout.addView(textView, params);
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == backImageView.getId()) {
            onBackPressed();
        } else if (id == searchButton.getId()) {
            search();
        } else if (id == R.id.searchResultItem) {

            SearchAdaper.SearchHolder vh = (SearchAdaper.SearchHolder) view.getTag();
            Tasks tasks = arrayList.get(vh.getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.Key.Task, tasks);
            jumpActivity(TaskDetailActivity.class, bundle);
        }
    }

    private void search() {
        final String key = keyEditText.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            return;
        }

        AVQuery<AVObject> query = new AVQuery<>(Constant.LeancloundTable.TaskTable.tableName);
        query.whereContains(Constant.LeancloundTable.TaskTable.taskDetail, key);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (null != list) {
                    arrayList.clear();
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
                        tasks.setBuild(object.getString(Constant.LeancloundTable.TaskTable.build));
                        arrayList.add(tasks);

                    }
                    if (arrayList.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        mFlowLayout.setVisibility(View.GONE);

                        SearchHistory history = new SearchHistory();
                        history.setKey(key);
                        history.setTime(System.currentTimeMillis());
                        history.save();
                        updateHistory();

                    } else {
                        CustomUtils.showToast(mContext, "暂无相关内容！");
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    CustomUtils.showToast(mContext, "暂无相关内容！");
                }
            }
        });

    }

    class SearchAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            SearchHolder vh = (SearchHolder) holder;
            Tasks t = arrayList.get(position);

            vh.name.setText(t.getName());
            vh.title.setText(t.getTitle());
            vh.detail.setText(t.getBody());

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        class SearchHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView title;
            TextView detail;

            public SearchHolder(View itemView) {
                super(itemView);
                View v = itemView;
                itemView.setTag(this);
                itemView.setOnClickListener(SearchTaskActivity.this);
                name = (TextView) v.findViewById(R.id.name);
                title = (TextView) v.findViewById(R.id.title);
                detail = (TextView) v.findViewById(R.id.detail);
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
