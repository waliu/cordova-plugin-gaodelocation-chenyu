#import <AMapLocationKit/AMapLocationKit.h>

@protocol SerialLocationDelegate  //委托协议的声明

- (void)SerialLocationInfo:(CLLocation *)location Regeocode:(AMapLocationReGeocode *)regeocode;

@end

@interface SerialLocation : NSObject <AMapLocationManagerDelegate> {
}
@property(assign, nonatomic) id <SerialLocationDelegate> delegate;

@property(nonatomic, strong) AMapLocationManager *locationManager;

- (void)startSerialLocation:(NSMutableDictionary *)message;

- (void)stopSerialLocation;

@end


