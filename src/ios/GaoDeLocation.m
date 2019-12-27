/********* GaoDeLocation.m Cordova Plugin Implementation *******/

#import "GaoDeLocation.h"

@implementation GaoDeLocation
- (void)pluginInitialize {
    self.IOS_API_KEY = [self.commandDelegate settings][@"ios_api_key"];

    [AMapServices sharedServices].apiKey = self.IOS_API_KEY;

    self.singleLocaiton = [SingleLocaiton alloc].init;

    self.singleLocaiton.delegate = self;
}

/*
 * 单次定位
 *
 */
- (void)getCurrentPosition:(CDVInvokedUrlCommand *)command {
    [self.singleLocaiton locAction];
}

#pragma mark - SingleLocaiton Delegate

- (void)PositionInfo:(CLLocation *)location Regeocode:(AMapLocationReGeocode *)regeocode {
    NSLog(@"收到消息");
    NSLog(@"location:%@", location);
    NSLog(@"regeocode:%@", regeocode);
}


@end
