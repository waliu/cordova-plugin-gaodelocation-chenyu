#import "SerialLocation.h"

@implementation SerialLocation

#pragma mark - Action Handle

- (void)configLocationManager {
    self.locationManager = [[AMapLocationManager alloc] init];

    [self.locationManager setDelegate:self];

    //设置不允许系统暂停定位
    [self.locationManager setPausesLocationUpdatesAutomatically:NO];

    //设置允许在后台定位
    [self.locationManager setAllowsBackgroundLocationUpdates:YES];

    //设置允许连续定位逆地理
    [self.locationManager setLocatingWithReGeocode:YES];
}

- (id)init {
    [self configLocationManager];
    return self;
}

- (void)startSerialLocation:(NSMutableDictionary *)message {
    [self setLocationOption:message];
    [self.locationManager startUpdatingLocation];
}

- (void)stopSerialLocation {
    [self.locationManager stopUpdatingLocation];
}

- (void)setLocationOption:(NSMutableDictionary *)message{
    NSMutableDictionary *iosOption=message[@"iosOption"];
    BOOL pausesLocationUpdatesAutomatically= [iosOption[@"pausesLocationUpdatesAutomatically"] boolValue];
    BOOL allowsBackgroundLocationUpdates= [iosOption[@"allowsBackgroundLocationUpdates"] boolValue];
    BOOL locatingWithReGeocode= [iosOption[@"locatingWithReGeocode"] boolValue];

    //设置不允许系统暂停定位
    [self.locationManager setPausesLocationUpdatesAutomatically:pausesLocationUpdatesAutomatically];

    //设置允许在后台定位
    [self.locationManager setAllowsBackgroundLocationUpdates:allowsBackgroundLocationUpdates];

    //设置允许连续定位逆地理
    [self.locationManager setLocatingWithReGeocode:locatingWithReGeocode];
}

#pragma mark - AMapLocationManager Delegate

- (void)amapLocationManager:(AMapLocationManager *)manager doRequireLocationAuth:(CLLocationManager *)locationManager {
    [locationManager requestAlwaysAuthorization];
}

- (void)amapLocationManager:(AMapLocationManager *)manager didFailWithError:(NSError *)error {
    NSLog(@"%s, amapLocationManager = %@, error = %@", __func__, [manager class], error);

    if (error.code == AMapLocationErrorRiskOfFakeLocation) {
        NSLog(@"存在虚拟定位的风险:{%ld - %@};", (long) error.code, error.userInfo);

        //存在虚拟定位的风险的定位结果
        __unused CLLocation *riskyLocateResult = [error.userInfo objectForKey:@"AMapLocationRiskyLocateResult"];
        //存在外接的辅助定位设备
        __unused NSDictionary *externalAccressory = [error.userInfo objectForKey:@"AMapLocationAccessoryInfo"];
    }
}

- (void)amapLocationManager:(AMapLocationManager *)manager didUpdateLocation:(CLLocation *)location reGeocode:(AMapLocationReGeocode *)reGeocode {
//    NSLog(@"location:{lat:%f; lon:%f; accuracy:%f; reGeocode:%@}", location.coordinate.latitude, location.coordinate.longitude, location.horizontalAccuracy, reGeocode.formattedAddress);
    [self.delegate SerialLocationInfo:location Regeocode:reGeocode];
}
@end
