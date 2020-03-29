import { IonicNativePlugin } from '@ionic-native/core';
import { Observable } from 'rxjs';
/**
 * @name Gao De Location
 * @description
 * Because the original GPS positioning uses Google Browser positioning, and Google withdraws from China, resulting in GPS Android positioning can not be positioned.
 * Gaode location can directly return address informationGaode location can directly return address information
 *
 * @usage
 * ```typescript
 * import { GaoDeLocation } from '@ionic-native/gao-de-location/ngx';
 *
 *
 * constructor(private gaoDeLocation: GaoDeLocation) { }
 *
 * this.gaoDeLocation.getCurrentPosition()
 * .then((res: PositionOptions) => console.log(res))
 * .catch((error) => console.error(error));
 *
 * ```
 */
export declare class GaoDeLocationOriginal extends IonicNativePlugin {
    /**
     * Single location
     * @param positionOptions
     * @return Promise<PositionRes>
     */
    getCurrentPosition(positionOptions: PositionOptions): Promise<PositionRes>;
    /**
     * Start serial location
     * @param positionOptions
     * @return Promise<PositionRes>
     */
    startSerialLocation(positionOptions: PositionOptions): Observable<PositionRes>;
    /**
     * Stop Serial Location
     * @return Promise<any>
     */
    stopSerialLocation(): Promise<any>;
}
/**
 * Location parameter
 */
export interface PositionOptions {
    /**
     * android options
     */
    androidOption: AndroidOptions;
    /**
     * ios options
     */
    iosOption: IosOptions;
}
/**
 * android positioning accuracy
 */
export interface AndroidOptions {
    /**
     * location mode
     */
    locationMode: LocationModeEnum;
    /**
     * gps first
     */
    gpsFirst: boolean;
    /**
     * Http timeOut
     */
    HttpTimeOut: number;
    /**
     * Location interval
     */
    interval: number;
    /**
     * Open reverse address
     */
    needAddress: boolean;
    /**
     * once location
     */
    onceLocation: boolean;
    /**
     * once location latest
     */
    onceLocationLatest: boolean;
    /**
     * location protocol
     */
    locationProtocol: LocationProtocolEnum;
    /**
     * sensor enable
     */
    sensorEnable: boolean;
    /**
     * wifi scan
     */
    wifiScan: boolean;
    /**
     * location cache enable
     */
    locationCacheEnable: boolean;
}
/**
 *
 * IOS positioning parameters
 *
 */
export interface IosOptions {
    /**
     * desired accuracy
     */
    desiredAccuracy?: DesiredAccuracyEnum;
    /**
     * pauses location updates automatically
     */
    pausesLocationUpdatesAutomatically: IosBoolean;
    /**
     * allows background location updates
     */
    allowsBackgroundLocationUpdates: IosBoolean;
    /**
     * location timeout
     */
    locationTimeout: number;
    /**
     * re geocode timeout
     */
    reGeocodeTimeout?: number;
    /**
     * locating with re geocode
     */
    locatingWithReGeocode?: IosBoolean;
}
/**
 * ios positioning accuracy
 * https://developer.apple.com/documentation/corelocation/kcllocationaccuracybestfornavigation
 */
export declare enum DesiredAccuracyEnum {
    /**
     * The highest possible accuracy that uses additional sensor data to facilitate navigation apps.
     */
    kCLLocationAccuracyBestForNavigation = 1,
    /**
     * The best level of accuracy available.
     */
    kCLLocationAccuracyBest = 2,
    /**
     * Accurate to within ten meters of the desired target.
     */
    kCLLocationAccuracyNearestTenMeters = 3,
    /**
     * Accurate to within one hundred meters.
     */
    kCLLocationAccuracyHundredMeters = 4,
    /**
     * Accurate to the nearest kilometer.
     */
    kCLLocationAccuracyKilometer = 5,
    /**
     * Accurate to the nearest three kilometers.
     */
    kCLLocationAccuracyThreeKilometers = 6
}
/**
 * locationModeEnum
 */
export declare enum LocationModeEnum {
    Hight_Accuracy = 1,
    Battery_Saving = 2,
    Device_Sensors = 3
}
/**
 * locationProtocolEnum
 */
export declare enum LocationProtocolEnum {
    HTTP = 1,
    HTTPS = 2
}
/**
 * ios boolean
 */
export declare type IosBoolean = 'YES' | 'NO';
/**
 * Positioning return result
 */
export interface PositionRes {
    /**
     * Status code
     */
    code: number;
    /**
     * latitude
     */
    latitude: string;
    /**
     * longitude
     */
    longitude: string;
    /**
     * accuracy
     */
    accuracy: string;
    /**
     * address
     */
    formattedAddress: string;
    /**
     * country
     */
    country: string;
    /**
     * province
     */
    province: string;
    /**
     * city
     */
    city: string;
    /**
     * district
     */
    district: string;
    /**
     * citycode
     */
    citycode: string;
    /**
     * adcode
     */
    adcode: string;
    /**
     * street
     */
    street: string;
    /**
     * Street number information
     */
    number: string;
    /**
     * POI name
     */
    POIName: string;
    /**
     * AOI Name
     */
    AOIName: string;
    /**
     * altitude
     */
    altitude?: string;
    /**
     * speed
     */
    speed?: string;
    /**
     * bearing
     */
    bearing?: string;
    /**
     * building id
     */
    buildingId?: string;
    /**
     * floor
     */
    floor?: string;
    /**
     * gps accuracy status
     */
    gpsAccuracyStatus?: string;
    /**
     * Get location result source
     */
    locationType?: string;
    /**
     * Location detail
     */
    locationDetail?: string;
}

export declare const GaoDeLocation: GaoDeLocationOriginal;