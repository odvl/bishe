package com.iocm.freetime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.LocationInfo;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2015/3/18.
 */
public class ShowLocationAdapter extends BaseAdapter {

    private ArrayList<HashMap<String,LocationInfo>> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public ShowLocationAdapter(ArrayList<HashMap<String, LocationInfo>> list, Context context) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =null;
//        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.task_msg_child_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.msg_child_layout_activity_title);
            viewHolder.locationName = (TextView) convertView.findViewById(R.id.msg_child_layout_activity_body);
            parent.setTag(viewHolder);
//        }else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }

        LocationInfo locationInfo = list.get(position).get("locationinfo");

        if(viewHolder!=null) {
            viewHolder.name.setText("名称 " +locationInfo.getName());
            viewHolder.locationName.setText(locationInfo.getAddress());
        }
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView locationName;
    }
}
