package com.iocm.freetime.wedgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 16/1/2.
 */
public class InputDialog extends AlertDialog {

    public InputDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_input);
    }
}
