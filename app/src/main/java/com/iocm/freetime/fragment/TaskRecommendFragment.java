package com.iocm.freetime.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.adapter.ExpandAdapter;
import com.iocm.freetime.activity.TaskDetailActivity;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.base.ItemData;
import com.iocm.freetime.base.RecyclerArray;
import com.iocm.freetime.bean.TaskTypeName;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRecommendFragment extends Fragment implements View.OnClickListener {

    //活动属性名常量
    private static final String NAME = "name";
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String PHONENUMBER = "phonenumber";
    private static final String BEGIN_TIME = "begin_time";
    private static final String END_TIME = "end_time";
    private static final String LOCA = "loca";
    private ExpandableListView activity_msg = null;
    private View messageLayout = null;


    private static final int REQUESTID = 3;
    private static final int RESULTID = 4;
    private ArrayList<String> group;

    //初始化装载数据容器
    ArrayList<Map<String, String>> get_job_activity = new ArrayList<Map<String, String>>();
    ArrayList<Map<String, String>> contest_activity = new ArrayList<Map<String, String>>();
    ArrayList<Map<String, String>> meeting_activity = new ArrayList<Map<String, String>>();



    private RecyclerView mRecyclerView;
    private RecyclerArray mItemArray;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        messageLayout = inflater.inflate(R.layout.fragment_task_recommend, container, false);
        init();
        setupViews(messageLayout);
        activity_msg = (ExpandableListView) messageLayout.findViewById(R.id.message_layout_activity_body);

        getact();
        System.out.println("test " + get_job_activity.size());


        ArrayList<Map<String, String>> buttonList = new ArrayList<Map<String, String>>();
        Map<String, String> buttonMap1 = new HashMap<String, String>();
        buttonMap1.put("min_button", "详细信息");
        Map<String, String> buttonMap2 = new HashMap<String, String>();
        buttonMap2.put("detail_button", "我要报名");
        Map<String, String> buttonMap3 = new HashMap<String, String>();
        buttonMap3.put("to_here", "到这里去");
        buttonList.add(buttonMap1);
        buttonList.add(buttonMap2);
        buttonList.add(buttonMap3);

        ArrayList<String> groupList = new ArrayList<>();
        groupList.add("比赛");
        groupList.add("科技");
        groupList.add("娱乐");

        ArrayList<Map<String, String>> activites = new ArrayList<Map<String, String>>();
        Map<String, String> activity_outside_title1 = new HashMap<String, String>();
        activity_outside_title1.put("activity_type", "比赛");
        Map<String, String> activity_outside_title2 = new HashMap<String, String>();
        activity_outside_title2.put("activity_type", "科技");
        Map<String, String> activity_outside_title3 = new HashMap<String, String>();
        activity_outside_title3.put("activity_type", "娱乐");
        activites.add(activity_outside_title1);
        activites.add(activity_outside_title2);
        activites.add(activity_outside_title3);
        group = new ArrayList<>();
        group.add("比赛");
        group.add("科技");
        group.add("娱乐");


        ArrayList<List<Map<String, String>>> all_activity = new ArrayList<List<Map<String, String>>>();
        all_activity.add(contest_activity);
        all_activity.add(meeting_activity);
        all_activity.add(get_job_activity);

        ExpandAdapter expandAdapter = new ExpandAdapter(groupList, all_activity, getActivity());

        activity_msg.setAdapter(expandAdapter);
        activity_msg.setGroupIndicator(null);
        activity_msg.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Intent intent = new Intent();
                    //存入传递值
                    intent.putExtra(NAME, contest_activity.get(childPosition).get(NAME));
                    intent.putExtra(TITLE, contest_activity.get(childPosition).get(TITLE));
                    intent.putExtra(BODY, contest_activity.get(childPosition).get(BODY));
                    intent.putExtra(PHONENUMBER, contest_activity.get(childPosition).get(PHONENUMBER));
                    intent.putExtra(LOCA, contest_activity.get(childPosition).get(LOCA));
                    intent.putExtra(BEGIN_TIME, contest_activity.get(childPosition).get(BEGIN_TIME));
                    intent.putExtra(END_TIME, contest_activity.get(childPosition).get(END_TIME));
                    intent.putExtra("id", contest_activity.get(childPosition).get("id"));
                    intent.putExtra("build", contest_activity.get(childPosition).get("build"));
                    intent.putExtra("latitude", contest_activity.get(childPosition).get("latitude"));
                    intent.putExtra("longitude", contest_activity.get(childPosition).get("longitude"));
                    intent.setClass(getActivity(), TaskDetailActivity.class);
                    getActivity().startActivity(intent);

                } else if (groupPosition == 1) {
                    Intent intent = new Intent();
                    intent.putExtra(NAME, meeting_activity.get(childPosition).get(NAME));
                    intent.putExtra(TITLE, meeting_activity.get(childPosition).get(TITLE));
                    intent.putExtra(BODY, meeting_activity.get(childPosition).get(BODY));
                    intent.putExtra(PHONENUMBER, meeting_activity.get(childPosition).get(PHONENUMBER));
                    intent.putExtra(LOCA, meeting_activity.get(childPosition).get(LOCA));
                    intent.putExtra(BEGIN_TIME, meeting_activity.get(childPosition).get(BEGIN_TIME));
                    intent.putExtra(END_TIME, meeting_activity.get(childPosition).get(END_TIME));
                    intent.putExtra("id", meeting_activity.get(childPosition).get("id"));
                    intent.putExtra("build", meeting_activity.get(childPosition).get("build"));
                    intent.putExtra("longitude", meeting_activity.get(childPosition).get("longitude"));
                    intent.putExtra("latitude", meeting_activity.get(childPosition).get("latitude"));
                    intent.setClass(getActivity(), TaskDetailActivity.class);
                    getActivity().startActivity(intent);

                } else if (groupPosition == 2) {

                    Intent intent = new Intent();
                    intent.putExtra(NAME, get_job_activity.get(childPosition).get(NAME));
                    intent.putExtra(TITLE, get_job_activity.get(childPosition).get(TITLE));
                    intent.putExtra(BODY, get_job_activity.get(childPosition).get(BODY));
                    intent.putExtra(PHONENUMBER, get_job_activity.get(childPosition).get(PHONENUMBER));
                    intent.putExtra(LOCA, get_job_activity.get(childPosition).get(LOCA));
                    intent.putExtra(BEGIN_TIME, get_job_activity.get(childPosition).get(BEGIN_TIME));
                    intent.putExtra(END_TIME, get_job_activity.get(childPosition).get(END_TIME));
                    intent.putExtra("id", get_job_activity.get(childPosition).get("id"));
                    intent.putExtra("build", get_job_activity.get(childPosition).get("build"));
                    intent.putExtra("latitude", get_job_activity.get(childPosition).get("latitude"));
                    intent.putExtra("longitude", get_job_activity.get(childPosition).get("longitude"));
                    intent.setClass(getActivity(), TaskDetailActivity.class);
                    getActivity().startActivity(intent);
                }
                return true;
            }
        });

        return messageLayout;
    }

    private void init() {
        mItemArray = new RecyclerArray();
        mItemArray.add(new ItemData(TYPE_TASK, new TaskTypeName(Constant.TASK.takeSomethingId, Constant.TASK.takeSomethingStr)));
        mItemArray.add(new ItemData(TYPE_TASK, new TaskTypeName(Constant.TASK.findSomethingId, Constant.TASK.findSomethingStr)));

    }

    private void setupViews(View root) {

        mRecyclerView = (RecyclerView) root.findViewById(R.id.task_recommend_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new TaskRecommendRecyclerViewAdapter());

    }

    private static final int TYPE_TASK = 1;

    private class TaskRecommendRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_TASK: {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recommend_item, parent, false);
                    return new TaskViewHolder(view);
                }
            }

            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return mItemArray.get(position).getType();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case TYPE_TASK: {
                    TaskViewHolder vh = (TaskViewHolder) holder;
                    TaskTypeName typeName = (TaskTypeName) mItemArray.get(position).getData();
                    vh.mTaskTypeName.setText(typeName.getTypeName());
                    break;
                }
            }

        }

        @Override
        public int getItemCount() {
            return mItemArray.size();
        }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView mTaskTypeName;

            public TaskViewHolder(View itemView) {
                super(itemView);
                mTaskTypeName = (TextView) itemView.findViewById(R.id.task_type_name);
                itemView.setOnClickListener(TaskRecommendFragment.this);
                itemView.setTag(this);
            }
        }
    }

    private void getact() {
        final AsyncHttpClient client = new AsyncHttpClient();
        String url = getResources().getString(R.string.getacturl);
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                if (statusCode == 200) {
//                    List<String> objects = new ArrayList<String>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if (new String(jsonObject.getString("type").getBytes(), "utf-8").equals("PLAY")) {
                                Map<String, String> map = new HashMap<String, String>();
//                                System.out.println("name is "+ new String(jsonObject.getString("body").getBytes(),"utf-8"));
                                //存入活动的相关信息
                                map.put("name", new String(jsonObject.getString("name").getBytes(), "utf-8"));
                                map.put("type", new String(jsonObject.getString("type").getBytes(), "utf-8"));
                                map.put("begin_time", new String(jsonObject.getString("begin_time").getBytes(), "utf-8"));
                                map.put("end_time", new String(jsonObject.getString("end_time").getBytes(), "utf-8"));
                                map.put("phonenumber", new String(jsonObject.getString("phonenumber").getBytes(), "utf-8"));
                                map.put("title", new String(jsonObject.getString("title").getBytes(), "utf-8"));
                                map.put("body", new String(jsonObject.getString("msg").getBytes(), "utf-8") != null ? new String(jsonObject.getString("msg").getBytes(), "utf-8") : "null");
                                map.put("loca", new String(jsonObject.getString("body").getBytes(), "utf-8"));
                                map.put("id", "" + jsonObject.getInt("id"));
                                map.put("build", new String(jsonObject.getString("build").getBytes(), "utf-8"));
                                map.put("latitude", new String(jsonObject.getString("latitude").getBytes(), "utf-8"));
                                map.put("longitude", new String(jsonObject.getString("longitude").getBytes(), "utf-8"));

                                Log.i("tag", jsonObject.getString("latitude"));
                                System.out.println("hell" + get_job_activity.size());
                                get_job_activity.add(map);
                            } else if (new String(jsonObject.getString("type").getBytes(), "utf-8").equals("MATCH")) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("name", new String(jsonObject.getString("name").getBytes(), "utf-8"));
                                map.put("type", new String(jsonObject.getString("type").getBytes(), "utf-8"));
                                map.put("begin_time", new String(jsonObject.getString("begin_time").getBytes(), "utf-8"));
                                map.put("end_time", new String(jsonObject.getString("end_time").getBytes(), "utf-8"));
                                map.put("phonenumber", new String(jsonObject.getString("phonenumber").getBytes(), "utf-8"));
                                map.put("title", new String(jsonObject.getString("title").getBytes(), "utf-8"));
                                map.put("body", new String(jsonObject.getString("msg").getBytes(), "utf-8") != null ? new String(jsonObject.getString("msg").getBytes(), "utf-8") : "null");
                                map.put("loca", new String(jsonObject.getString("body").getBytes(), "utf-8"));
                                map.put("id", "" + jsonObject.getInt("id"));
                                map.put("build", new String(jsonObject.getString("build").getBytes(), "utf-8"));
                                map.put("latitude", new String(jsonObject.getString("latitude").getBytes(), "utf-8"));
                                map.put("longitude", new String(jsonObject.getString("longitude").getBytes(), "utf-8"));

                                contest_activity.add(map);
                            } else if (new String(jsonObject.getString("type").getBytes(), "utf-8").equals("SCIENCE")) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("name", new String(jsonObject.getString("name").getBytes(), "utf-8"));
                                map.put("type", new String(jsonObject.getString("type").getBytes(), "utf-8"));
                                map.put("begin_time", new String(jsonObject.getString("begin_time").getBytes(), "utf-8"));
                                map.put("end_time", new String(jsonObject.getString("end_time").getBytes(), "utf-8"));
                                map.put("phonenumber", new String(jsonObject.getString("phonenumber").getBytes(), "utf-8"));
                                map.put("title", new String(jsonObject.getString("title").getBytes(), "utf-8"));
                                map.put("body", new String(jsonObject.getString("msg").getBytes(), "utf-8") != null ? new String(jsonObject.getString("msg").getBytes(), "utf-8") : "null");
                                map.put("loca", new String(jsonObject.getString("body").getBytes(), "utf-8"));
                                map.put("id", "" + jsonObject.getInt("id"));
                                map.put("build", new String(jsonObject.getString("build").getBytes(), "utf-8"));
                                map.put("latitude", new String(jsonObject.getString("latitude").getBytes(), "utf-8"));
                                map.put("longitude", new String(jsonObject.getString("longitude").getBytes(), "utf-8"));

                                meeting_activity.add(map);
                            }
//                            objects.add(jsonObject.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //  System.out.println("ddddddffddddddddddddddddddd"+get_job_activity.size());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTID && resultCode == RESULTID) {
        }
        if (requestCode == 5 && resultCode == 6) {
        }
    }

    @Override
    public void onClick(View view) {

    }
}
