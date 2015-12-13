package com.iocm.freetime.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/12/13.
 */
public class VerifyMobileFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_verify_mobile, container, false);

        return root;
    }
}
