package com.iocm.freetime.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iocm.administrator.yunxuan.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;
import java.util.Map;

public class UserjoinAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Map<String,String>> mDatas;
    private Context context;
    private int id = -1;
    boolean tag = true;
    private String url;

    public UserjoinAdapter(Context context,List<Map<String,String>> mDatas,String url) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.url = url;
    }

    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return mDatas.size();
    }


    @Override
    public Object getItem(int arg0) {
        // TODO 自动生成的方法存根
        return mDatas.get(arg0).get("activity");
    }

    @Override
    public long getItemId(int arg0) {
        // TODO 自动生成的方法存根
        return arg0;
    }

    @Override
    public View getView(final int arg0, View arg1, ViewGroup arg2) {

        ViewHoldr viewHoldr = null;
        if(arg1 == null){
            //通过itemget设置布局
            arg1 = mInflater.inflate(R.layout.user_join_child, arg2,false);
            viewHoldr = new ViewHoldr();
            viewHoldr.title = (TextView) arg1.findViewById(R.id.txv_userJoinChild_activity);
            viewHoldr.cancel = (Button) arg1.findViewById(R.id.btn_userjoinchild_cancel);
            arg1.setTag(viewHoldr);

        }
        else {
            viewHoldr = (ViewHoldr) arg1.getTag();

        }
        if(mDatas.size() != 0) {
            viewHoldr.title.setText(mDatas.get(arg0).get("activity"));
            viewHoldr.cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              //      Toast.makeText(context,"点击了"+mDatas.get(arg0).get("id"),Toast.LENGTH_SHORT).show();
                    id = Integer.parseInt(mDatas.get(arg0).get("id"));
                   if( deleteAct(id)){
                       mDatas.remove(arg0);
                       notifyDataSetChanged();
                   }
                }
            });

        }
        else {
            viewHoldr.progressBar.setVisibility(View.VISIBLE);
            viewHoldr.search.setVisibility(View.VISIBLE);
        }

        return arg1;
    }

    private boolean deleteAct(int id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String url = this.url;
        params.put("id",id);
        client.post(url,params,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(context,"删除失败",Toast.LENGTH_SHORT).show();
                tag = false;
                throwable.printStackTrace();
            }
        });
return tag;
    }

    private final class ViewHoldr {
        TextView title;
        ProgressBar progressBar;
        TextView search;
        Button cancel;
    }

}
