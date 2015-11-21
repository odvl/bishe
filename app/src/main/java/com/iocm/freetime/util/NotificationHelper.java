package com.iocm.freetime.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liubo on 15/6/22.
 */
public class NotificationHelper {
    public static void toast(Context context, String toast) {
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
    }
}
