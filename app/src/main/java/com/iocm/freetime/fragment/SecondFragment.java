package com.iocm.freetime.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.MyApplication;
import com.iocm.freetime.activity.SetLocationActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.entity.mime.Header;

import java.util.Calendar;

import static android.app.DatePickerDialog.OnDateSetListener;

public class SecondFragment extends Fragment {


    //获取当前日期
    private Calendar calendar = Calendar.getInstance();


    //初始化控件
    private EditText title, body, phonenumber, Msg;
    private Button show_begin_time_button = null;
    private Button show_end_time_button = null;
    private Spinner type_spinner = null;
    private Button regAct = null;
    private Button setLocation;


    //活动属性
    private Activities.Type typeMsg = Activities.Type.SCIENCE;
    private String titleMsg = null;
    private String bodyMsg = null;
    private String beginTimeMsg = null;
    private String endTimeMsg = null;
    private String phonenumberMsg = null;
    private String Msgmsg = null;


    private String locationName = null;
    private String locationAddress = null;
    private Double locationLatitude;
    private Double locationLongitude;


    View contactsLayout = null;
    private int beginYear = calendar.get(Calendar.YEAR);
    private int beginMonth = calendar.get(Calendar.MONTH);
    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
    private int endYear = calendar.get(Calendar.YEAR);
    private int endMonth = calendar.get(Calendar.MONTH);
    private int endDay = calendar.get(Calendar.DAY_OF_MONTH);
    private boolean beginFlag = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contactsLayout = inflater.inflate(R.layout.fragment_create_task, container, false);
        initViews();
        initListeners();
        initDatas();
        return contactsLayout;
    }

    private void initDatas() {
        //活动类型下拉列表
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity()
                , R.array.activity_type,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setPrompt("选择活动的类型");
        type_spinner.setAdapter(adapter);
    }

    private void initListeners() {

        //跳转到设置位置界面
        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetLocationActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = parent.getItemAtPosition(position).toString();
                switch (position) {
                    case 0:
                        typeMsg = Activities.Type.SCIENCE;
                        break;
                    case 1:
                        typeMsg = Activities.Type.MATCH;
                        break;
                    case 2:
                        typeMsg = Activities.Type.PLAY;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //显示活动开始时间的弹出框
        show_begin_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        beginYear = year;
                        beginMonth = monthOfYear + 1;
                        beginDay = dayOfMonth;
                        if (!beginFlag) {
                            if (beginYear > endYear) {
                                Toast.makeText(getActivity(), "year wrong!", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (beginYear == endYear && beginMonth > endMonth) {
                                Toast.makeText(getActivity(), "month wrong", Toast.LENGTH_SHORT).show();
                                return;
                            } else if (beginYear == endYear && beginMonth == endMonth && beginDay > endDay) {
                                Toast.makeText(getActivity(), "day wrong", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                show_begin_time_button.setText("始:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            }
                        } else {
                            show_begin_time_button.setText("始:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            beginTimeMsg = "" + year + "年" + (1 + monthOfYear) + "月" + dayOfMonth + "日";
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //显示活动结束时间的弹出框
        show_end_time_button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endYear = year;
                        endMonth = monthOfYear + 1;
                        endDay = dayOfMonth;
                        if (endYear < beginYear) {
                            Toast.makeText(getActivity(), "year错误,请检查", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (endYear == beginYear && endMonth < beginMonth) {
                            Toast.makeText(getActivity(), "month错误,请检查", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (endYear == beginYear && endMonth == beginMonth && endDay < beginDay) {
                            Toast.makeText(getActivity(), "day错误,请检查", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            show_end_time_button.setText("末:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            beginFlag = false;
                            endTimeMsg = "" + year + "年" + (1 + monthOfYear) + "月" + dayOfMonth + "日";
                        }


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        regAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean regTag = true;

                 titleMsg = title.getText().toString();
                 bodyMsg = body.getText().toString();
                 phonenumberMsg = phonenumber.getText().toString();
                Msgmsg = Msg.getText().toString();

                if(titleMsg.equals("")||titleMsg == null) {
                    Toast.makeText(getActivity(),"活动名称不能为空",Toast.LENGTH_SHORT).show();
                    getFocus(title);
                    regTag = false;
                    return;
                }


                if(beginTimeMsg == null) {
                    Toast.makeText(getActivity(), "开始时间没有设置吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(endTimeMsg == null ) {
                    Toast.makeText(getActivity(),"结束时间忘了设置吧", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(phonenumberMsg == null||phonenumberMsg.equals("")){
                    Toast.makeText(getActivity(),"请输入联系方式",Toast.LENGTH_SHORT).show();
                    getFocus(phonenumber);
                    regTag = false;
                    return;
                }

                if(Msgmsg == null || Msgmsg.equals("")) {
                    Toast.makeText(getActivity(),"您的活动内容貌似为空吧",Toast.LENGTH_SHORT).show();;
                    getFocus(Msg);
                    regTag = false;
                    return;
                }
                if(regTag) {
                    //


                }
                IssueActivity();
                Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //发布活动
    private void IssueActivity() {
        MyApplication userloginApp = (MyApplication) getActivity().getApplication();

        if (userloginApp.getUserName() == null || userloginApp.getUserName().equals("")) {
            Toast.makeText(getActivity(), "用户尚未登录", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
            Activities activities = new Activities();
            activities.setName(userloginApp.getUserName());
            activities.setType(typeMsg);
            activities.setTitle(titleMsg);
            activities.setBody(locationAddress);
            activities.setBeginTime(beginTimeMsg);
            activities.setEndTime(endTimeMsg);
            activities.setPhoneNumber(phonenumberMsg);
            activities.setMsg(Msgmsg);
            activities.setBuild(locationName);
            activities.setLatitude(locationLatitude);
            activities.setLongitude(locationLongitude);


            String a = activities.toString();
            String[] s = a.split("@");

            String url = getResources().getString(R.string.issurl);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add("activity", activities.toString());
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {

                }

                @Override
                public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {

                }


                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("活动发布")
                            .setMessage("发布成功")
                            .setPositiveButton("确定", null)
                            .show();
                    title.setText(null);
                    //   body.setText(null);
                    Msg.setText(null);
                    show_begin_time_button.setText("点我设置开始时间");
                    show_end_time_button.setText("点我设置结束时间");
                    phonenumber.setText(null);
                }


                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        }

    }

    private void initViews() {

        setLocation = (Button) contactsLayout.findViewById(R.id.shortcut);
        regAct = (Button) contactsLayout.findViewById(R.id.commit);

        show_begin_time_button = (Button) contactsLayout.findViewById(R.id.shortcut);
        show_end_time_button = (Button) contactsLayout.findViewById(R.id.commit);
        title = (EditText) contactsLayout.findViewById(R.id.input);

        phonenumber = (EditText) contactsLayout.findViewById(R.id.input);
        Msg = (EditText) contactsLayout.findViewById(R.id.input);
    }

    //获取焦点
    private void getFocus(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && requestCode == 2) {
            if (data != null) {
                locationName = data.getStringExtra("name");
                locationAddress = data.getStringExtra("address");
                locationLatitude = data.getDoubleExtra("mlatitude", 0);
                locationLongitude = data.getDoubleExtra("mlongitude", 0);
            }
        }
    }

    static class Activities {
        private String title;
        private String body;
        private String beginTime;
        private String endTime;
        private String phoneNumber;
        private String name;
        private Type type;
        private String msg;
        private Double latitude;
        private Double longitude;
        private String build;

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
//        return "Activities{" +
//                "title='" + title + '\'' +
//                ", body='" + body + '\'' +
//                ", beginTime='" + beginTime + '\'' +
//                ", endTime='" + endTime + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", name='" + name + '\'' +
//                ", type=" + type +
//                '}';
            return name+"@"+title+"@"+body+"@"+beginTime+"@"+endTime+"@"+phoneNumber+"@"+type+"@"+msg+"@"+build+"@"+latitude+"@"+longitude;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

         private  enum Type{
            MATCH,PLAY,SCIENCE
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

}

