package com.iocm.freetime.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iocm.administrator.yunxuan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/1/24.
 */
public class ExpandAdapter extends BaseExpandableListAdapter {

    private int selectedGroupPosition = -1;
    private int selectedChildPosition = -1;
    private ArrayList<String> groupList;
    private ArrayList<List<Map<String,String>>> childList;
    private  Context context;

    public ExpandAdapter(ArrayList<String> groupList, ArrayList<List<Map<String,String>>> childList,Context context) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition).get("title")+":  "+childList.get(groupPosition).get(childPosition).get("body");
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
        TextView textView = null;
        if (convertView == null) {
            textView = new TextView(context);
            textView.setPadding(32, 10, 0, 10);
            convertView = textView;
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(getChild(groupPosition, childPosition).toString());
        return textView;
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
            switch (groupPosition){
                case 0:
                    imgIndicator.setBackgroundResource(R.drawable.match);
                    break;
                case 1:
                    imgIndicator.setBackgroundResource(R.drawable.science);
                    break;
                case 2:
                    imgIndicator.setBackgroundResource(R.drawable.play);
            }

        } else {
            switch (groupPosition){
                case 0:
                    imgIndicator.setBackgroundResource(R.drawable.match);
                    break;
                case 1:
                    imgIndicator.setBackgroundResource(R.drawable.science);
                    break;
                case 2:
                    imgIndicator.setBackgroundResource(R.drawable.play);
            }
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
