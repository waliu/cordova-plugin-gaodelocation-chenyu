package com.chenyu.GaoDeLocation;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.hosp123.testapp.location.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SerialLocation implements SerialLocationInterface{

    public AMapLocationClient locationClient = null;
    public AMapLocationClientOption locationOption = null;
    private Context mContext;

    List<SerialLocationDelegate> l = new ArrayList<>();

    public SerialLocation(Context mContext) {
        this.mContext = mContext;
        this.initLocation();
    }

    /**
     * 初始化定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(mContext);

        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }


    public void setLocationOption(JSONObject message) throws JSONException {

        JSONObject androidOption=message.getJSONObject("androidOption");

        //设置逆地址定位
        Integer locationMode = androidOption.getInt("locationMode");

        Boolean gpsFirst = androidOption.getBoolean("gpsFirst");

        Integer HttpTimeOut = androidOption.getInt("HttpTimeOut");

        Integer interval = androidOption.getInt("interval");

        Boolean needAddress = androidOption.getBoolean("needAddress");

        Boolean onceLocation = androidOption.getBoolean("onceLocation");

        Boolean onceLocationLatest = androidOption.getBoolean("onceLocationLatest");

        Integer locationProtocol = androidOption.getInt("locationProtocol");

        Boolean sensorEnable = androidOption.getBoolean("sensorEnable");

        Boolean wifiScan = androidOption.getBoolean("wifiScan");

        Boolean locationCacheEnable = androidOption.getBoolean("locationCacheEnable");

        switch (locationMode) {
            case 1:
                locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度模式
                break;
            case 2:
                locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);//
                break;
            case 3:
                locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);//
                break;
        }

        switch (locationProtocol) {
            case 1:
                AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
                break;
            case 2:
                AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
                break;
        }

        locationOption.setGpsFirst(gpsFirst);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        locationOption.setHttpTimeOut(HttpTimeOut);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        locationOption.setInterval(interval);//可选，设置定位间隔。默认为2秒
        locationOption.setNeedAddress(needAddress);//可选，设置是否返回逆地理地址信息。默认是true
        locationOption.setOnceLocation(onceLocation);//可选，设置是否单次定位。默认是false
        locationOption.setOnceLocationLatest(onceLocationLatest);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setSensorEnable(sensorEnable);//可选，设置是否使用传感器。默认是false
        locationOption.setWifiScan(wifiScan); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationOption.setLocationCacheEnable(locationCacheEnable); //可选，设置是否使用缓存定位，默认为true
//        locationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
    }

    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            try {
                SerialLocationInfo(location);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 获取GPS状态的字符串
     *
     * @param statusCode GPS状态码
     * @return
     */
    private String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void startLocation(JSONObject message) throws JSONException {
       this.setLocationOption(message);
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }


    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    public void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    private NotificationManager notificationManager = null;
    boolean isCreateChannel = false;

    private static final String NOTIFICATION_CHANNEL_NAME = "BackgroundLocation";

    @SuppressLint("NewApi")
    public Notification buildNotification() {

        Notification.Builder builder = null;
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
            if (null == notificationManager) {
                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            }
            String channelId = mContext.getPackageName();
            if (!isCreateChannel) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId,
                        NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.enableLights(true);//是否在桌面icon右上角展示小圆点
                notificationChannel.setLightColor(Color.BLUE); //小圆点颜色
                notificationChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                notificationManager.createNotificationChannel(notificationChannel);
                isCreateChannel = true;
            }
            builder = new Notification.Builder(mContext, channelId);
        } else {
            builder = new Notification.Builder(mContext);
        }
        builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getAppName(mContext))
                .setContentText("正在后台运行")
                .setWhen(System.currentTimeMillis());

        if (android.os.Build.VERSION.SDK_INT >= 16) {
            notification = builder.build();
        } else {
            return builder.getNotification();
        }
        return notification;
    }

    public String getAppName(Context context) {
        String appName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return appName;
    }

    @Override
    public void setSingleLocaitonDelegate(SerialLocationDelegate serialLocationDelegate) {
        if (l==null) {
            throw new NullPointerException();
        }
        else {
            if (!l.contains(serialLocationDelegate)) {
                l.add(serialLocationDelegate);
            }
        }
    }

    @Override
    public void removeAll() {
        l.clear();
    }

    @Override
    public void SerialLocationInfo(AMapLocation location) throws JSONException {
        for(SerialLocationDelegate o:l) {
            o.SerialLocationInfo(location);
        }
    }
}
