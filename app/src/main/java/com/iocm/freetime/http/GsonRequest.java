package com.iocm.freetime.http;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * Created by liubo on 15/6/21.
 */
public class GsonRequest<T> extends Request<T> {
    private static final String TAG = GsonRequest.class.getName();

    private Class<T> mClazz;
    private Type mTypeOfT;
    private Gson mGson = GsonUtils.getInstance().getGson();
    private Response.Listener<T> mListener;

    public GsonRequest(int method, String url, Type mTypeOfT, Response.Listener<T> mListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.mTypeOfT = mTypeOfT;
        this.mListener = mListener;
    }

    public GsonRequest(int method, String url, Class<T> mClazz, Response.Listener<T> mListener, Response.ErrorListener listener) {

        super(method, url, listener);
        this.mListener = mListener;
        this.mClazz = mClazz;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, "utf-8");
            T result = json2T(data);
            Log.i(TAG, "response = " + result);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(T t) {

    }


    private T json2T(String data) {
        if (mClazz != null) {
            return mGson.fromJson(data, mClazz);
        } else if (mTypeOfT != null) {
            return mGson.fromJson(data, mTypeOfT);
        }
        return null;
    }
}
