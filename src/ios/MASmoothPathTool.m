//
//  MASmoothPathTool.m
//  iOS-path-smooth
//
//  Created by shaobin on 2017/10/12.
//  Copyright © 2017年 autonavi. All rights reserved.
//

#import "MASmoothPathTool.h"
#import <MAMapKit/MAMapKit.h>

@implementation MALonLatPoint
@end

@implementation MASmoothPathTool
{
    double lastLocation_x; //上次位置
    double currentLocation_x;//这次位置
    double lastLocation_y; //上次位置
    double currentLocation_y;//这次位置
    double estimate_x; //修正后数据
    double estimate_y; //修正后数据
    double pdelt_x; //自预估偏差
    double pdelt_y; //自预估偏差
    double mdelt_x; //上次模型偏差
    double mdelt_y; //上次模型偏差
    double gauss_x; //高斯噪音偏差
    double gauss_y; //高斯噪音偏差
    double kalmanGain_x; //卡尔曼增益
    double kalmanGain_y; //卡尔曼增益

    double m_R;
    double m_Q;
}

- (id)init {
    self = [super init];

    if(self) {
        self.intensity = 3;
        self.threshHold = 0.3f;
        self.noiseThreshhold = 10;
    }

    return self;
}
/**
 * 轨迹平滑优化
 * @param originlist 原始轨迹list,list.size大于2
 * @return 优化后轨迹list
 */
- (NSArray<MALonLatPoint*>*)pathOptimize:(NSArray<MALonLatPoint*>*)originlist {

    NSArray<MALonLatPoint*>* list = [self removeNoisePoint:originlist];//去噪
    NSArray<MALonLatPoint*>* afterList = [self kalmanFilterPath:list intensity:self.intensity];//滤波
    NSArray<MALonLatPoint*>* pathoptimizeList = [self reducerVerticalThreshold:afterList threshHold:self.threshHold];//抽稀
    return pathoptimizeList;
}

/**
 * 轨迹线路滤波
 * @param originlist 原始轨迹list,list.size大于2
 * @return 滤波处理后的轨迹list
 */
- (NSArray<MALonLatPoint*>*)kalmanFilterPath:(NSArray<MALonLatPoint*>*)originlist {
    return [self kalmanFilterPath:originlist intensity:self.intensity];
}


/**
 * 轨迹去噪，删除垂距大于20m的点
 * @param originlist 原始轨迹list,list.size大于2
 * @return 去燥后的list
 */
- (NSArray<MALonLatPoint*>*)removeNoisePoint:(NSArray<MALonLatPoint*>*)originlist{
    return [self reduceNoisePoint:originlist threshHold:self.noiseThreshhold];
}

/**
 * 单点滤波
 * @param lastLoc 上次定位点坐标
 * @param curLoc 本次定位点坐标
 * @return 滤波后本次定位点坐标值
 */
- (MALonLatPoint*)kalmanFilterPoint:(MALonLatPoint*)lastLoc curLoc:(MALonLatPoint*)curLoc {
    return [self kalmanFilterPoint:lastLoc curLoc:curLoc intensity:self.intensity];
}

/**
 * 轨迹抽稀
 * @param inPoints 待抽稀的轨迹list，至少包含两个点，删除垂距小于mThreshhold的点
 * @return 抽稀后的轨迹list
 */
- (NSArray<MALonLatPoint*>*)reducerVerticalThreshold:(NSArray<MALonLatPoint*>*)inPoints {
    return [self reducerVerticalThreshold:inPoints threshHold:self.threshHold];
}

/********************************************************************************************************/
/**
 * 轨迹线路滤波
 * @param originlist 原始轨迹list,list.size大于2
 * @param intensity 滤波强度（1—5）
 * @return 滤波后的list
 */
- (NSArray<MALonLatPoint*>*)kalmanFilterPath:(NSArray<MALonLatPoint*>*)originlist intensity:(int)intensity {
    if (!originlist || originlist.count <= 2) {
        return nil;
    }

    NSMutableArray<MALonLatPoint*>* kalmanFilterList = [NSMutableArray array];

    [self initial];//初始化滤波参数

    MALonLatPoint* point = nil;
    MALonLatPoint* lastLoc = [[MALonLatPoint alloc] init];
    lastLoc.lat = [originlist objectAtIndex:0].lat;
    lastLoc.lon = [originlist objectAtIndex:0].lon;
    [kalmanFilterList addObject:lastLoc];

    for (int i = 1; i < originlist.count; i++) {
        MALonLatPoint* curLoc = [originlist objectAtIndex:i];
        point = [self kalmanFilterPoint:lastLoc curLoc:curLoc intensity:intensity];
        if (point) {
            [kalmanFilterList addObject:point];
            lastLoc = point;
        }
    }
    return kalmanFilterList;
}

/**
 * 单点滤波
 * @param lastLoc 上次定位点坐标
 * @param curLoc 本次定位点坐标
 * @param intensity 滤波强度（1—5）
 * @return 滤波后本次定位点坐标值
 */
- (MALonLatPoint*)kalmanFilterPoint:(MALonLatPoint*)lastLoc curLoc:(MALonLatPoint*)curLoc intensity:(int)intensity {
    if (!lastLoc || !curLoc){
        return nil;
    }

    if (pdelt_x == 0 || pdelt_y == 0 ){
        [self initial];
    }

    MALonLatPoint* point = nil;
    if (intensity < 1){
        intensity = 1;
    } else if (intensity > 5){
        intensity = 5;
    }
    for (int j = 0; j < intensity; j++){
        point = [self kalmanFilter:lastLoc.lon value_x:curLoc.lon oldValue_y:lastLoc.lat value_y:curLoc.lat];
        curLoc = point;
    }
    return point;
}


/***************************卡尔曼滤波开始********************************/

//初始模型
- (void)initial {
    pdelt_x =  0.001;
    pdelt_y =  0.001;
    //        mdelt_x = 0;
    //        mdelt_y = 0;
    mdelt_x =  5.698402909980532E-4;
    mdelt_y =  5.698402909980532E-4;
}

- (MALonLatPoint*)kalmanFilter:(double)oldValue_x value_x:(double)value_x oldValue_y:(double)oldValue_y value_y:(double)value_y{
    lastLocation_x = oldValue_x;
    currentLocation_x= value_x;

    gauss_x = sqrt(pdelt_x * pdelt_x + mdelt_x * mdelt_x)+m_Q;     //计算高斯噪音偏差
    kalmanGain_x = sqrt((gauss_x * gauss_x)/(gauss_x * gauss_x + pdelt_x * pdelt_x)) +m_R; //计算卡尔曼增益
    estimate_x = kalmanGain_x * (currentLocation_x - lastLocation_x) + lastLocation_x;    //修正定位点
    mdelt_x = sqrt((1-kalmanGain_x) * gauss_x *gauss_x);      //修正模型偏差

    lastLocation_y = oldValue_y;
    currentLocation_y = value_y;
    gauss_y = sqrt(pdelt_y * pdelt_y + mdelt_y * mdelt_y)+m_Q;     //计算高斯噪音偏差
    kalmanGain_y = sqrt((gauss_y * gauss_y)/(gauss_y * gauss_y + pdelt_y * pdelt_y)) +m_R; //计算卡尔曼增益
    estimate_y = kalmanGain_y * (currentLocation_y - lastLocation_y) + lastLocation_y;    //修正定位点
    mdelt_y = sqrt((1-kalmanGain_y) * gauss_y * gauss_y);      //修正模型偏差

    MALonLatPoint *point = [[MALonLatPoint alloc] init];
    point.lon = estimate_x;
    point.lat = estimate_y;

    return point;
}
/***************************卡尔曼滤波结束**********************************/

/***************************抽稀算法*************************************/
- (NSArray<MALonLatPoint*>*)reducerVerticalThreshold:(NSArray<MALonLatPoint*>*)inPoints threshHold:(float)threshHold {
    if(inPoints.count < 2) {
        return inPoints;
    }

    NSMutableArray *ret = [NSMutableArray arrayWithCapacity:inPoints.count];

    for(int i = 0; i < inPoints.count; ++i) {
        MALonLatPoint *pre = ret.lastObject;
        MALonLatPoint *cur = [inPoints objectAtIndex:i];


        if (!pre || i == inPoints.count - 1) {
            [ret addObject:[inPoints objectAtIndex:i]];
            continue;
        }

        MALonLatPoint *next = [inPoints objectAtIndex:(i + 1)];

        MAMapPoint curP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(cur.lat, cur.lon));
        MAMapPoint prevP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(pre.lat, pre.lon));
        MAMapPoint nextP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(next.lat, next.lon));
        double distance = [self calculateDistanceFromPoint:curP lineBegin:prevP lineEnd:nextP];
        if (distance >= threshHold) {
            [ret addObject:cur];
        }
    }

    return ret;
}

- (MALonLatPoint*)getLastLocation:(NSArray<MALonLatPoint*>*)oneGraspList {
    if (!oneGraspList || oneGraspList.count == 0) {
        return nil;
    }
    NSInteger locListSize = oneGraspList.count;
    MALonLatPoint* lastLocation = [oneGraspList objectAtIndex:(locListSize - 1)];
    return lastLocation;
}

/**
 * 计算当前点到线的垂线距离
 * @param pt 当前点
 * @param begin 线的起点
 * @param end 线的终点
 *
 */
- (double)calculateDistanceFromPoint:(MAMapPoint)pt
                           lineBegin:(MAMapPoint)begin
                             lineEnd:(MAMapPoint)end {

    MAMapPoint mappedPoint;
    double dx = begin.x - end.x;
    double dy = begin.y - end.y;
    if(fabs(dx) < 0.00000001 && fabs(dy) < 0.00000001 ) {
        mappedPoint = begin;
    } else {
        double u = (pt.x - begin.x)*(begin.x - end.x) +
                (pt.y - begin.y)*(begin.y - end.y);
        u = u/((dx*dx)+(dy*dy));

        mappedPoint.x = begin.x + u*dx;
        mappedPoint.y = begin.y + u*dy;
    }

    return MAMetersBetweenMapPoints(pt, mappedPoint);
}
/***************************抽稀算法结束*********************************/

- (NSArray<MALonLatPoint*>*)reduceNoisePoint:(NSArray<MALonLatPoint*>*)inPoints threshHold:(float)threshHold {
    if (!inPoints) {
        return nil;
    }
    if (inPoints.count <= 2) {
        return inPoints;
    }

    NSMutableArray<MALonLatPoint*>* ret = [NSMutableArray array];
    for (int i = 0; i < inPoints.count; i++) {
        MALonLatPoint* pre = [self getLastLocation:ret];
        MALonLatPoint* cur = [inPoints objectAtIndex:i];
        if (!pre || i == inPoints.count - 1) {
            [ret addObject:cur];
            continue;
        }
        MALonLatPoint* next = [inPoints objectAtIndex:(i + 1)];

        MAMapPoint curP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(cur.lat, cur.lon));
        MAMapPoint prevP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(pre.lat, pre.lon));
        MAMapPoint nextP = MAMapPointForCoordinate(CLLocationCoordinate2DMake(next.lat, next.lon));
        double distance = [self calculateDistanceFromPoint:curP lineBegin:prevP lineEnd:nextP];
        if (distance < threshHold){
            [ret addObject:cur];
        }
    }
    return ret;
}


@end
