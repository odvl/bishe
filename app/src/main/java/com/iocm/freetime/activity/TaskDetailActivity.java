package com.iocm.freetime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.wedgets.CommonToolBar;
import com.iocm.freetime.wedgets.dialog.InputDialog;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/1/22.
 */
public class TaskDetailActivity extends BaseActivity {


    private int activity_id = -1;
    private String aname, adate, atitle;
    private String build;
    private double longitude;
    private double latitude;
    private Calendar calendar = Calendar.getInstance();
    private int beginYear = calendar.get(Calendar.YEAR);
    private int beginMonth = calendar.get(Calendar.MONTH);
    private int beginDay = calendar.get(Calendar.DAY_OF_MONTH);

    private CommonToolBar mToolbar;

    private TextView tv_username;
    private TextView tv_title;
    private TextView tv_detail;
    private TextView tv_mobile;
    private TextView tv_time;
    private TextView tv_location;

    private ImageView iv_call;
    private ImageView iv_location;

    private Button btn_add_collection;
    private Button btn_apply;

    private Context mContext;

    private Tasks tasks;


    @Override
    void initView() {

        setContentView(R.layout.activity_task_detail);

        mContext = TaskDetailActivity.this;

        mToolbar = (CommonToolBar) findViewById(R.id.toolbar);
        mToolbar.setOnCommonToolBarClickListener(this);

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_detail = (TextView) findViewById(R.id.tv_task_detail);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_location = (TextView) findViewById(R.id.tv_location);

        iv_call = (ImageView) findViewById(R.id.iv_call);
        iv_call.setOnClickListener(this);

        iv_location = (ImageView) findViewById(R.id.iv_location);
        iv_location.setOnClickListener(this);

        btn_add_collection = (Button) findViewById(R.id.add_collect_btn);
        btn_add_collection.setOnClickListener(this);

        btn_apply = (Button) findViewById(R.id.apply_btn);
        btn_apply.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    void initListener() {

    }

    @Override
    void loadData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tasks = (Tasks) bundle.getSerializable(Constant.Key.Task);
        latitude = tasks.getLatitude();
        longitude = tasks.getLongitude();
        build = tasks.getBuild();

        tv_username.setText(tasks.getName());
        tv_time.setText(tasks.getBeginTime() + "-" + tasks.getEndTime());
        tv_title.setText(tasks.getTitle());
        tv_detail.setText(tasks.getBody());
        tv_mobile.setText(tasks.getPhoneNumber());
        tv_location.setText(tasks.getBuild());

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == iv_call.getId()) {
            CustomUtils.makeCall(mContext, tv_mobile.getText().toString().trim());
        } else if (id == iv_location.getId()) {

            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString(Constant.LeancloundTable.TaskTable.build, build);
            jumpActivity(MapActivity.class, bundle);

        } else if (id == btn_add_collection.getId()) {

            try {
                tasks.save();
                CustomUtils.showToast(mContext, "已加入收藏!");
            } catch (Exception e) {
                CustomUtils.showToast(mContext, "加入收藏失败!");
            }
        } else if (id == btn_apply.getId()) {
            applyTask();

        }

    }

    private void applyTask() {
        final InputDialog dialog = new InputDialog(this);
        dialog.show();
        final EditText editText = (EditText) dialog.findViewById(R.id.inputEdit);
        Button no = (Button) dialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        });
        Button yes = (Button) dialog.findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AVObject apply = new AVObject("Apply");
                apply.put("taskId", tasks.getObjectId());
                apply.put("taskName", tasks.getTitle());
                apply.put("joinUserId", AVUser.getCurrentUser().getObjectId());
                apply.put("joinUserName", AVUser.getCurrentUser().getUsername());
                apply.put("publishUserName", tasks.getName());
                apply.put("publishUserId", tasks.getUserId());
                apply.put("state", 0);
                apply.put("message", TextUtils.isEmpty(editText.getText().toString()) ? "我能胜任！！" : editText.getText().toString());
                apply.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            CustomUtils.showToast(mContext, "申请成功，请稍后！! ");
                        } else {
                            CustomUtils.showToast(mContext, "申请失败，可能是您已经申请过了！");
                        }
                    }
                });
            }
        });


    }

    @Override
    public void leftClickListener() {
        finish();
    }

    @Override
    public void rightClickListener() {
        report();
    }

    private void report() {
        AVObject object = new AVObject("Report");
        object.put("name", AVUser.getCurrentUser().getUsername());
        object.put("userId", AVUser.getCurrentUser().getObjectId());
        object.put("taskTitle", tasks.getTitle());
        object.put("taskId", tasks.getObjectId());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (null == e) {
                    CustomUtils.showToast(mContext, "举报成功， 我们将进行审核！");
                } else {
                    CustomUtils.showToast(mContext, "举报失败，您已经举报过了！");
                }
            }
        });
    }
}
