package com.iocm.freetime.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by liubo on 15/10/16.
 */
public class CustomUtils {

    public static void setRecyclerViewHeight(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        if (null == adapter) {
            return;
        }

        int totalHeight = 0;
        for (int i =0;i < adapter.getItemCount(); i++) {
            View item = recyclerView.getChildAt(i);

            item.measure(0, 0);
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        layoutParams.height = totalHeight;

        recyclerView.setLayoutParams(layoutParams);

    }


    /**
     * 显示toast
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 调用系统的拨打电话界面
     * @param context
     * @param mobile
     */
    public static void makeCall(Context context, String mobile) {
        Intent intent  = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(intent);
    }
}
