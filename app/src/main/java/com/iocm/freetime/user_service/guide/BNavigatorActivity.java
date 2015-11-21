package com.iocm.freetime.user_service.guide;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.comapi.mapcontrol.BNMapController;
import com.baidu.navisdk.comapi.routeplan.BNRoutePlaner;
import com.baidu.navisdk.comapi.tts.BNTTSPlayer;
import com.baidu.navisdk.comapi.tts.BNavigatorTTSPlayer;
import com.baidu.navisdk.comapi.tts.IBNTTSPlayerListener;
import com.baidu.navisdk.model.datastruct.LocData;
import com.baidu.navisdk.model.datastruct.RoutePlanNode;
import com.baidu.navisdk.model.datastruct.SensorData;
import com.baidu.navisdk.ui.routeguide.BNavigator;
import com.baidu.navisdk.ui.routeguide.IBNavigatorListener;
import com.baidu.navisdk.ui.widget.RoutePlanObserver;
import com.baidu.nplatform.comapi.map.MapGLSurfaceView;

/**
 * Created by Administrator on 2015/3/21.
 */
public class BNavigatorActivity extends Activity {

    private RoutePlanNode mRoutePlanModel;

    private IBNavigatorListener   mIBNavigatorListener  = new IBNavigatorListener() {
        @Override
        public void onPageJump(int i, Object o) {
            if(IBNavigatorListener.PAGE_JUMP_WHEN_GUIDE_END == i) {
                finish();
            } else  if(IBNavigatorListener.PAGE_JUMP_WHEN_ROUTE_PLAN_FAIL == i) {
                finish();
            }
        }

        @Override
        public void notifyStartNav() {
            BaiduNaviManager.getInstance().dismissWaitProgressDialog();
        }

        @Override
        public void onYawingRequestStart() {

        }

        @Override
        public void onYawingRequestSuccess() {

        }

        @Override
        public void notifySensorData(SensorData sensorData) {

        }

        @Override
        public void notifyNmeaData(String s) {

        }

        @Override
        public void notifyLoacteData(LocData locData) {

        }

        @Override
        public void notifyGPSStatusData(int i) {

        }

        @Override
        public void notifyViewModeChanged(int i) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建NmapView
        MapGLSurfaceView nMapView = BaiduNaviManager.getInstance().createNMapView(this);

        //创建导航视图
        View navigatorView = BNavigator.getInstance().init(BNavigatorActivity.this, getIntent().getExtras(), nMapView);

        //填充视图
        setContentView(navigatorView);

        BNavigator.getInstance().setListener(mIBNavigatorListener);
        BNavigator.getInstance().startNav();

        // 初始化TTS. 开发者也可以使用独立TTS模块，不用使用导航SDK提供的TTS
        BNTTSPlayer.initPlayer();

        //设置TTS播放回调
        BNavigatorTTSPlayer.setTTSPlayerListener(new IBNTTSPlayerListener() {

            @Override
            public int playTTSText(String arg0, int arg1) {
                //开发者可以使用其他TTS的API
                return BNTTSPlayer.playTTSText(arg0, arg1);
            }

            @Override
            public void phoneHangUp() {
                //手机挂断
            }

            @Override
            public void phoneCalling() {
                //通话中
            }

            @Override
            public int getTTSState() {
                //开发者可以使用其他TTS的API,
                return BNTTSPlayer.getTTSState();
            }
        });

        BNRoutePlaner.getInstance().setObserver(new RoutePlanObserver(this, new RoutePlanObserver.IJumpToDownloadListener() {

            @Override
            public void onJumpToDownloadOfflineData() {
                // TODO Auto-generated method stub

            }
        }));



    }


    @Override
    public void onResume() {
        BNavigator.getInstance().resume();
        super.onResume();
        BNMapController.getInstance().onResume();
    };

    @Override
    public void onPause() {
        BNavigator.getInstance().pause();
        super.onPause();
        BNMapController.getInstance().onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        BNavigator.getInstance().onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }

    public void onBackPressed(){
        BNavigator.getInstance().onBackPressed();
    }

    @Override
    public void onDestroy(){
        BNavigator.destory();
        BNRoutePlaner.getInstance().setObserver(null);
        super.onDestroy();
    }




}
