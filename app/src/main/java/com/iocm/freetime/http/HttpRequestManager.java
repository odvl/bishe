package com.iocm.freetime.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.iocm.freetime.util.NotificationHelper;
import com.iocm.freetime.util.TLog;
import com.google.gson.Gson;

import java.util.Map;

/**
 * 网络请求通信
 * Created by liubo on 15/6/21.
 */
public class HttpRequestManager {

    private static final String TAG = HttpRequestManager.class.getName();

    private static HttpRequestManager sHttpRequestManager;
    private RequestQueue mQueue;
    private Gson mGson;
    private static int sTaskId = 0;

    public static synchronized HttpRequestManager getInstance(Context context) {
        if (sHttpRequestManager == null) {
            sHttpRequestManager = new HttpRequestManager(context.getApplicationContext());
        }

        return sHttpRequestManager;
    }

    private HttpRequestManager(Context context) {
        mQueue = Volley.newRequestQueue(context);
        mGson = GsonUtils.getInstance().getGson();
    }

    private RequestQueue getQueue() {
        return this.mQueue;
    }
    private int obtainTaskId() {
        return sTaskId++;
    }

    public <T> int jsonPostRequest(String url, final Map<String, String> params, Class clazz, final Map<String, String> userData, final OnResponseListener<T> listener) {

        final int taskId = obtainTaskId();

        GsonRequest<T> request = new GsonRequest<T>(Request.Method.POST, url, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                if (listener != null) {
                    listener.onSuccess(taskId, t, userData);
                    listener.onResponse(taskId, userData);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (listener != null) {
                    listener.onResponse(taskId, userData);
                    listener.onFail(taskId, userData, volleyError);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        TLog.i(TAG, "params =" + mGson.toJson(params));
        TLog.i(TAG, "url =" + url);
        mQueue.add(request);
        return 0;
    }

    public static abstract class OnResponseListener<T> {
        Context mContext;
        protected OnResponseListener(Context context) {
            this.mContext = context;
        }
        public void onSuccess(int taskId,T data, Map<String, String> userData){

        };

        public abstract void onResponse(int taskId, Map<String, String> userData);

        public void onFail(int taskId, Map<String, String> userData, VolleyError error){

            if (error instanceof TimeoutError) {
                NotificationHelper.toast(mContext, "连接超时");
            } else if (error instanceof NoConnectionError) {
                NotificationHelper.toast(mContext, "无网络连接");
            } else {
                NotificationHelper.toast(mContext,  "服务器错误");
            }

        }
    }
}
