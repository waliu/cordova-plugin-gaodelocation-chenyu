#import <AMapLocationKit/AMapLocationKit.h>

#define DefaultLocationTimeout 10
#define DefaultReGeocodeTimeout 5

@protocol SingleLocaitonDelegate  //委托协议的声明

- (void)PositionInfo:(CLLocation *)location Regeocode:(AMapLocationReGeocode *)regeocode;

@end

@interface SingleLocaiton : NSObject <AMapLocationManagerDelegate> {
//    id <Mydelegate> delegate;
}

@property(assign, nonatomic) id <SingleLocaitonDelegate> delegate;

@property(nonatomic, copy) AMapLocatingCompletionBlock completionBlock;

@property(nonatomic, strong) AMapLocationManager *locationManager;

- (id)init;

- (void)locAction;

- (void)reGeocodeAction;

@end;
