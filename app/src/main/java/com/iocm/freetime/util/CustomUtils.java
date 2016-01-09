package com.iocm.freetime.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iocm.freetime.cache.Cache;
import com.iocm.freetime.common.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 验证手机号码
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    public static boolean checkUserLogin(Context context) {
        Cache cache = Cache.getInstance(context);
        return cache.getBooleanValue(Constant.User.login);
    }


    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
