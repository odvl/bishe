package com.iocm.freetime.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.activity.BaseActivity;

/**
 * Created by liubo on 15/11/21.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return initView(inflater);
    }

    abstract View initView(LayoutInflater inflater);

    abstract void initListener();

    abstract void loadData();
}
