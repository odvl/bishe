package com.iocm.freetime.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;

import com.iocm.freetime.bean.NameValue;
import com.iocm.freetime.common.Constant;

import java.util.Map;
import java.util.Set;

/**
 * Created by liubo on 15/11/28.
 */
public class Cache<T> {

    private SharedPreferences mSharedPreferences;

    private Context context;

    private static Cache cache;

    private SharedPreferences.Editor mEditor;

    private static Cache getInstance(Context context) {
        if (cache == null) {
            cache = new Cache(context);
        }
        return cache;
    }

    private Cache(Context context) {

        mSharedPreferences = context.getSharedPreferences(Constant.SharedPreferences.Name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

    }

    public void saveValue(NameValue<T> value) {
        if (value.getValue() instanceof String) {
            mEditor.putString(value.getName(), (String) value.getValue());
        } else if (value.getValue() instanceof Boolean) {
            mEditor.putBoolean(value.getName(), (Boolean) value.getValue());
        } else if (value.getValue() instanceof Integer) {
            mEditor.putInt(value.getName(), (Integer) value.getValue());
        }
        mEditor.commit();
    }


    public String getStringValue(String name) {
        return mSharedPreferences.getString(name, "");
    }

    public Boolean getBooleanValue(String name) {
        return mSharedPreferences.getBoolean(name, false);
    }

    public Integer getIntValue(String name) {
        return mSharedPreferences.getInt(name, -1);
    }
}
