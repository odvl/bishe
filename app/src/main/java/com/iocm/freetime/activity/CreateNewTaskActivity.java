package com.iocm.freetime.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.iocm.administrator.yunxuan.R;
import com.iocm.freetime.base.TaskActivity;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.wedgets.CommonToolBar;

/**
 * Created by liubo on 15/7/29.
 */
public class CreateNewTaskActivity extends BaseActivity{

    private EditText mTaskName;
    private EditText mMobile;
    private EditText mTaskDetail;
    private Button mSetStartBtn;
    private Button mSetEntBtn;
    private Button mReleaseBtn;
    private ImageView mSetLocation;
    private CommonToolBar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create_task);
        init();
        setupViews();
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

    private void setupViews() {
        mTaskName = (EditText) findViewById(R.id.task_name);
        mMobile = (EditText) findViewById(R.id.mobile);
        mTaskDetail = (EditText) findViewById(R.id.task_detail);
        mSetEntBtn = (Button) findViewById(R.id.set_end_time_btn);
        mSetStartBtn = (Button) findViewById(R.id.set_start_time_btn);
        mSetLocation = (ImageView) findViewById(R.id.set_location);
        mReleaseBtn = (Button) findViewById(R.id.release_task);
        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);

        mSetStartBtn.setOnClickListener(this);
        mSetEntBtn.setOnClickListener(this);
        mReleaseBtn.setOnClickListener(this);
        mSetLocation.setOnClickListener(this);
        mToolbar.setOnCommonToolBarClickListener(this);

    }

    private void init() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_start_time_btn: {
                setStartTime();
                break;
            }
            case R.id.set_end_time_btn: {
                break;
            }
            case R.id.set_location: {
                jumpActivityForResult(SetLocationActivity.class, Constant.RequestCode.SetLocationCode);
                break;
            }
            case R.id.release_task: {
                break;
            }
        }

    }

    private void setStartTime() {
        //显示活动开始时间的弹出框

       new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
           @Override
           public void onDateSet(DatePicker datePicker, int year, int month, int day) {

           }
       }, 0 , 0 , 0).show();
    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
