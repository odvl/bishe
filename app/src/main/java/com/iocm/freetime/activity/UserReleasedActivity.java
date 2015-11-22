package com.iocm.freetime.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.adapter.UserjoinAdapter;
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

/**用户发布的任务
 * Created by Administrator on 2015/2/4.
 */
public class UserReleasedActivity extends BaseActivity {

    private ListView listView;
    private TextView loading;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    private UserjoinAdapter userjoinAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_issue_layout);
        getDatas();
        initViews();
        initDatas();
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

    private void getDatas() {
        UserLoginApp userLoginApp = (UserLoginApp) getApplication();
        String url = getResources().getString(R.string.getIssueurl);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", userLoginApp.getUserName());
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(UserReleasedActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if (response.length() == 0 || response == null) {
                    loading.setText("您未发布任何活动");
                }
                if (statusCode == 200) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("activity", new String(jsonObject.getString("activity").getBytes(), "utf-8"));
                            System.out.println("huoqu" + new String(jsonObject.getString("activity").getBytes(), "utf-8"));
                            map.put("id", new String(jsonObject.getString("id").getBytes(), "utf-8"));
                            list.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (list.size() == response.length() && response.length() != 0) {
                            loading.setVisibility(View.GONE);
                            userjoinAdapter.notifyDataSetChanged();

                        }

                    }
                }
            }
        });


    }

    private void initDatas() {
        userjoinAdapter = new UserjoinAdapter(this, list, getResources().getString(R.string.deleteIssueurl));
        listView.setAdapter(userjoinAdapter);

    }

    private void initViews() {
        loading = (TextView) findViewById(R.id.loading2);
        listView = (ListView) findViewById(R.id.ltv_userIssue_showAct);
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
