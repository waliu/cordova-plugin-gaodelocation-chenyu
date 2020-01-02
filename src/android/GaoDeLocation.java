package com.chenyu.GaoDeLocation;
import com.amap.api.location.AMapLocation;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GaoDeLocation extends CordovaPlugin implements SingleLocaitonDelegate {

    private SingleLocaiton singleLocaiton;

    public static CallbackContext singleLocaitonCC = null;
    /*
     * 程序入口
     * */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getCurrentPosition")) {
            this.startLocation( callbackContext);
            return true;
        }
        return false;
    }

    protected void pluginInitialize() {
        this.singleLocaiton = new SingleLocaiton(cordova.getContext());

        this.singleLocaiton.addObserver(this);

    }

    public void startLocation(CallbackContext callbackContext) {
        this.singleLocaiton.startLocation();
        singleLocaitonCC = callbackContext;
    }

    @Override
    public void PositionInfo(AMapLocation location) throws JSONException{
        System.out.println("收到消息");

        if (null != location) {
            JSONObject json = new JSONObject();
            // 和ios 公用属性
            //插件是否成功
            json.put("code", "200");

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
            json.put("speed",location.getSpeed());
            //方向角
            json.put("bearing",location.getBearing());
            //室内定位建筑物Id
            json.put("buildingId",location.getBuildingId());
            //楼层
            json.put("floor",location.getFloor());
            //设备当前 GPS 状态
            json.put("gpsAccuracyStatus",location.getGpsAccuracyStatus());
            //定位来源
            json.put("locationType",location.getLocationType());
            //定位信息描述
            json.put("locationDetail",location.getLocationDetail());

            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, json);

            pluginResult.setKeepCallback(true);

            singleLocaitonCC.sendPluginResult(pluginResult);

        } else {
            JSONObject json = new JSONObject();

            json.put("code", "500");

            json.put("errorCode",location.getErrorCode());

            json.put("errorInfo",location.getErrorInfo());

            PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, json);

            pluginResult.setKeepCallback(true);

            singleLocaitonCC.sendPluginResult(pluginResult);

        }


    }
}
