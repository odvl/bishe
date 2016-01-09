package com.iocm.freetime.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.LocationInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/3/17.
 */
public class SetLocationActivity extends BaseActivity {

    private PoiSearch poiSearch = null;
    private PoiCitySearchOption option = null;
    private EditText key;
    private Button get;
    private TextView show;
    private List<PoiInfo> list;
    private Context context;
    private ListView listView;


    private ArrayList<HashMap<String, LocationInfo>> locationInfos = new ArrayList<HashMap<String, LocationInfo>>();


    //需要返回的信息
    private String addressStr;
    private String nameStr;
    private double mlatitude;
    private double mlongitude;


    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

            if (poiResult.getAllPoi() != null) {
                show.setText("点击以下你要定位的位置");
                list = poiResult.getAllPoi();
                for (int i = 0; i < list.size(); i++) {

                    HashMap<String, LocationInfo> map = new HashMap<String, LocationInfo>();
                    LocationInfo locationInfo = new LocationInfo();
                    locationInfo.setAddress(list.get(i).address);
                    locationInfo.setName(list.get(i).name);
                    map.put("locationinfo", locationInfo);
                    locationInfos.add(map);
                    System.out.println(list.get(i).name + ":" + list.get(i).address + ":" + list.get(i).location.latitude);

                }


                ShowLocationAdapter adapter = new ShowLocationAdapter(locationInfos, context);
                listView.setAdapter(adapter);
            } else {
                show.setText("尚无您输入的位置，请重新输入");
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_set_location);

        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        key = (EditText) findViewById(R.id.key_words);
        get = (Button) findViewById(R.id.begin_search);
        show = (TextView) findViewById(R.id.result_show);
        listView = (ListView) findViewById(R.id.list_show_location);

        key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                show.setText("");
                if (locationInfos != null) {
                    locationInfos.clear();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("定位到：");
                builder.setMessage(list.get(position).address + "附近。");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mlatitude = list.get(position).location.latitude;
                        System.out.println(mlatitude);
                        mlongitude = list.get(position).location.longitude;
                        nameStr = list.get(position).name;
                        addressStr = list.get(position).address;

                        Intent intent = new Intent();
                        intent.putExtra("mlatitude", mlatitude);
                        intent.putExtra("mlongitude", mlongitude);
                        intent.putExtra("name", nameStr);
                        intent.putExtra("address", addressStr);
                        setResult(2, intent);
                        finish();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });
        this.context = this;
        get.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                option = new PoiCitySearchOption()
                        .city("杭州")
                        .keyword(key.getText().toString());
                poiSearch.searchInCity(option);
            }
        });
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        poiSearch.destroy();
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



    /**
     * Created by Administrator on 2015/3/18.
     */
    class ShowLocationAdapter extends BaseAdapter {

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

}

