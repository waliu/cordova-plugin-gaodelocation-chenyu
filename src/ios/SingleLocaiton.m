#import "SingleLocaiton.h"

@implementation SingleLocaiton

- (void)cleanUpAction {
    //停止定位
    [self.locationManager stopUpdatingLocation];

    [self.locationManager setDelegate:nil];
}

- (void)getCurrentPosition:(NSMutableDictionary *)message {
    [self setLocationOption:message];
    //进行单次带逆地理定位请求
}

- (id)init {

    [self configLocationManager];

    [self initCompleteBlock];

    return self;
}

- (void)configLocationManager {
    self.locationManager = [[AMapLocationManager alloc] init];

    [self.locationManager setDelegate:self];

    //设置期望定位精度
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];

    //设置不允许系统暂停定位
    [self.locationManager setPausesLocationUpdatesAutomatically:NO];

    //设置允许在后台定位
    [self.locationManager setAllowsBackgroundLocationUpdates:YES];

    //设置定位超时时间
    [self.locationManager setLocationTimeout:DefaultLocationTimeout];

    //设置逆地理超时时间
    [self.locationManager setReGeocodeTimeout:DefaultReGeocodeTimeout];

}


- (void)locAction {
    //进行单次定位请求
    [self.locationManager requestLocationWithReGeocode:NO completionBlock:self.completionBlock];
}


#pragma mark - Initialization

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Warc-retain-cycles"
- (void)initCompleteBlock {
    self.completionBlock = ^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
        if (error != nil && error.code == AMapLocationErrorLocateFailed) {
            //定位错误：此时location和regeocode没有返回值，不进行annotation的添加
            NSLog(@"定位错误:{%ld - %@};", (long) error.code, error.localizedDescription);
            return;
        } else if (error != nil
                && (error.code == AMapLocationErrorReGeocodeFailed
                || error.code == AMapLocationErrorTimeOut
                || error.code == AMapLocationErrorCannotFindHost
                || error.code == AMapLocationErrorBadURL
                || error.code == AMapLocationErrorNotConnectedToInternet
                || error.code == AMapLocationErrorCannotConnectToHost)) {
            //逆地理错误：在带逆地理的单次定位中，逆地理过程可能发生错误，此时location有返回值，regeocode无返回值，进行annotation的添加
            NSLog(@"逆地理错误:{%ld - %@};", (long) error.code, error.localizedDescription);
        } else if (error != nil && error.code == AMapLocationErrorRiskOfFakeLocation) {
            //存在虚拟定位的风险：此时location和regeocode没有返回值，不进行annotation的添加
            NSLog(@"存在虚拟定位的风险:{%ld - %@};", (long) error.code, error.localizedDescription);
            return;
        } else {
            //没有错误：location有返回值，regeocode是否有返回值取决于是否进行逆地理操作，进行annotation的添加
        }
        [self.delegate PositionInfo:location Regeocode:regeocode];
    };
}


- (void)setLocationOption:(NSMutableDictionary *)message{
    NSMutableDictionary *iosOption=message[@"iosOption"];
    NSInteger desiredAccuracy= [iosOption[@"desiredAccuracy"] integerValue];
    BOOL pausesLocationUpdatesAutomatically= [iosOption[@"pausesLocationUpdatesAutomatically"] boolValue];
    BOOL allowsBackgroundLocationUpdates= [iosOption[@"allowsBackgroundLocationUpdates"] boolValue];
    NSInteger locationTimeout= [iosOption[@"locationTimeout"] integerValue];
    NSInteger reGeocodeTimeout= [iosOption[@"defaultReGeocodeTimeout"] integerValue];
    BOOL locatingWithReGeocode= [iosOption[@"locatingWithReGeocode"] boolValue];

    switch (desiredAccuracy){
        case 1:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBestForNavigation];
            break;
        case 2:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
            break;
        case 3:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyNearestTenMeters];
            break;
        case 4:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyHundredMeters];
            break;
        case 5:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyKilometer];
            break;
        case 6:
            [self.locationManager setDesiredAccuracy:kCLLocationAccuracyThreeKilometers];
            break;
    }
    //设置不允许系统暂停定位
    [self.locationManager setPausesLocationUpdatesAutomatically:pausesLocationUpdatesAutomatically];
    //设置允许在后台定位
    [self.locationManager setAllowsBackgroundLocationUpdates:allowsBackgroundLocationUpdates];
    //设置定位超时时间
    [self.locationManager setLocationTimeout:locationTimeout];
    //设置逆地理超时时间
    [self.locationManager setReGeocodeTimeout:reGeocodeTimeout];
    //开启定位
    [self.locationManager requestLocationWithReGeocode:locatingWithReGeocode completionBlock:self.completionBlock];
}

#pragma clang diagnostic pop

#pragma mark - Life Cycle

#pragma mark - AMapLocationManager Delegate

- (void)amapLocationManager:(AMapLocationManager *)manager doRequireLocationAuth:(CLLocationManager *)locationManager {
    [locationManager requestAlwaysAuthorization];
}

- (void)dealloc {
    [self cleanUpAction];
}
@end;
