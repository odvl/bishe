package com.iocm.freetime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.adapter.SimAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/22.
 */
public class FindTaskActivity extends BaseActivity {

    //活动参数静态变量
    private static final String NAME = "name", TYPE = "type", BEGIN_TIME = "begin_time",
            END_TIME = "end_time", TITLE = "title", BODY = "body", LOCA = "loca", PHONENUMBER = "phonenumber";

    private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
    ArrayList<Map<String, String>> get_job_activity = new ArrayList<Map<String, String>>();
    ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();

    private Button time, distance, area, hot;
    private ListView listView;
    private SimAdapter simAdapter = null;
    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_task);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        List<Map<String, String>> ll = find(bundle.getString("type"), bundle.getString("distance"), bundle.getString("phonenumber"));
        initViews();
        initDatas();
        initListeners();
    }

    @Override
    void initView() {

    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {

    }

    private void initListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "点击了" + position, Toast.LENGTH_SHORT);
                Intent intent = new Intent();
                intent.putExtra(NAME, get_job_activity.get(position).get(NAME));
                intent.putExtra(TITLE, get_job_activity.get(position).get(TITLE));
                intent.putExtra(BODY, get_job_activity.get(position).get(BODY));
                intent.putExtra(PHONENUMBER, get_job_activity.get(position).get(PHONENUMBER));
                intent.putExtra(LOCA, get_job_activity.get(position).get(LOCA));
                intent.putExtra(BEGIN_TIME, get_job_activity.get(position).get(BEGIN_TIME));
                intent.putExtra(END_TIME, get_job_activity.get(position).get(END_TIME));
                intent.putExtra("id", get_job_activity.get(position).get("id"));

                intent.setClass(FindTaskActivity.this, TaskDetailActivity.class);
                FindTaskActivity.this.startActivity(intent);

            }
        });
    }

    private void initDatas() {
        for (int i = 0; i < 5; i++) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("title", "title" + i);
            map.put("body", "body" + i);
            arrayList.add(map);
        }

        simAdapter = new SimAdapter(getApplicationContext(), get_job_activity);

        listView.setAdapter(simAdapter);

    }

    private void initViews() {
        time = (Button) findViewById(R.id.find_activity_defBytime_Button);
        distance = (Button) findViewById(R.id.findActivityDefByDistanceButton);
        area = (Button) findViewById(R.id.findActivityDefByAreaButton);
        hot = (Button) findViewById(R.id.findActivityDefByHotButton);
        listView = (ListView) findViewById(R.id.findActivityListActivityListView);
        progressBar = (ProgressBar) findViewById(R.id.pro);
        textView = (TextView) findViewById(R.id.text);
    }

    private List<Map<String, String>> find(final String type, String distance, final String phonenumber) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = getResources().getString(R.string.searchurl);
        params.add("type", type);
        params.add("distance", distance);
        params.add("phonenumber", phonenumber);

        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (statusCode == 200) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Map<String, String> map = new HashMap<String, String>();
                            //存入活动的相关信息
                            map.put("name", new String(jsonObject.getString("name").getBytes(), "utf-8"));
                            map.put("type", new String(jsonObject.getString("type").getBytes(), "utf-8"));
                            map.put("begin_time", new String(jsonObject.getString("begin_time").getBytes(), "utf-8"));
                            map.put("end_time", new String(jsonObject.getString("end_time").getBytes(), "utf-8"));
                            map.put("phonenumber", new String(jsonObject.getString("phonenumber").getBytes(), "utf-8"));
                            map.put("title", new String(jsonObject.getString("title").getBytes(), "utf-8"));
                            map.put("body", new String(jsonObject.getString("body").getBytes(), "utf-8") != null ? new String(jsonObject.getString("body").getBytes(), "utf-8") : "null");
                            map.put("loca", new String(jsonObject.getString("loca").getBytes(), "utf-8"));
                            map.put("id", "" + jsonObject.getInt("id"));


                            get_job_activity.add(map);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (get_job_activity.size() == response.length()) {
                            simAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
        return get_job_activity;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
