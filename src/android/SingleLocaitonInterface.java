package com.chenyu.GaoDeLocation;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;

public interface SingleLocaitonInterface {


  void setSingleLocaitonDelegate(SingleLocaitonDelegate singleLocaitonDelegate);

  /**
   * 移除所有对象
   */
  void removeAll();


  void PositionInfo(AMapLocation location) throws JSONException;


}
