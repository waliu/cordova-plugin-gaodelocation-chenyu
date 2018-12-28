package com.chenyu.GaoDeLocation;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

public class GaoDeLocation extends CordovaPlugin {
    //声明AMapLocationClient类对象
    public AMapLocationClient locationClient = null;
    //声明定位参数
    public AMapLocationClientOption locationOption = null;

    //权限申请码
    private static final int PERMISSION_REQUEST_CODE = 500;

    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int PERMISSON_REQUEST_CODE = 0;

    /**
     * JS回调接口对象
     */
    public static CallbackContext cb = null;

    /*
    * 程序入口
    * */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCurrentPosition")) {
            cb = callbackContext;
            PluginResult pluginResult = new PluginResult(PluginResult.Status.NO_RESULT);
            pluginResult.setKeepCallback(true);
            cb.sendPluginResult(pluginResult);
            if(this.isNeedCheckPermissions(needPermissions)){
                this.checkPermissions(needPermissions);
            }else{
                this.getCurrentPosition();
            }

            return true;
        }
        return false;
    }


    /**
     * 获取定位
     *
     */
    private void getCurrentPosition() {
        if (locationClient == null) {
            this.initLocation();
        }
        this.startLocation();
    }


    /**
     * 初始化定位
     *
     */
    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(this.webView.getContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);

    }

    /**
     * 默认的定位参数
     *
     * @author zhaoying
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
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
                JSONObject json = new JSONObject();
                if (null != location) {
                    //解析定位结果
                    //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                    if (location.getErrorCode() == 0) {
                        json.put("status", "定位成功");
                        //定位类型
                        json.put("type", location.getLocationType());
                        //纬度
                        json.put("latitude", location.getLatitude());
                        //经度
                        json.put("longitude", location.getLongitude());
                        //精度
                        json.put("accuracy", location.getAccuracy());
                        //角度
                        json.put("bearing", location.getBearing());
                        // 获取当前提供定位服务的卫星个数
                        //星数
                        json.put("satellites", location.getSatellites());
                        //国家
                        json.put("country", location.getCountry());
                        //省
                        json.put("province", location.getProvince());
                        //市
                        json.put("city", location.getCity());
                        //城市编码
                        json.put("citycode", location.getCityCode());
                        //区
                        json.put("district", location.getDistrict());
                        //区域码
                        json.put("adcode", location.getAdCode());
                        //地址
                        json.put("address", location.getAddress());
                        //兴趣点
                        json.put("poi", location.getPoiName());
                        //兴趣点
                        json.put("time", location.getTime());
                    } else {
                        json.put("status", "定位失败");
                        json.put("errcode", location.getErrorCode());
                        json.put("errinfo", location.getErrorInfo());
                        json.put("detail", location.getLocationDetail());
                    }
                    //定位之后的回调时间
                    json.put("backtime", System.currentTimeMillis());
                } else {

                }
                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, json);
                pluginResult.setKeepCallback(true);
                cb.sendPluginResult(pluginResult);
            } catch (JSONException e) {
                PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, e.getMessage());
                pluginResult.setKeepCallback(true);
                cb.sendPluginResult(pluginResult);
            } finally {
                locationClient.stopLocation();
            }
        }
    };

    /**
     * 开始定位
     *
     */
    private void startLocation() {
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     */
    private void stopLocation() {
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    /**
     * 判断是否需要检查权限
     *
     */

    private boolean isNeedCheckPermissions(String... permissions) {
        List<String> needRequestPermissonList = findNeedPermissions(permissions);
        if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查权限
     *
     */
    private void checkPermissions(String... permissions) {
        try {
            List<String> needRequestPermissonList = findNeedPermissions(permissions);
            if (null != needRequestPermissonList && needRequestPermissonList.size() > 0) {
                String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                cordova.requestPermissions(this, PERMISSION_REQUEST_CODE, array);
            }
        } catch (Throwable e) {

        }
    }

    /**
     * 获取需要获取权限的集合
     *
     */
    private List<String> findNeedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        try {
            for (String perm : permissions) {
                if (!cordova.hasPermission(perm)) {
                    needRequestPermissonList.add(perm);
                }
            }
        } catch (Throwable e) {

        }
        return needRequestPermissonList;
    }

    /*
    * 权限检查回调
    *
    * @author
    * */
    public void onRequestPermissionResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            this.getCurrentPosition();
        }
    }
}
