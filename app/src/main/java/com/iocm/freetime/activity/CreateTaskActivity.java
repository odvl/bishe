package com.iocm.freetime.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;

import java.util.Calendar;

/**
 * Created by liubo on 15/11/21.
 */
public class CreateTaskActivity extends BaseActivity {

    //初始化控件

    private EditText taskNameEditText;
    private EditText mobileEditText;
    private EditText taskDetailEditText;

    private Button setBeginTimeButton;
    private Button setEndTimeButton;
    private Button createTaskButton;

    private ImageView setLocationImageView;

    private TextView location;

    //活动属性

    private String titleMsg = null;
    private String beginTimeMsg = null;
    private String endTimeMsg = null;
    private String phonenumberMsg = null;
    private String Msgmsg = null;


    private String locationName = null;
    private String locationAddress = null;
    private Double locationLatitude = -1.0;
    private Double locationLongitude = -1.0;


    private Calendar calendar = Calendar.getInstance();
    View contactsLayout = null;
    private int beginYear = calendar.get(Calendar.YEAR);
    private int beginMonth = calendar.get(Calendar.MONTH);
    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
    private int endYear = calendar.get(Calendar.YEAR);
    private int endMonth = calendar.get(Calendar.MONTH);
    private int endDay = calendar.get(Calendar.DAY_OF_MONTH);
    private boolean beginFlag = true;


    @Override
    void initView() {
        setContentView(R.layout.fragment_create_task);

        taskNameEditText = (EditText) findViewById(R.id.task_name);
        mobileEditText = (EditText) findViewById(R.id.mobile);
        taskDetailEditText = (EditText) findViewById(R.id.task_detail);

        setBeginTimeButton = (Button) findViewById(R.id.set_start_time_btn);
        setEndTimeButton = (Button) findViewById(R.id.set_end_time_btn);
        createTaskButton = (Button) findViewById(R.id.release_task);

        setLocationImageView = (ImageView) findViewById(R.id.set_location);
        location = (TextView) findViewById(R.id.location);


    }

    @Override
    void initListener() {

        //  跳转到设置位置界面
        setLocationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetLocationActivity.class);
                startActivityForResult(intent, 2);
            }
        });


        //显示活动开始时间的弹出框
        setBeginTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                                setBeginTimeButton.setText("始:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            }
                        } else {
                            setBeginTimeButton.setText("始:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            beginTimeMsg = "" + year + "年" + (1 + monthOfYear) + "月" + dayOfMonth + "日";
                        }

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //显示活动结束时间的弹出框
        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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
                            setEndTimeButton.setText("末:" + year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
                            beginFlag = false;
                            endTimeMsg = "" + year + "年" + (1 + monthOfYear) + "月" + dayOfMonth + "日";
                        }


                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titleMsg = taskNameEditText.getText().toString();
                Msgmsg = taskDetailEditText.getText().toString();
                phonenumberMsg = mobileEditText.getText().toString();


                if (titleMsg.equals("") || titleMsg == null) {
                    Toast.makeText(getActivity(), "活动名称不能为空", Toast.LENGTH_SHORT).show();
                    getFocus(taskNameEditText);
                    return;
                }


                if (beginTimeMsg == null) {
                    Toast.makeText(getActivity(), "开始时间没有设置吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (endTimeMsg == null) {
                    Toast.makeText(getActivity(), "结束时间忘了设置吧", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phonenumberMsg == null || phonenumberMsg.equals("")) {
                    Toast.makeText(getActivity(), "请输入联系方式", Toast.LENGTH_SHORT).show();
                    getFocus(mobileEditText);
                    return;
                }

                if (Msgmsg == null || Msgmsg.equals("")) {
                    Toast.makeText(getActivity(), "您的活动内容貌似为空吧", Toast.LENGTH_SHORT).show();
                    getFocus(taskDetailEditText);
                    return;
                }

                if (locationLatitude == -1.0 || locationLongitude == -1.0) {
                    return;
                }
                IssueActivity();
            }
        });

    }

    //    发布活动
    private void IssueActivity() {

        AVObject task = new AVObject(Constant.LeancloundTable.TaskTable.tableName);

        AVGeoPoint point = new AVGeoPoint();
        point.setLatitude(locationLatitude);
        point.setLongitude(locationLongitude);

        task.put(Constant.LeancloundTable.TaskTable.taskTitle, taskNameEditText.getText().toString());
        task.put(Constant.LeancloundTable.TaskTable.userId, cache.getStringValue(Constant.User.userId));
        task.put(Constant.LeancloundTable.TaskTable.taskDetail, taskDetailEditText.getText().toString());
        task.put(Constant.LeancloundTable.TaskTable.taskMobile, mobileEditText.getText().toString());
        task.put(Constant.LeancloundTable.TaskTable.taskBeginTime, beginTimeMsg);
        task.put(Constant.LeancloundTable.TaskTable.taskEndTime, endTimeMsg);
        task.put(Constant.LeancloundTable.TaskTable.point, point);
        task.put(Constant.LeancloundTable.TaskTable.username, cache.getStringValue(Constant.User.username));
        task.put(Constant.LeancloundTable.TaskTable.joinedNum, 0);
        task.put("upvotes", 0);
        task.put(Constant.LeancloundTable.TaskTable.build, locationAddress + "附近");


        task.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    CustomUtils.showToast(mContext, "发布成功");
                    finish();
                } else {
                    e.printStackTrace();
                    CustomUtils.showToast(mContext, "发布失败");
                }
            }
        });

    }

    @Override
    void loadData() {

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            locationName = data.getStringExtra("name");
            locationAddress = data.getStringExtra("address");
            location.setText(locationName + "附近");
            locationLatitude = data.getDoubleExtra("mlatitude", 0);
            locationLongitude = data.getDoubleExtra("mlongitude", 0);
        }
    }
}
