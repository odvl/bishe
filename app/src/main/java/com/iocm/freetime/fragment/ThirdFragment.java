package com.iocm.freetime.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.FindTaskActivity;

import java.util.ArrayList;
import java.util.Map;

public class ThirdFragment extends Fragment{


    private ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();

    private Spinner type_spinner = null;
    private Spinner distance_spinner =null;
    private EditText key_word = null;
    private Button register = null;
    private View newsLayout = null;
    private String type,distance,phonenumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        newsLayout = inflater.inflate(R.layout.fragment_task_find, container, false);
        initViews();
        initDatas();
        initListeners();

        return newsLayout;
    }

    private void initListeners() {
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
                if(type.equals("科技")){
                    type = "SCIENCE";
                }else if (type.equals("比赛")) {
                    type = "MATCH";
                } else if (type.equals("娱乐")) {
                    type = "PLAY";
                }
               // Toast.makeText(getActivity(),"您选择了" + type,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        distance_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                distance = parent.getItemAtPosition(position).toString();
                int dis = Integer.parseInt(distance.substring(0,distance.lastIndexOf("米")));
            //    Toast.makeText(getActivity()," "+dis,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreeTimeApplication freeTimeApplication = (FreeTimeApplication) getActivity().getApplication();
                if(freeTimeApplication.getUserName() == null) {
                    Toast.makeText(getActivity(),"请前往我的世界登录",Toast.LENGTH_SHORT).show();
                }
                else {

                    phonenumber = key_word.getText().toString().trim();
                    Intent intent = new Intent();
                    intent.putExtra("type",type);
                    intent.putExtra("distance",distance);
                    intent.putExtra("phonenumber",phonenumber);
                    intent.setClass(getActivity(),FindTaskActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });*/
    }


    private void initDatas() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.activity_type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.activity_distance,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distance_spinner.setAdapter(adapter1);


    }


    private void initViews() {
        type_spinner = (Spinner) newsLayout.findViewById(R.id.news_layout_activity_Spinner);
        distance_spinner = (Spinner) newsLayout.findViewById(R.id.news_layout_activity_distance_type_Spinner);
        register = (Button) newsLayout.findViewById(R.id.news_layout_activity_register_Button);
        key_word = (EditText) newsLayout.findViewById(R.id.news_layout_activity_keyword_EditText);
    }
}
