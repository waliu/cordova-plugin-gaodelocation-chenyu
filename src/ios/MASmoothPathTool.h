//
//  MASmoothPathTool.h
//  iOS-path-smooth
//
//  Created by shaobin on 2017/10/12.
//  Copyright © 2017年 autonavi. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MALonLatPoint : NSObject
@property (nonatomic, assign) double lat;
@property (nonatomic, assign) double lon;
@end

@interface MASmoothPathTool : NSObject

@property (nonatomic, assign) int intensity;
@property (nonatomic, assign) float threshHold;
@property (nonatomic, assign) int noiseThreshhold;

/**
 * 轨迹平滑优化
 * @param originlist 原始轨迹list,list.size大于2
 * @return 优化后轨迹list
 */
- (NSArray<MALonLatPoint*>*)pathOptimize:(NSArray<MALonLatPoint*>*)originlist;

/**
 * 轨迹线路滤波
 * @param originlist 原始轨迹list,list.size大于2
 * @return 滤波处理后的轨迹list
 */
- (NSArray<MALonLatPoint*>*)kalmanFilterPath:(NSArray<MALonLatPoint*>*)originlist;


/**
 * 轨迹去噪，删除垂距大于20m的点
 * @param originlist 原始轨迹list,list.size大于2
 * @return 去燥后的list
 */
- (NSArray<MALonLatPoint*>*)removeNoisePoint:(NSArray<MALonLatPoint*>*)originlist;

/**
 * 单点滤波
 * @param lastLoc 上次定位点坐标
 * @param curLoc 本次定位点坐标
 * @return 滤波后本次定位点坐标值
 */
- (MALonLatPoint*)kalmanFilterPoint:(MALonLatPoint*)lastLoc curLoc:(MALonLatPoint*)curLoc;

/**
 * 轨迹抽稀
 * @param inPoints 待抽稀的轨迹list，至少包含两个点，删除垂距小于mThreshhold的点
 * @return 抽稀后的轨迹list
 */
- (NSArray<MALonLatPoint*>*)reducerVerticalThreshold:(NSArray<MALonLatPoint*>*)inPoints;

@end
