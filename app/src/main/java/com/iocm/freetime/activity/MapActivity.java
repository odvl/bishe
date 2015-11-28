package com.iocm.freetime.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.nplatform.comapi.basestruct.GeoPoint;
import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.LocationInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/3/17.
 */
public class MapActivity extends BaseActivity {
    private MapView mapView = null;
    private BaiduMap baiduMap;
    private Button getMarkerLocation;
    private List<String> lineList = new ArrayList<>();


    //添加覆盖物
//    private BitmapDescriptor mBitmapDescriptor;
    private LocationInfo locationInfo = new LocationInfo();
    private LinearLayout mMarkerLayout;
    private Context context;

    private String mCity = null;
    private static String mLocationMsg = null;
    private static String endMsg = null;

    //定位核心类
    private LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = null;

    //用于存放用户第一次进入的经纬度
    private static double mLatitude;
    private static double mLongitude;

    private boolean isFirstIn = true;

    private Handler handler = new Handler();

    private RoutePlanSearch mSearch;
    private PoiSearch poiSearch;

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            //
        }

        public void onGetTransitRouteResult(TransitRouteResult result) {
            if (result == null) {
                //    Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (result.error != SearchResult.ERRORNO.NO_ERROR) {
                //    Toast.makeText(MapActivity.this, "抱歉，未果", Toast.LENGTH_SHORT).show();
            }
            if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                //result.getSuggestAddrInfo()
                return;
            }
            if (result.error == SearchResult.ERRORNO.NO_ERROR) {

                List<String> hotelName = new ArrayList<String>();
                List<GeoPoint> JWpoints = new ArrayList<GeoPoint>();
                List<TransitRouteLine> line = result.getRouteLines();
                for (int i = 0; i < 1; i++) {
                    List<TransitRouteLine.TransitStep> line2 = line.get(i).getAllStep();
                    for (int k = 0; k < line2.size(); k++) {
                        System.out.println("1：：：" + line2.get(k).getInstructions());
                        System.out.println("2：：：" + line2.get(k).getEntrace());
                        System.out.println("3：：：" + line2.get(k).getExit());
                        System.out.println("4：：：" + line2.get(k).getStepType());
                        System.out.println("5：：：" + line2.get(k).getVehicleInfo());
                        System.out.println("6：：：" + line2.get(k).getWayPoints());
                        System.out.println("7：：：" + line2.get(k).toString());
                        System.out.println("________________________________________");
                        lineList.add(line2.get(k).getInstructions());

                    }

                    TextView s = (TextView) findViewById(R.id.text_show_location_msg);
                    if (lineList.size() != 0) {
                        s.setText(lineList.toString());
                    } else {
                        s.setText("未找到合适线路");
                    }
//                    Toast.makeText(MapActivity.this,"luxian:"+lineList.toString(),Toast.LENGTH_SHORT).show();
                }

//                for (MKPoiInfo info : result.getAllPoi()) {
//                    System.out.println("搜索结果位置信息:" + info.address);
//                    System.out.println("搜索结果城市信息:" + info.city);
//                    System.out.println("搜索结果name:" + info.name);
//                    System.out.println("酒店联系电话:" + info.phoneNum);
//                    System.out.println("搜索结果经纬度:" + info.pt);
//                    System.out.println("搜索结果ePoiType:" + info.ePoiType);
//                    bMapView.getController().animateTo(info.pt);
//                    // 将搜索到的酒店添加到list里面
//                    hotelName.add(info.name);
//                    //把所有的点添加到list中
//                    JWpoints.add(info.pt);
//                    // break;
//                }
                TransitRouteOverlay overlay = new TransitRouteOverlay(baiduMap);
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            }
        }

        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            //

        }
    };

    OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {

            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            //遍历所有POI，找到类型为公交线路的POI
            for (PoiInfo poi : poiResult.getAllPoi()) {
                if (poi.type == PoiInfo.POITYPE.BUS_LINE || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                    //说明该条POI为公交信息，获取该条POI的UID
                    //    Toast.makeText(context,poi.uid+"",Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    @Override
    void initView() {


        SDKInitializer.initialize(getApplicationContext());
//        mMapView = BaiduNaviManager.getInstance().createNMapView(this);

        setContentView(R.layout.map_layout);
        this.context = this;

        //获取地图控件的引用
        getMap();
        initDatas();
        initLocation();
        initMarker();
        findViewById(R.id.btn_to_here).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MapActivity.this, "正在获取线路，请稍等。", Toast.LENGTH_SHORT).show();
                mSearch = RoutePlanSearch.newInstance();
                poiSearch = PoiSearch.newInstance();
                poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
                mSearch.setOnGetRoutePlanResultListener(listener);

                PlanNode stNode = PlanNode.withCityNameAndPlaceName("杭州市", mLocationMsg);
                PlanNode s = PlanNode.withLocation(new LatLng(mLatitude, mLongitude));
                System.out.println("my location0 :" + mLocationMsg);
                PlanNode enNode = PlanNode.withCityNameAndPlaceName("杭州市", endMsg);
                mSearch.transitSearch((new TransitRoutePlanOption())
                        .from(s)
                        .city("杭州")
                        .to(enNode));

                poiSearch.searchInCity((new PoiCitySearchOption())
                        .city("杭州")
                        .keyword("104"));


            }
        });

        getMarkerLocation = (Button) findViewById(R.id.btn_get_myloca);
    }

    private void initDatas() {
        Intent intent = getIntent();
        locationInfo.setAddress("sss");
        locationInfo.setName("fff");
        locationInfo.setLatitude(10.001);
        locationInfo.setLongitude(100.100);
        endMsg = "hahh ";
        // Toast.makeText(context,"address:"+locationInfo.getAddress(),Toast.LENGTH_SHORT).show();
    }

    private void initMarker() {
//        mBitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.maker);
        mMarkerLayout = (LinearLayout) findViewById(R.id.relativeLayout_dialog);
    }

//    private void addOverlay() {
//       baiduMap.clear();
//        LatLng latLng = null;
//        Marker marker = null;
//        OverlayOptions overlayOptions ;
//
//        latLng = new LatLng(locationInfo.getLatitude(),locationInfo.getLongitude());
//        overlayOptions = new MarkerOptions().position(latLng).icon(mBitmapDescriptor).zIndex(5);
//        marker = (Marker) baiduMap.addOverlay(overlayOptions);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("info",locationInfo);
//        marker.setExtraInfo(bundle);
//        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
//        baiduMap.animateMapStatus(mapStatusUpdate);
//
//
//
//    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);


    }

    @Override
    public void initListener() {

        getMarkerLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MapActivity.this,"实现定位到我的位置",Toast.LENGTH_SHORT).show();
                LatLng latLng = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
            }
        });

//        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Bundle bundle = marker.getExtraInfo();
//                LocationInfo locationInfo1 = (LocationInfo) bundle.getSerializable("info");
//
//                TextView person = (TextView) mMarkerLayout.findViewById(R.id.text_person);
//                TextView tel = (TextView) mMarkerLayout.findViewById(R.id.text_tel);
//                TextView add = (TextView) mMarkerLayout.findViewById(R.id.text_address);
//                TextView distance = (TextView) mMarkerLayout.findViewById(R.id.text_distace);
//
//                Button walk  = (Button) mMarkerLayout.findViewById(R.id.btn_walk);
//                Button bus = (Button) mMarkerLayout.findViewById(R.id.btn_bus);
//
//                walk.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//
//                title.setText(locationInfo1.getName());
//                person.setText("发布人为空？");
//                tel.setText("电话为空？");
//                add.setText(locationInfo1.getAddress());
//                distance.setText("距您的距离未知.");
//
////                getMarkerLocation.setVisibility(View.GONE);
//                mMarkerLayout.setVisibility(View.VISIBLE);
//           //     Toast.makeText(getApplicationContext(),"hell",Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
////                getMarkerLocation.setText("dd");
//                getMarkerLocation.setVisibility(View.VISIBLE);
//                mMarkerLayout.setVisibility(View.GONE);
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });
    }

    @Override
    void loadData() {

    }


    private void getMap() {
        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(15.0f);
        baiduMap.setMapStatus(mapStatusUpdate);
    }

    @Override
    protected void onStart() {

        super.onStart();
        baiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mSearch.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            MyLocationData data = new MyLocationData.Builder()
                    .longitude(bdLocation.getLongitude())
                    .latitude(bdLocation.getLatitude())
                    .accuracy(bdLocation.getRadius())
                    .build();

            baiduMap.setMyLocationData(data);
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();


            if (isFirstIn) {
                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
                mCity = bdLocation.getCity();
                mLocationMsg = bdLocation.getAddrStr();
                System.out.println(bdLocation.getCity() + "coty");
                Toast.makeText(MapActivity.this, "我的位置" + bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
                isFirstIn = false;

            }

        }
    }
}
