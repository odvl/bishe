package com.iocm.freetime.user_service;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.UserLoginApp;
import com.iocm.freetime.adapter.UserMsgAdapter;
import com.iocm.freetime.bean.MsgType;
import com.iocm.freetime.bean.UserMsg;
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
 * Created by Administrator on 2015/2/4.
 */
public class Msg extends Activity{

    private ExpandableListView expandableListView;
    private List<Map<String,UserMsg>> datas = new ArrayList<Map<String, UserMsg>>();
    private List<Map<String,UserMsg>> datas2 = new ArrayList<Map<String, UserMsg>>();
    private List<Map<String,UserMsg>> datas3 = new ArrayList<Map<String, UserMsg>>();
    private ArrayList<List<Map<String,UserMsg>>> all_datas = new ArrayList<List<Map<String,UserMsg>>>();
    private ArrayList<String> group = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_message_layout);


        initDatas();
        initViews();
        all_datas.add(datas);
        HashMap<String,UserMsg> msgHashMap = new HashMap<String,UserMsg>();
        UserMsg userMsg = new UserMsg();
        userMsg.setType(MsgType.ISSUE);
        userMsg.setUserIssue("ddd");
        userMsg.setUserJoin("dfdf");
        msgHashMap.put("msg",userMsg);
        datas2.add(msgHashMap);
        all_datas.add(datas2);
        all_datas.add(datas3);
        UserLoginApp userLoginApp = (UserLoginApp) getApplication();

        UserMsgAdapter userMsgAdapter  = new UserMsgAdapter(group,all_datas,this,userLoginApp.getUserName());
        expandableListView.setAdapter(userMsgAdapter);
    }

    private void initViews() {
        expandableListView = (ExpandableListView) findViewById(R.id.eplv_userMsg_show);

    }

    private void initDatas() {

        group.add("已参与");
        group.add("已发布");
        group.add("已同意");

        String url = getResources().getString(R.string.getMsgurl);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        UserLoginApp userLoginApp = (UserLoginApp) getApplication();
        params.add("name",userLoginApp.getUserName());
        client.get(url,params,new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(Msg.this,"获取信息失败",Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(statusCode == 200) {
                    for(int i = 0;i < response.length();i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            UserMsg userMsg = new UserMsg();
                            HashMap<String,UserMsg> map = new HashMap<String, UserMsg>();
                            userMsg.setAgree(new String(jsonObject.getString("agree").getBytes(),"utf-8"));
                            userMsg.setType(MsgType.JOIN);
                            userMsg.setName(new String(jsonObject.getString("name").getBytes(),"utf-8"));
                            userMsg.setUserIssue(new String(jsonObject.getString("userIssue").getBytes(),"utf-8"));
                            map.put("msg",userMsg);
//                            map.put("agree", new String(jsonObject.getString("agree").getBytes(), "utf-8"));
                            System.out.println(new String(jsonObject.getString("userIssue")));
//                            map.put("userIssue",new String(jsonObject.getString("userIssue").getBytes(),"utf-8"));
                            datas.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        String url2 = getResources().getString(R.string.getIssMsgurl);
        RequestParams params2 = new RequestParams();
        params2.add("name",userLoginApp.getUserName());
        client.get(url2,params2,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                if(statusCode == 200) {
                    for (int i = 0 ;i < response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            if(jsonObject.getString("type").equals("ISSUE")) {
                                UserMsg userMsg = new UserMsg();
                                HashMap<String,UserMsg> map = new HashMap<String, UserMsg>();
                                userMsg.setId(jsonObject.getInt("id"));
                                userMsg.setType(MsgType.ISSUE);
                                userMsg.setUserJoin(new String(jsonObject.getString("register_user").getBytes(), "utf-8"));
                                userMsg.setName(new String(jsonObject.getString("name").getBytes(),"utf-8"));
                                System.out.println("发布："+new String(jsonObject.getString("register_user")));
                                map.put("msg",userMsg);
                                datas2.add(map);
                            }
                           else if(jsonObject.getString("type").equals("AGREE")) {
                                UserMsg userMsg = new UserMsg();
                                HashMap<String,UserMsg> map = new HashMap<String, UserMsg>();
                                userMsg.setId(jsonObject.getInt("id"));
                                userMsg.setType(MsgType.AGREE);
                                userMsg.setUserJoin(new String(jsonObject.getString("register_user").getBytes(), "utf-8"));
                                userMsg.setName(new String(jsonObject.getString("name").getBytes(),"utf-8"));
                               System.out.println("同意："+new String(jsonObject.getString("register_user")));
                                map.put("msg",userMsg);
                                datas3.add(map);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(Msg.this,"获取已发布失败",Toast.LENGTH_SHORT).show();
                throwable.printStackTrace();
            }
        });
    }
}
