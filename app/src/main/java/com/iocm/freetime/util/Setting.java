package com.iocm.freetime.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.bean.User;
import com.iocm.freetime.common.Constant;
import com.iocm.freetime.http.GsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liubo on 15/7/21.
 */
public class Setting {
    private SharedPreferences mSharePreferences;
    private static Setting sSetting;
    private Map<String, User> mCache;
    private GsonUtils mGson;

    public static synchronized Setting getInstance(Context context) {
        if (sSetting == null) {
            sSetting = new Setting(context);
        }
        return sSetting;
    }

    private Setting(Context context) {
        load(context);
    }

    private void load(Context context) {
        mSharePreferences = context.getSharedPreferences(Constant.Key.AppName, Context.MODE_PRIVATE);
        mGson = GsonUtils.getInstance();
        mCache = new HashMap<>();

        String string = this.getStringValue(Constant.Key.UserInfo);
        if (string != null) {
            mCache.put(Constant.Key.UserInfo, mGson.getGson().fromJson(string, User.class));
        }
    }

    public void setCache(User user) {
        String string = mGson.getGson().toJson(user);
        saveStringValue(string);
    }

    public User getCache() {
        return mGson.getGson().fromJson(this.getStringValue(Constant.Key.UserInfo), User.class);
    }

    public void remooveCache() {
        this.mSharePreferences.edit().remove(Constant.Key.UserInfo).apply();
    }

    private void saveStringValue(String value) {
        mSharePreferences.edit().putString(Constant.Key.UserInfo, value).apply();
    }

    private String getStringValue(String name) {
        return mSharePreferences.getString(name, null);
    }

    private void delete(String name) {
        mSharePreferences.edit().remove(name).apply();
    }

    private int getIntValue(String name) {
        return mSharePreferences.getInt(name, -1);
    }

    private void saveIntValue(NameValue value) {
        mSharePreferences.edit().putInt(value.getName(), (Integer) value.getValue()).apply();
    }


}
