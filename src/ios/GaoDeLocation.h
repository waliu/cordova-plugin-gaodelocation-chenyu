#import <Cordova/CDV.h>
#import <AMapFoundationKit/AMapFoundationKit.h>
#import <AMapLocationKit/AMapLocationKit.h>
#import "SingleLocaiton.h"
#import "SerialLocation.h"

@interface GaoDeLocation : CDVPlugin <SingleLocaitonDelegate,SerialLocationDelegate> {
    // Member variables go here.
}

@property(nonatomic, copy) AMapLocatingCompletionBlock completionBlock;

@property(nonatomic, strong) NSString *IOS_API_KEY;

@property(nonatomic, strong) SingleLocaiton *singleLocaiton;

@property(nonatomic, strong) SerialLocation *serialLocation;

@property(nonatomic, strong) NSString *singleCallbackId;

@property(nonatomic, strong) NSString *serialCallbackId;

- (void)getCurrentPosition:(CDVInvokedUrlCommand *)command;

- (void)startSerialLocation:(CDVInvokedUrlCommand *)command;

- (void)stopSerialLocation:(CDVInvokedUrlCommand *)command;

@end