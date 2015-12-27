package qcjlibrary.util;

import java.util.List;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.zhiyicx.zycx.R;

/**
 * author：qiuchunjia time：上午11:55:27
 * <p/>
 * 类描述：这个类是实现专门对百度地图做的一个封装
 */

public class BaiduUtil {
    /**
     * 初始化定位的option span 每几秒请求一次服务器
     *
     * @return
     */
    public static LocationClientOption initOption(int span) {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int defaultSpan = 5 * 1000;
        if (span > 1 * 1000) {
            defaultSpan = span;
        }
        option.setScanSpan(defaultSpan);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        return option;
    }

    /**
     * 开启定位
     *
     * @param client
     * @param span             每多少秒请求服务器一次
     * @param locationListener
     */
    public static void startLocation(LocationClient client, int span,
                                     BDLocationListener locationListener) {
        if (client != null && locationListener != null) {
            client.setLocOption(initOption(span));
            client.registerLocationListener(locationListener);
            client.start();
        }

    }

    /**
     * 初始化地图需要显示的标志
     *
     * @param map       地图
     * @param ResouceId 定位图标id
     */
    public static void initSiteDisplay(BaiduMap map, int ResouceId) {
        if (map != null) {
            map.setMyLocationEnabled(true);
            BitmapDescriptor mDescriptor = null;
            if (ResouceId > 0) {
                mDescriptor = BitmapDescriptorFactory.fromResource(ResouceId);
            }
            MyLocationConfiguration config = new MyLocationConfiguration(
                    MyLocationConfiguration.LocationMode.NORMAL, true,
                    mDescriptor);
            map.setMyLocationConfigeration(config);
        }
    }

    /**
     * 显示当前的位置
     *
     * @param baiduMap
     * @param latLng   经纬度
     */
    public static void displayCurrentPos(BaiduMap baiduMap, LatLng latLng) {
        if (baiduMap != null && latLng != null) {
            MyLocationData locData = new MyLocationData.Builder()
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(0).latitude(latLng.latitude)
                    .longitude(latLng.longitude).build();
            baiduMap.setMyLocationData(locData);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
            baiduMap.animateMapStatus(u);
        }
    }

    /**
     * 做地标
     *
     * @param baiduMap            百度地图
     * @param latLng              经纬度
     * @param markers             各个地标的集合
     * @param markerClickListener 地标点击后的监听器（这个可以用来点击地标后，显示相关信息）
     */
    public static void markPos(BaiduMap baiduMap, LatLng latLng,
                               List<Marker> markers, OnMarkerClickListener markerClickListener) {
        if (baiduMap != null && latLng != null) {
            // 构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.location);
            // 构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions().position(latLng).icon(
                    bitmap);
            // 在地图上添加Marker，并显示
            Marker marker = (Marker) baiduMap.addOverlay(option);
            if (marker != null) {
                markers.add(marker);
                baiduMap.setOnMarkerClickListener(markerClickListener);
            }
        }
    }

    /**
     * 清楚地图上的所有覆盖物
     *
     * @param baiduMap
     */
    public static void clearAllOverLay(BaiduMap baiduMap) {
        if (baiduMap != null) {
            baiduMap.clear();
        }
    }

    /************************* 百度poi查询 ******************************************/
    /**
     * 查询地址经纬度，或者更加经纬度差选地址
     *
     * @param geoCoder 查询的客服端
     * @param option   需要查找的信息
     * @param listener 查询结果
     */
    public static void PoiAddress(GeoCoder geoCoder, GeoCodeOption option,
                                  OnGetGeoCoderResultListener listener) {
        if (geoCoder != null && option != null && listener != null) {
            geoCoder.setOnGetGeoCodeResultListener(listener);
            geoCoder.geocode(option);
        }

    }
}
