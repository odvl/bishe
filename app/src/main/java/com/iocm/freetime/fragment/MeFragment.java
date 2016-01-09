package com.iocm.freetime.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.input.InputManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidu.mapapi.map.Text;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.AboutMeActivity;
import com.iocm.freetime.activity.CollectionsTaskActivity;
import com.iocm.freetime.activity.FansOrFollowActivity;
import com.iocm.freetime.activity.FeedBackActivity;
import com.iocm.freetime.activity.UserApplyedActivity;
import com.iocm.freetime.activity.CreatedTaskListActivity;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.cache.Cache;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.util.CustomUtils;
import com.iocm.freetime.util.Setting;
import com.iocm.freetime.util.TLog;
import com.iocm.freetime.wedgets.dialog.CommonDialog;
import com.iocm.freetime.wedgets.dialog.InputDialog;
import com.ozn.circleimage.CircleImageView;

import java.util.List;

/**
 * Created by liubo on 15/7/13.
 */
public class MeFragment extends TaskFragments implements View.OnClickListener {


    private CircleImageView mUserPhoto;
    private TextView mUsername;
    private TextView mUserMobile;
    private TextView mLoginOutBtn;
    private TextView mUserReleaseBtn;
    private TextView mUserApplyBtn;
    private TextView mUserCollect;
    private TextView aboutMeTextView;
    private TextView updateTextView;
    private TextView feedBackTextView;


    private View mMeContent;

    private TextView followTextView;
    private TextView fansTextView;


    Cache mCache;
    private CustomDialogFragment mProgressDialogFragment;

    private UpdateUserInfoTask mUpdateUserInfoTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_me, container, false);
        init();
        setupViewInMe(_root);
        getFans();
        getFollow();
        return _root;

    }

    private void init() {
        mCache = Cache.getInstance(getActivity());


        mUpdateUserInfoTask = new UpdateUserInfoTask();
        mUpdateUserInfoTask.execute(null == null ? R.drawable.bestican_teaser : Integer.parseInt("0"));

    }


    private void getFollow() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("userId", AVUser.getCurrentUser().getObjectId());

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    followTextView.setText("关注: " + list.size() + "人");
                }
            }
        });
    }


    private void getFans() {
        AVQuery<AVObject> query = new AVQuery("Follow");
        query.whereEqualTo("followId", AVUser.getCurrentUser().getObjectId());

        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list == null) {
                    TLog.d("liubo", "null");
                } else {
                    fansTextView.setText("粉丝: " + list.size() + "人");
                }
            }
        });

    }

    private void setupViewInMe(View root) {
        mUsername = (TextView) root.findViewById(R.id.username);
        mUserMobile = (TextView) root.findViewById(R.id.user_mobile);
        mUserPhoto = (CircleImageView) root.findViewById(R.id.user_photo);

        mUserReleaseBtn = (TextView) root.findViewById(R.id.user_release);
        mUserReleaseBtn.setOnClickListener(MeFragment.this);
        mUserApplyBtn = (TextView) root.findViewById(R.id.user_apply);
        mUserApplyBtn.setOnClickListener(MeFragment.this);
        mLoginOutBtn = (TextView) root.findViewById(R.id.login_out);
        mLoginOutBtn.setOnClickListener(MeFragment.this);

        mUserCollect = (TextView) root.findViewById(R.id.user_collect);
        mUserCollect.setOnClickListener(MeFragment.this);

        aboutMeTextView = (TextView) root.findViewById(R.id.aboutMeTextView);
        aboutMeTextView.setOnClickListener(MeFragment.this);

        updateTextView = (TextView) root.findViewById(R.id.checkUpdateTextView);
        updateTextView.setOnClickListener(MeFragment.this);


        mUserMobile.setText(mCache.getStringValue(Constant.User.username));
        mUsername.setText(mCache.getStringValue(Constant.User.username));
        mUsername.setOnClickListener(MeFragment.this);
        feedBackTextView = (TextView) root.findViewById(R.id.feedBackTextView);
        feedBackTextView.setOnClickListener(MeFragment.this);

        fansTextView = (TextView) root.findViewById(R.id.fansTextView);
        followTextView = (TextView) root.findViewById(R.id.followTextView);

        fansTextView.setOnClickListener(MeFragment.this);
        followTextView.setOnClickListener(MeFragment.this);

    }

    /**
     * 更新用户信息的异步任务
     * 第一个参数启动时传入
     * 第二个参数更新进度返回的进度
     * 第三个参数返回的类型
     */
    class UpdateUserInfoTask extends AsyncTask<Integer, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialogFragment = new CustomDialogFragment();
            mProgressDialogFragment.show(getFragmentManager(), "hh");
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), integers[0]);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mUserPhoto.setSrc(bitmap);

            mProgressDialogFragment.dismiss();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //用户申请
            case R.id.user_apply: {
                jumpActivity(UserApplyedActivity.class);
                break;
            }
            //用户发布
            case R.id.user_release: {
                jumpActivity(CreatedTaskListActivity.class);
                break;
            }
            //用户退出
            case R.id.login_out: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确认退出登录");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //清除用户信息，跳转界面
                        NameValue<Boolean> value = new NameValue<Boolean>(Constant.User.login, false);
                        mCache.saveValue(value);
                        AVUser.logOut();
                        if (mLogoutClickListener != null) {
                            mLogoutClickListener.logoutClick();
                        }

                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create();
                builder.show();
                break;
            }
            case R.id.user_collect: {
                jumpActivity(CollectionsTaskActivity.class);
                break;
            }
            case R.id.aboutMeTextView: {
                jumpActivity(AboutMeActivity.class);
                break;
            }
            case R.id.checkUpdateTextView: {
                checkUpdate();
                break;
            }
            case R.id.username: {
                updateUsername();
                break;
            }
            case R.id.feedBackTextView: {
                jumpActivity(FeedBackActivity.class);
                break;
            }
            case R.id.fansTextView: {
                Intent intent = new Intent(getActivity(), FansOrFollowActivity.class);
                intent.putExtra(Constant.User.userId, AVUser.getCurrentUser().getObjectId());
                intent.putExtra("fansOrfollow", 1);
                startActivity(intent);
                break;
            }
            case R.id.followTextView: {
                Intent intent = new Intent(getActivity(), FansOrFollowActivity.class);
                intent.putExtra(Constant.User.userId, AVUser.getCurrentUser().getObjectId());
                intent.putExtra("fansOrfollow", 2);
                startActivity(intent);
                break;
            }
        }
    }


    private void updateUsername() {
        final InputDialog dialog = new InputDialog(getActivity());
        dialog.show();

        final EditText editText = (EditText) dialog.findViewById(R.id.inputEdit);
        editText.setHint("请输入新用户名");


        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        Button no = (Button) dialog.findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button yes = (Button) dialog.findViewById(R.id.yes);
        yes.setText("确定");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    return;
                }
                AVObject avObject = AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
                avObject.put("username", editText.getText().toString());
                avObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            CustomUtils.showToast(getActivity(), "更新成功！");
                            dialog.dismiss();
                            mUsername.setText(editText.getText().toString());
                            mUserMobile.setText(editText.getText().toString());
                            mCache.saveValue(new NameValue(Constant.User.username, editText.getText().toString()));
                        } else {
                            CustomUtils.showToast(getActivity(), "该用户名已被占用，请重试！");
                        }
                    }
                });
            }
        });
    }

    private void checkUpdate() {
        final CommonDialog dialog = new CommonDialog(getActivity());
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

    public void jumpActivity(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    private LogoutClickListener mLogoutClickListener;

    public void setLogoutClickListener(LogoutClickListener logoutClickListener) {
        this.mLogoutClickListener = logoutClickListener;
    }

    public interface LogoutClickListener {
        void logoutClick();
    }
}




