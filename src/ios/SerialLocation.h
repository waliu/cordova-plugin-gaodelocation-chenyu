#import <AMapLocationKit/AMapLocationKit.h>

@interface SerialLocation : NSObject <AMapLocationManagerDelegate> {
}
@property(nonatomic, strong) AMapLocationManager *locationManager;

- (void)startSerialLocation;

- (void)stopSerialLocation;

@end


