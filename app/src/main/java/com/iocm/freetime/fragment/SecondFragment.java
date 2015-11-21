package com.iocm.freetime.fragment;

import android.app.Fragment;

public class SecondFragment extends Fragment{

}
//
//
//    //获取当前日期
//    private Calendar calendar = Calendar.getInstance();
//
//
//    //初始化控件
//    private EditText title ,body,phonenumber,Msg;
//    private Button show_begin_time_button = null;
//    private Button show_end_time_button = null;
//    private Spinner type_spinner = null;
//    private Button regAct = null;
//    private Button setLocation;
//
//
//    //活动属性
//    private Tasks.Type typeMsg = Tasks.Type.SCIENCE;
//    private String titleMsg = null;
//    private String bodyMsg = null;
//    private String  beginTimeMsg = null;
//    private String endTimeMsg = null;
//    private String phonenumberMsg = null;
//    private String Msgmsg =null;
//
//
//    private String locationName = null;
//    private String locationAddress = null;
//    private Double locationLatitude ;
//    private Double locationLongitude;
//
//
//    View contactsLayout = null;
//    private int beginYear = calendar.get(Calendar.YEAR);
//    private int beginMonth = calendar .get(Calendar.MONTH);
//    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
//    private int endYear = calendar.get(Calendar.YEAR);
//    private int endMonth = calendar.get(Calendar.MONTH);
//    private int endDay = calendar.get(Calendar.DAY_OF_MONTH);
//    private boolean beginFlag = true;
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        contactsLayout = inflater.inflate(R.layout.fragment_create_task, container, false);
//        initViews();
//        initListeners();
//        initDatas();
//        return contactsLayout;
//    }
//    private void initDatas() {
//        //活动类型下拉列表
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity()
//                ,R.array.activity_type,
//                android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        type_spinner.setPrompt("选择活动的类型");
//        type_spinner.setAdapter(adapter);
//    }
//    private void initListeners() {
//
//        //跳转到设置位置界面
//        setLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             Intent intent = new Intent(getActivity(),SetLocationActivity.class);
//                startActivityForResult(intent, 2);
//            }
//        });
//
//        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String type = parent.getItemAtPosition(position).toString();
//                switch (position){
//                    case 0:
//                        typeMsg = Tasks.Type.SCIENCE;break;
//                    case 1:
//                        typeMsg = Tasks.Type.MATCH;break;
//                    case 2:
//                        typeMsg = Tasks.Type.PLAY;break;
//
//                }
//               // Toast.makeText(getActivity(),"您选的活动类型为"+type,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//
//
//        //显示活动开始时间的弹出框
//        show_begin_time_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    new DatePickerDialog(getActivity(),new OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                            beginYear = year;
//                            beginMonth = monthOfYear + 1;
//                            beginDay = dayOfMonth;
//                            if(!beginFlag) {
//                                if(beginYear>endYear){
//                                    Toast.makeText(getActivity(),"year wrong!",Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else if(beginYear == endYear && beginMonth>endMonth){
//                                    Toast.makeText(getActivity(),"month wrong",Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else if(beginYear == endYear && beginMonth == endMonth && beginDay>endDay) {
//                                    Toast.makeText(getActivity(),"day wrong",Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                else {
//                                    show_begin_time_button.setText("始:"+year+"-"+(1+monthOfYear)+"-"+dayOfMonth);
//                                }
//                            }
//                            else {
//                                show_begin_time_button.setText("始:"+year+"-"+(1+monthOfYear)+"-"+dayOfMonth);
//                                beginTimeMsg =  ""+year+"年"+(1+monthOfYear)+"月"+dayOfMonth+"日";
//                            }
//
//                        }
//                    },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        //显示活动结束时间的弹出框
//        show_end_time_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//                new DatePickerDialog(getActivity(),new OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        endYear = year;
//                        endMonth = monthOfYear + 1;
//                        endDay = dayOfMonth;
//                        if(endYear<beginYear){
//                            Toast.makeText(getActivity(),"year错误,请检查",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        else if(endYear == beginYear && endMonth<beginMonth) {
//                            Toast.makeText(getActivity(),"month错误,请检查",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        else if(endYear == beginYear && endMonth == beginMonth && endDay<beginDay){
//                            Toast.makeText(getActivity(),"day错误,请检查",Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                        else {
//                            show_end_time_button.setText("末:"+year+"-"+(1+monthOfYear)+"-"+dayOfMonth);
//                            beginFlag = false;
//                            endTimeMsg = ""+year+"年"+(1+monthOfYear)+"月"+dayOfMonth+"日";
//                        }
//
//
//                    }
//                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        regAct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean regTag = true;
//
////                 titleMsg = title.getText().toString();
////                 bodyMsg = body.getText().toString();
////                 phonenumberMsg = phonenumber.getText().toString();
////                Msgmsg = Msg.getText().toString();
////
////                if(titleMsg.equals("")||titleMsg == null) {
////                    Toast.makeText(getActivity(),"活动名称不能为空",Toast.LENGTH_SHORT).show();
////                    getFocus(title);
////                    regTag = false;
////                    return;
////                }
////
////
////                if(beginTimeMsg == null) {
////                    Toast.makeText(getActivity(), "开始时间没有设置吧", Toast.LENGTH_SHORT).show();
////                    return;
////                }
////                if(endTimeMsg == null ) {
////                    Toast.makeText(getActivity(),"结束时间忘了设置吧", Toast.LENGTH_SHORT).show();
////                    return;
////                }
////
////                if(phonenumberMsg == null||phonenumberMsg.equals("")){
////                    Toast.makeText(getActivity(),"请输入联系方式",Toast.LENGTH_SHORT).show();
////                    getFocus(phonenumber);
////                    regTag = false;
////                    return;
////                }
////
////                if(Msgmsg == null || Msgmsg.equals("")) {
////                    Toast.makeText(getActivity(),"您的活动内容貌似为空吧",Toast.LENGTH_SHORT).show();;
////                    getFocus(Msg);
////                    regTag = false;
////                    return;
////                }
////                if(regTag) {
////                    //
////                    Log.i("tag","dddd");
////
////                }
//                IssueActivity();
//                Toast.makeText(getActivity(),"发布成功",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//    //发布活动
//    private void IssueActivity() {
//        UserLoginApp userloginApp = (UserLoginApp) getActivity().getApplication();
//
//        if(userloginApp.getUserName() == null || userloginApp.getUserName().equals("")){
//            Toast.makeText(getActivity(),"用户尚未登录",Toast.LENGTH_SHORT).show();
//            return;
//        }else {
//
//            Tasks tasks = new Tasks();
//            tasks.setName(userloginApp.getUserName());
//            tasks.setType(typeMsg);
//            tasks.setTitle(titleMsg);
//            tasks.setBody(locationAddress);
//            tasks.setBeginTime(beginTimeMsg);
//            tasks.setEndTime(endTimeMsg);
//            tasks.setPhoneNumber(phonenumberMsg);
//            tasks.setMsg(Msgmsg);
//            tasks.setBuild(locationName);
//            tasks.setLatitude(locationLatitude);
//            tasks.setLongitude(locationLongitude);
//
//
//        String a = tasks.toString();
//        String[] s  = a.split("@");
//        for(int i = 0;i<s.length;i++) {
//            System.out.println("第"+i+"个元素为："+s[i]);
//        }
//
//        String url = getResources().getString(R.string.issurl);
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.add("activity", tasks.toString());
//        client.post(url,params,new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int i, Header[] headers, byte[] bytes) {
//             //   Toast.makeText(getActivity(), "发布成功", Toast.LENGTH_SHORT).show();
//                new  AlertDialog.Builder(getActivity())
//
//                        .setTitle("活动发布" )
//
//                        .setMessage("发布成功" )
//
//                        .setPositiveButton("确定" ,  null )
//
//                        .show();
//                title.setText(null);
//             //   body.setText(null);
//                Msg.setText(null);
//                show_begin_time_button.setText("点我设置开始时间");
//                show_end_time_button.setText("点我设置结束时间");
//                phonenumber.setText(null);
//            }
//
//            @Override
//            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//
//            }
//        });
//
//
//        //    Toast.makeText(getActivity(), activities.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//    }
//    private void initViews() {
//
//        setLocation = (Button) contactsLayout.findViewById(R.id.btn_show_map);
//        regAct = (Button) contactsLayout.findViewById(R.id.button5);
//        type_spinner = (Spinner) contactsLayout.findViewById(R.id.button);
//        show_begin_time_button = (Button)contactsLayout.findViewById(R.id.button2);
//        show_end_time_button = (Button) contactsLayout.findViewById(R.id.button3);
//        title = (EditText) contactsLayout.findViewById(R.id.editText);
//
//        phonenumber = (EditText) contactsLayout.findViewById(R.id.editText3);
//        Msg = (EditText) contactsLayout.findViewById(R.id.edit_activity_Msg);
//    }
//    //获取焦点
//    private void getFocus(View v) {
//        v.setFocusable(true);
//        v.setFocusableInTouchMode(true);
//        v.requestFocus();
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 2 && requestCode == 2) {
//
//            locationName = data.getStringExtra("name");
//            locationAddress = data.getStringExtra("address");
//            locationLatitude = data.getDoubleExtra("mlatitude",0);
//            locationLongitude = data.getDoubleExtra("mlongitude",0);
////            Toast.makeText(getActivity(),data.getStringExtra("name")+":"+data.getStringExtra("address")
////                    +":"+data.getDoubleExtra("mlatitude",0)+":"+data.getDoubleExtra("mlongitude",0),Toast.LENGTH_SHORT).show();
//        }
//    }
//}
//
