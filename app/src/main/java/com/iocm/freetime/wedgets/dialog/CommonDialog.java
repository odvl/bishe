package com.iocm.freetime.wedgets.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 15/11/21.
 */
public class CommonDialog extends AlertDialog {
    public CommonDialog(Context context) {
        this(context, AlertDialog.THEME_HOLO_LIGHT);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCancelable(false);
    }

    @Override
    public void onBackPressed() {

    }
}
