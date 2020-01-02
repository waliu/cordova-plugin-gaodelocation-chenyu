package com.chenyu.GaoDeLocation;

import com.amap.api.location.AMapLocation;

import org.json.JSONException;

public interface SingleLocaitonInterface {


  void addObserver(SingleLocaitonDelegate singleLocaitonDelegate);

  /**
   * 移除所有对象
   */
  void removeAll();


  void PositionInfo(AMapLocation location) throws JSONException;


  void eventAll(SingleLocaitonInterface s,SingleLocaitonDelegate obj,Object o);

}
