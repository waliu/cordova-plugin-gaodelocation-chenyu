package com.chenyu.GaoDeLocation;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;

public interface SerialLocationInterface {

    void setSingleLocaitonDelegate(SerialLocationDelegate serialLocationDelegate);

    /**
     * 移除所有对象
     */
    void removeAll();


    void SerialLocationInfo(AMapLocation location) throws JSONException;
}
