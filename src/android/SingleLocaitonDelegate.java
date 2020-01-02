package com.chenyu.GaoDeLocation;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;

public interface SingleLocaitonDelegate {
  void PositionInfo(AMapLocation location) throws JSONException;
}
