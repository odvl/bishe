package com.iocm.freetime;


import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.iocm.freetime.bean.Tasks;

/**
 * Created by liubo on 15/12/3.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(Tasks.class);
        ActiveAndroid.initialize(configurationBuilder.create());


    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
