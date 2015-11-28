package com.iocm.freetime.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.iocm.freetime.activity.BaseActivity;

/**
 * Created by liubo on 15/11/21.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    abstract void initView();

    abstract void initListener();

    abstract void loadData();
}
