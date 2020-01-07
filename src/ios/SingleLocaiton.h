#import <AMapLocationKit/AMapLocationKit.h>
#import <Cordova/CDVInvokedUrlCommand.h>

#define DefaultLocationTimeout 10
#define DefaultReGeocodeTimeout 5

@protocol SingleLocaitonDelegate  //委托协议的声明

- (void)PositionInfo:(CLLocation *)location Regeocode:(AMapLocationReGeocode *)regeocode;

- (void)Erro:(NSError *)error;

@end

@interface SingleLocaiton : NSObject <AMapLocationManagerDelegate> {
//    id <Mydelegate> delegate;
}

@property(assign, nonatomic) id <SingleLocaitonDelegate> delegate;

@property(nonatomic, copy) AMapLocatingCompletionBlock completionBlock;

@property(nonatomic, strong) AMapLocationManager *locationManager;

- (id)init;


- (void)getCurrentPosition:(NSMutableDictionary *)message;

@end;
