package com.iocm.freetime.base;

import android.app.Fragment;
import android.content.Intent;

/**
 * Created by liubo on 15/7/13.
 */
public class TaskFragments extends Fragment {

    public void jumpActivity(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }
}
