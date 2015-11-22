package com.iocm.freetime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iocm.administrator.freetime.R;

/**
 * Created by Administrator on 2015/2/11.
 */
public class UserNotLogFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View userNotLoglayout = inflater.inflate(R.layout.user_notlogin,container,false);
        return userNotLoglayout;
    }
}
