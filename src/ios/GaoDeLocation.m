/********* GaoDeLocation.m Cordova Plugin Implementation *******/

#import "GaoDeLocation.h"
#import "SerialLocation.h"

@implementation GaoDeLocation
- (void)pluginInitialize {
    self.IOS_API_KEY = [self.commandDelegate settings][@"ios_api_key"];

    [AMapServices sharedServices].apiKey = self.IOS_API_KEY;

    self.singleLocaiton = [SingleLocaiton alloc].init;

    self.singleLocaiton.delegate = self;

    self.serialLocation=[SerialLocation alloc].init;
}


/*
 * 单次定位
 *
 */
- (void)getCurrentPosition:(CDVInvokedUrlCommand *)command {
    [self.singleLocaiton locAction];
}

-(void)startSerialLocation:(CDVInvokedUrlCommand *)command{
    [self.serialLocation startSerialLocation];
}

- (void)stopSerialLocation:(CDVInvokedUrlCommand *)command {
    [self.serialLocation stopSerialLocation];
}


#pragma mark - SingleLocaiton Delegate

- (void)PositionInfo:(CLLocation *)location Regeocode:(AMapLocationReGeocode *)regeocode {
    NSLog(@"收到消息");
    NSLog(@"location:%@", location);
    NSLog(@"regeocode:%@", regeocode);
}


@end
