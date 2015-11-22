package com.iocm.freetime.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;

import java.util.List;
import java.util.Map;

public class SimAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;

    public SimAdapter(Context context,List<Map<String,String>> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;

    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return mDatas.size();
    }


    @Override
    public Object getItem(int arg0) {
        // TODO 自动生成的方法存根
        return mDatas.get(arg0).get("title")+": "+mDatas.get(arg0).get("body");
    }

    @Override
    public long getItemId(int arg0) {
        // TODO 自动生成的方法存根
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        View view = mInflater.inflate(R.layout.activity_find_task,arg2,false);
        ViewHoldr viewHoldr = null;
        if(arg1 == null){
            //通过itemget设置布局
            arg1 = mInflater.inflate(R.layout.task_msg_child_item, arg2,false);
            viewHoldr = new ViewHoldr();
            viewHoldr.title = (TextView) arg1.findViewById(R.id.msg_child_layout_activity_title);
            viewHoldr.body = (TextView) arg1.findViewById(R.id.msg_child_layout_activity_body);

            viewHoldr.progressBar = (ProgressBar) view.findViewById(R.id.pro);
            viewHoldr.search = (TextView) view.findViewById(R.id.text);
            arg1.setTag(viewHoldr);

        }
        else {
            viewHoldr = (ViewHoldr) arg1.getTag();

        }
        if(mDatas.size() != 0) {
            viewHoldr.progressBar.setVisibility(View.GONE);
            viewHoldr.search.setVisibility(View.GONE);
            viewHoldr.title.setText("名称："+mDatas.get(arg0).get("title"));
            viewHoldr.body.setText("内容："+mDatas.get(arg0).get("body"));

        }
        else {
            viewHoldr.progressBar.setVisibility(View.VISIBLE);
            viewHoldr.search.setVisibility(View.VISIBLE);
        }

        return arg1;
    }

    private final class ViewHoldr {
        TextView body;
        TextView title;
        ProgressBar progressBar;
        TextView search;

    }

}
