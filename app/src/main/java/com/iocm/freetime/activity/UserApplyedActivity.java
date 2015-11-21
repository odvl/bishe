package com.iocm.freetime.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.iocm.administrator.yunxuan.R;
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
import java.util.Map;

/**用户申请的任务
 * Created by Administrator on 2015/2/4.
 */
public class UserApplyedActivity extends Activity {
    private ListView listView;
    private TextView loading;
    private ExpandableListView expandableListView;
    private UserjoinAdapter userjoinAdapter = null;
    private ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_join_layout);
        initViews();
        initDatas();
        getdatas();
    }

    private void getdatas() {
        String url = getResources().getString(R.string.getJoinurl);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        UserLoginApp userLoginApp = (UserLoginApp) getApplication();
        params.add("username", userLoginApp.getUserName());
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                super.onSuccess(statusCode, headers, response);
                if (response.length() == 0 || response == null) {

                    loading.setText("您没有参加过活动。");

                }
                if (statusCode == 200) {
                    for (int i = 0; i < response.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        try {
//                            list.add(response.get(i).toString());
                            JSONObject jsonObject = response.getJSONObject(i);
                            System.out.println("is " + response.get(i).toString());
                            map.put("activity", new String(jsonObject.getString("activity").getBytes(), "utf-8"));
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

        userjoinAdapter = new UserjoinAdapter(getApplicationContext(), list, getResources().getString(R.string.deleteJoinurl));
        listView.setAdapter(userjoinAdapter);
    }

    private void initViews() {
        loading = (TextView) findViewById(R.id.loading1);
        listView = (ListView) findViewById(R.id.ltv_userJoin_showActivity);
        expandableListView = (ExpandableListView) findViewById(R.id.eplv_userJoin_showLike);
    }


}
