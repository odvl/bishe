package com.iocm.freetime.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.UserApplyedActivity;
import com.iocm.freetime.activity.UserReleasedActivity;
import com.iocm.freetime.base.TaskFragments;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.util.Setting;
import com.ozn.circleimage.CircleImageView;

/**
 * Created by liubo on 15/7/13.
 */
public class MeFragment extends TaskFragments implements View.OnClickListener {

    private Setting mSetting;

    private CircleImageView mUserPhoto;
    private TextView mUsername;
    private TextView mUserMobile;
    private TextView mLoginOutBtn;
    private TextView mUserReleaseBtn;
    private TextView mUserApplyBtn;

    private View mMeContent;
    private User mUser;

    private CustomDialogFragment mProgressDialogFragment;

    private UpdateUserInfoTask mUpdateUserInfoTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View _root = inflater.inflate(R.layout.fragment_me, container, false);
        init();
        setupViewInMe(_root);
        return _root;

    }

    private void init() {

        mSetting = Setting.getInstance(getActivity());
        mUser = mSetting.getCache();
        mUpdateUserInfoTask = new UpdateUserInfoTask();
        mUpdateUserInfoTask.execute(mUser.getUserPhoto() == null ?R.drawable.bestican_teaser : Integer.parseInt(mUser.getUserPhoto()));

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



        mUserMobile.setText(mUser.getPhoneNumber());
        mUsername.setText(mUser.getName());
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
                jumpActivity(UserReleasedActivity.class);
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
                        mSetting.remooveCache();
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
        }
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




