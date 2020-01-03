package com.chenyu.GaoDeLocation;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;

public interface SerialLocationDelegate {
    void SerialLocationInfo(AMapLocation location) throws JSONException;
}
