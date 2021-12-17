package com.chenyu.GaoDeLocation;

import android.Manifest;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GaoDeLocation extends CordovaPlugin implements SingleLocaitonDelegate, SerialLocationDelegate {

  private String TAG = "CordovaPlugin.GaoDeLocation";

  private SingleLocaiton singleLocaiton;

  private SerialLocation serialLocation;

  public static CallbackContext singleLocaitonCC = null;

  public static CallbackContext serialLocationCC = null;

  public boolean serialLocationIng = false;

  //权限申请码
  private static final int PERMISSION_REQUEST_CODE = 500;

  protected String[] needPermissions = {
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.READ_PHONE_STATE
  };

  /*
   * 程序入口
   * */
  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("getCurrentPosition")) {
      JSONObject message = args.getJSONObject(0);
      this.singleLocaiton(callbackContext, message);
      return true;
    }
    if (action.equals("startSerialLocation")) {
      JSONObject message = args.getJSONObject(0);
      this.startSerialLocation(callbackContext, message);
      return true;
    }
    if (action.equals("stopSerialLocation")) {
      this.stopSerialLocation(callbackContext);
      return true;
    }
    return false;
  }

  protected void pluginInitialize() {
    try {
      cordova.requestPermissions(this, PERMISSION_REQUEST_CODE, needPermissions);

      AMapLocationClient.updatePrivacyShow(cordova.getContext(), true, true);

      AMapLocationClient.updatePrivacyAgree(cordova.getContext(), true);

      this.singleLocaiton = new SingleLocaiton(cordova.getContext());

      this.singleLocaiton.setSingleLocaitonDelegate(this);

      this.serialLocation = new SerialLocation(cordova.getContext());

      this.serialLocation.setSingleLocaitonDelegate(this);

    } catch (Exception e) {
      LOG.d(TAG, "插件初始化失败！");
      e.printStackTrace();
    }

  }

  /**
   * 调用单次定位
   *
   * @param callbackContext
   * @param message
   */

  public void singleLocaiton(CallbackContext callbackContext, JSONObject message) throws JSONException {
    try {
      this.checkPermissions(callbackContext);
      this.singleLocaiton.startLocation(message);
      singleLocaitonCC = callbackContext;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 调用持续定位
   *
   * @param callbackContext
   * @param message
   */

  public void startSerialLocation(CallbackContext callbackContext, JSONObject message) throws JSONException {
    try {
      this.checkPermissions(callbackContext);
      this.serialLocation.startLocation(message);
      serialLocationCC = callbackContext;
      this.serialLocationIng = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 停止定位
   *
   * @param callbackContext
   */

  public void stopSerialLocation(CallbackContext callbackContext) throws JSONException {

    this.serialLocation.stopLocation();

    this.serialLocationIng = false;

    JSONObject json = new JSONObject();

    callbackContext.success(json);
  }

  /**
   * 实现销毁对象的方法
   */
  private void destroyLocation() {
    if (null != this.serialLocation.locationClient) {
      /**
       * 如果AMapLocationClient是在当前Activity实例化的，
       * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
       */
      this.serialLocation.locationClient.disableBackgroundLocation(true);
      this.serialLocation.locationClient.onDestroy();
      this.serialLocation.locationClient = null;
      this.serialLocation.locationOption = null;
    }
  }

  /**
   * 销毁 定位 对象
   */

  public void onDestroy() {
    destroyLocation();
  }

  /**
   * 进入页面时 调用
   *
   * @param multitasking Flag indicating if multitasking is turned on for app
   */
  public void onPause(boolean multitasking) {
    if (null != this.serialLocation.locationClient && this.serialLocationIng) {
      this.serialLocation.locationClient.enableBackgroundLocation(2001, this.serialLocation.buildNotification());
    }
  }

  /**
   * 进入后台时 调用
   *
   * @param multitasking Flag indicating if multitasking is turned on for app
   */

  public void onResume(boolean multitasking) {
    if (null != this.serialLocation.locationClient) {
      this.serialLocation.locationClient.disableBackgroundLocation(true);
    }
  }


  private void checkPermissions(CallbackContext callbackContext) throws Exception {
    for (String perm : needPermissions) {
      if (!cordova.hasPermission(perm)) {
        JSONObject errorJson = new JSONObject();
        errorJson.put("code", "500");
        errorJson.put("errorInfo", "权限不足,无法定位");
        callbackContext.error(errorJson);
        throw new Exception("权限不足,无法定位");
      }
    }
  }

  /**
   * 实现单次定位 委托
   *
   * @param location
   * @throws JSONException
   */
  @Override
  public void PositionInfo(AMapLocation location) throws JSONException {
    sendPositionInfo(location, singleLocaitonCC);
  }

  /**
   * 实现持续定位 委托
   *
   * @param location
   * @throws JSONException
   */

  @Override
  public void SerialLocationInfo(AMapLocation location) throws JSONException {
    sendPositionInfo(location, serialLocationCC);
  }

  /**
   * 返回定位 json  到js
   *
   * @param location
   * @throws JSONException
   */
  public void sendPositionInfo(AMapLocation location, CallbackContext c) throws JSONException {
    if (null != location) {
      JSONObject json = new JSONObject();
      // 和ios 公用属性
      //插件是否成功

      json.put("latitude", location.getLatitude());

      json.put("longitude", location.getLongitude());

      json.put("accuracy", location.getAccuracy());

      json.put("formattedAddress", location.getAddress());

      json.put("country", location.getCountry());

      json.put("province", location.getProvince());

      json.put("city", location.getCity());

      json.put("district", location.getDistrict());

      json.put("citycode", location.getCityCode());

      json.put("adcode", location.getAdCode());

      json.put("street", location.getStreet());

      json.put("number", location.getStreetNum());

      json.put("POIName", location.getPoiName());

      json.put("AOIName", location.getAoiName());

      //android 特有 属性
      //海拔
      json.put("altitude", location.getAltitude());
      //速度
      json.put("speed", location.getSpeed());
      //方向角
      json.put("bearing", location.getBearing());
      //室内定位建筑物Id
      json.put("buildingId", location.getBuildingId());
      //楼层
      json.put("floor", location.getFloor());
      //设备当前 GPS 状态
      json.put("gpsAccuracyStatus", location.getGpsAccuracyStatus());
      //定位来源
      json.put("locationType", location.getLocationType());
      //定位信息描述
      json.put("locationDetail", location.getLocationDetail());

      PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, json);

      pluginResult.setKeepCallback(true);

      c.sendPluginResult(pluginResult);

    } else {
      JSONObject json = new JSONObject();

      json.put("code", "500");

      json.put("errorCode", location.getErrorCode());

      json.put("errorInfo", location.getErrorInfo());

      PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, json);

      pluginResult.setKeepCallback(true);

      c.sendPluginResult(pluginResult);

    }
  }
}
