package com.iocm.freetime;


import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.avos.avoscloud.AVOSCloud;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.iocm.freetime.bean.SearchHistory;
import com.iocm.freetime.bean.Tasks;
import com.iocm.freetime.cache.Cache;

/**
 * Created by liubo on 15/12/3.
 */
public class MyApplication extends Application {

    private static final String key = "dAjr1hktZjLYhqqQV0UaPhwd";
    BMapManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        Configuration.Builder configurationBuilder = new Configuration.Builder(this);
        configurationBuilder.addModelClasses(Tasks.class, SearchHistory.class);
        ActiveAndroid.initialize(configurationBuilder.create());

        //引入leanclound
        AVOSCloud.initialize(this, "ci8awJklYEVbfosUdXjYf0Yf-gzGzoHsz", "ehvubCVB9ORRfzdn1mXRnahD");
        SDKInitializer.initialize(this);
//        initBMapManager(this);


    }

    public void initBMapManager(Context context) {
        if (manager == null) {
            manager = new BMapManager();
        }
        manager.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public String getUserName() {
        Cache cache = Cache.getInstance(getApplicationContext());
        return cache.getStringValue("username");

    }
}
