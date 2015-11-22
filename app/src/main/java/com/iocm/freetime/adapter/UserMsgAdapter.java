package com.iocm.freetime.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.MsgType;
import com.iocm.freetime.bean.UserMsg;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserMsgAdapter extends BaseExpandableListAdapter {


    private int selectedGroupPosition = -1;
    private int selectedChildPosition = -1;
    private ArrayList<String> groupList = new ArrayList<String>();
    private ArrayList<List<Map<String,UserMsg>>> childList;
    private Context context;
    private LayoutInflater layoutInflater ;
    private String name;

    public UserMsgAdapter(ArrayList<String> groupList, ArrayList<List<Map<String,UserMsg>>> childList,Context context,String name) {
        this.name = name;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        UserMsg userMsg = childList.get(groupPosition).get(childPosition).get("msg");
        if(userMsg.getType() == MsgType.JOIN) {
            return  1;
        }
        else if(userMsg.getType() == MsgType.ISSUE){
            return  0 ;
        }
        else  {
            return 2;
        }
    }

    @Override
    public int getChildTypeCount() {
        return 3;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition).get("msg").getUserIssue()+":  "+childList.get(groupPosition).get(childPosition).get("msg").getAgree();
    }

    public void setSelectedPosition(int selectedGroupPosition, int selectedChildPosition) {
        this.selectedGroupPosition = selectedGroupPosition;
        this.selectedChildPosition = selectedChildPosition;
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final UserMsg userMsg = childList.get(groupPosition).get(childPosition).get("msg");

        if (getChildType(groupPosition,childPosition) == 1){

            TextView textView = null;
            if (convertView == null) {
                textView = new TextView(context);
                textView.setPadding(32, 10, 0, 10);
                convertView = textView;
            } else {
                textView = (TextView) convertView;
            }
            if(userMsg.getAgree().equals("1")){
                textView.setText(name+"用户:发布人（"+userMsg.getUserIssue()+")已经允许您的请求.活动【"+userMsg.getName()+"】");
            }else if (userMsg.getAgree().equals("-1")){
                textView.setText(name+"用户:发布人（"+userMsg.getUserIssue()+")已经拒绝您的请求.活动【"+userMsg.getName()+"】");
            }else if (userMsg.getAgree().equals("0")){
                textView.setText(name+"用户:发布人（"+userMsg.getUserIssue()+")等待处理.活动【"+userMsg.getName()+"】");
            }
            return textView;
        }
        else if (getChildType(groupPosition,childPosition) == 0){

            convertView = layoutInflater.inflate(R.layout.user_msg_child2,parent,false);
            TextView show_msg = (TextView) convertView.findViewById(R.id.txv_user_msg_child2_show);
            final Button wel = (Button) convertView.findViewById(R.id.btn_user_msg_child2_wel);
            Button refuse = (Button) convertView.findViewById(R.id.btn_user_msg_child2_refuse);
            wel.setText("欢迎");
            wel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(context,"dianjil",Toast.LENGTH_SHORT).show();
                    welMsg(userMsg.getId(), context.getResources().getString(R.string.updateurl), "1");
                  //  Toast.makeText(context,groupPosition+":"+childPosition,Toast.LENGTH_SHORT).show();
                    childList.get(groupPosition).remove(childPosition);
                     notifyDataSetChanged();
                }
            });
            refuse.setText("残忍拒绝");
            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    welMsg(userMsg.getId(),context.getResources().getString(R.string.updateurl),"-1");
                    childList.get( groupPosition).remove(childPosition);
                    notifyDataSetChanged();
               //     Toast.makeText(context,groupPosition+":"+childPosition,Toast.LENGTH_SHORT).show();

                }
            });
            show_msg.setText(name + "用户:" + userMsg.getUserJoin() + "想要参加您的活动.活动【" + userMsg.getName() + "】");
            return  convertView;
        }
        else//  if(getChildType(groupPosition,childPosition) == 2){
        {
            convertView = layoutInflater.inflate(R.layout.user_msg_child3,parent,false);
            TextView show_msg = (TextView) convertView.findViewById(R.id.txv_user_msg_child3_show);
            final Button ref = (Button) convertView.findViewById(R.id.btn_user_msg_child3_refuse);
            ref.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    welMsg(userMsg.getId(),context.getResources().getString(R.string.updateurl),"-1");
//                    ref.setText("yishanchu");
               //     childList.remove(groupPosition).remove(childPosition);
//                    notifyDataSetChanged();
                }
            });
            show_msg.setText(name+"用户"+userMsg.getUserJoin()+"已参加您的活动.活动【"+userMsg.getName()+"】");
            return convertView;
        }
//return convertView;
    }

    private void welMsg(int id,String url,String agree) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("id",String.valueOf(id));
        params.add("agree",agree);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context,"操作失败",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    public int getGroupCount() {
        return groupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LinearLayout cotain = new LinearLayout(context);
        cotain.setPadding(0, 10, 0, 10);
        cotain.setGravity(Gravity.CENTER_VERTICAL);

        ImageView imgIndicator = new ImageView(context);
        TextView textView = new TextView(context);
        textView.setText(getGroup(groupPosition).toString());
        textView.setPadding(150, 0, 0, 0);

        if (isExpanded) {
            imgIndicator.setBackgroundResource(R.drawable.normal);
        } else {
            imgIndicator.setBackgroundResource(R.drawable.normal);
        }
        cotain.addView(imgIndicator);
        cotain.addView(textView);
        return cotain;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
