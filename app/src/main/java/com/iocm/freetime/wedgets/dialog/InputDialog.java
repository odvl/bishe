package com.iocm.freetime.wedgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 16/1/2.
 */
public class InputDialog extends AlertDialog {

    Context context;
    public InputDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = LayoutInflater.from(context).inflate(R.layout.dialog_input, null);
        setContentView(root);
    }
}
