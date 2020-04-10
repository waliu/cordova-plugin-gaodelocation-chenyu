### 高德地图定位 （2.0.5版本）

插件环境 cordova-android >= 7.0.0

#### 1.申请密钥
请参照：
<br>
[申请android密钥定位SDK](http://lbs.amap.com/api/android-location-sdk/guide/create-project/get-key/)
<br>
[申请ios密钥定位SDK](https://lbs.amap.com/api/ios-location-sdk/guide/create-project/get-key)
#### 2.安装插件



```bash
# 1.通过npm 安装 （2.0.4版本）

cordova plugin add cordova-plugin-gaodelocation-chenyu --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key

npm install --save @ionic-native/gao-de-location

# 2.通过github安装 （2.0.5版本，由于github限速，可以将插件克隆或者下载到本地安装）
cordova plugin add https://github.com/waliu/cordova-plugin-gaodelocation-chenyu  --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key

# 3.或者本地安
cordova plugin add --link 文件路径  --variable ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key

```



#### 3.js/ts使用方法

```typescript
// 1.js项目调用
window.GaoDe.getCurrentPosition(successCallback, failedCallback,option);
window.GaoDe.startSerialLocation(successCallback, failedCallback,option);
window.GaoDe.stopSerialLocation(successCallback, failedCallback);
// 2.ts/ionic项目调用。
(<any>window).GaoDe.getCurrentPosition(successCallback, failedCallback,option);
(<any>window).GaoDe.startSerialLocation(successCallback, failedCallback,option);
(<any>window).GaoDe.stopSerialLocation(successCallback, failedCallback);

```

①[ionic2.0.4版本调用方式](https://ionicframework.com/docs/native/gao-de-location)

②[ionic2.0.5版本调用方式](#Mark)
#### 4.定位方法说明

### 获取单次定位

> getCurrentPosition(successCallback,failedCallback,option);

参数|类型|说明
--|:--:|--
successCallback|funtion|回调函数
failedCallback|funtion|回调函数
option|PositionOption|定位参数

### <font color=red>PositionOption</font>

参数|类型|说明
--|:--:|--
androidOption|androidOption|android定位参数
iosOption|iosOption|ios定位参数

### androidOption

参数|类型|说明
--|:--:|--
locationMode|Number|1.精确定位 2.仅设备定位模式；3.低功耗定位模式
gpsFirst|Boolean|设置是否gps优先，只在高精度模式下有效。默认关闭
HttpTimeOut|Number|设置网络请求超时时间。默认为30秒。在仅设备模式下无效
interval|Number|设置定位间隔。默认为2秒 连续定位有效
needAddress|Boolean|设置是否返回逆地理地址信息。默认是true
onceLocation|Boolean|设置是否单次定位。默认是false
onceLocationLatest|Boolean|设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
locationProtocol|Number|设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP。1.http 2.https
sensorEnable|Boolean|设置是否使用传感器。默认是false
wifiScan|Boolean|设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
locationCacheEnable|Boolean|设置是否使用缓存定位，默认为true

### iosOption 
参数|类型|说明
--|:--:|--
desiredAccuracy|Number|1.最适合导航用的定位  iOS4.0以后新增 2.精度最高的定位 3.定位精度在10米以内 4.定位精度在100米以内 5.定位精度在1000米以内 6.3000m以内
pausesLocationUpdatesAutomatically|String|指定定位是否会被系统自动暂停。默认为NO。
allowsBackgroundLocationUpdates|String|是否允许后台定位。默认为NO。
locationTimeout|Number|指定单次定位超时时间,默认为10s。最小值是2s。
reGeocodeTimeout|Number|指定单次定位逆地理超时时间,默认为5s。最小值是2s。
locatingWithReGeocode|String|是否启用逆地址定位 默认YES

```typescript
//调用实例
getCurrentPosition() {
    let obj={
      androidOption:{
        locationMode:1,//定位精度 1.精确定位 2.仅设备定位模式；3.低功耗定位模式
        gpsFirst:false,//设置是否gps优先，只在高精度模式下有效。默认关闭
        HttpTimeOut:30000,//设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        interval:2000,//设置定位间隔。默认为2秒 连续定位有效
        needAddress:true,//设置是否返回逆地理地址信息。默认是true
        onceLocation:false,//设置是否单次定位。默认是false
        onceLocationLatest:false,//设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationProtocol:1,// 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP。1.http 2.https
        sensorEnable:false,//设置是否使用传感器。默认是false
        wifiScan:true,//设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        locationCacheEnable:true//设置是否使用缓存定位，默认为true
      },
      iosOption:{
        desiredAccuracy:4,// 1。最适合导航用的定位  iOS4.0以后新增 2.精度最高的定位 3.定位精度在10米以内定位精度在10米以内 4.定位精度在100米以内 5.定位精度在1000米以内 6.3000m
        pausesLocationUpdatesAutomatically:"YES",//指定定位是否会被系统自动暂停。默认为NO。
        allowsBackgroundLocationUpdates:"NO",//是否允许后台定位。默认为NO。只在iOS 9.0及之后起作用。设置为YES的时候必须保证 Background Modes 中的 Location updates 处于选中状态，否则会抛出异常。由于iOS系统限制，需要在定位未开始之前或定位停止之后，修改该属性的值才会有效果。
        locationTimeout:10, //指定单次定位超时时间,默认为10s。最小值是2s。注意单次定位请求前设置。注意: 单次定位超时时间从确定了定位权限(非kCLAuthorizationStatusNotDetermined状态)后开始计算
        reGeocodeTimeout:5, //指定单次定位逆地理超时时间,默认为5s。最小值是2s。注意单次定位请求前设置。
        locatingWithReGeocode:"YES" //是否 启用逆地址定位 默认YES
      }
    };
    (<any>window).GaoDe.getCurrentPosition( (res) => {
      console.log(JSON.stringify(res));
    }, () => {

    },obj);
  }
```

开启持续定位

> startSerialLocation(successCallback,failedCallback,option);

参数|类型|说明
--|:--:|--:
successCallback|funtion|回调函数
failedCallback|funtion|回调函数
option|PositionOption|定位参数

### androidOption

参数|类型|说明
--|:--:|--
locationMode|Number|1.精确定位 2.仅设备定位模式；3.低功耗定位模式
gpsFirst|Boolean|设置是否gps优先，只在高精度模式下有效。默认关闭
HttpTimeOut|Number|设置网络请求超时时间。默认为30秒。在仅设备模式下无效
interval|Number|设置定位间隔。默认为2秒 连续定位有效
needAddress|Boolean|设置是否返回逆地理地址信息。默认是true
onceLocation|Boolean|设置是否单次定位。默认是false
onceLocationLatest|Boolean|设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
locationProtocol|Number|设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP。1.http 2.https
sensorEnable|Boolean|设置是否使用传感器。默认是false
wifiScan|Boolean|设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
locationCacheEnable|Boolean|设置是否使用缓存定位，默认为true

### iosOption 

参数|类型|说明
--|:--:|--
pausesLocationUpdatesAutomatically|String|指定定位是否会被系统自动暂停。默认为NO。
allowsBackgroundLocationUpdates|String|是否允许后台定位。默认为NO。
locatingWithReGeocode|String|是否启用逆地址定位 默认YES

```typescript
startSerialLocation() {
    let obj={
      androidOption:{
        locationMode:1,
        gpsFirst:false,
        HttpTimeOut:30000,
        interval:2000,
        needAddress:true,
        onceLocation:false,
        onceLocationLatest:false,
        locationProtocol:1,
        sensorEnable:false,
        wifiScan:true,
        locationCacheEnable:true
      },
      iosOption:{
        pausesLocationUpdatesAutomatically:"YES",
        allowsBackgroundLocationUpdates:"NO",
        locatingWithReGeocode:"YES"
      }
    };
    (<any>window).GaoDe.startSerialLocation( (res) => {
      console.log(JSON.stringify(res));
    }, (e) => {

    },obj);
  }
```

停止持续定位

> stopSerialLocation(successCallback,failedCallback);

参数|类型|说明
--|:--:|--:
successCallback|funtion|回调函数
failedCallback|funtion|回调函数









#### 5.返回值说明:

返回值字段|返回值类型|说明| android支持|ios支持
--|:--:|--|--|--
latitude|string|获取纬度|√|√
longitude|string|获取经度|√|√
accuracy|string|获取精度信息|√|√
formattedAddress|string|获取地址描述|√|√
country|string|获取国家名称|√|√
province|string|获取省名称|√|√
city|string|获取城市名称|√|√
district|string|获取城区名称|√|√
citycode|string|获取城市编码信息|√|√
adcode|string|获取区域编码信息|√|√
street|string|获取街道名称|√|√
number|string|街道门牌号信息|√|√
POIName|string|获取当前位置的POI名称|√|√
AOIName|string|获取当前位置所处AOI名称|√|√
altitude|string|获取海拔高度信息|√|×
speed|string|单位：米/秒|√|×
bearing|string|获取方向角信息|√|×
buildingId|string|获取室内定位建筑物Id|√|×
floor|string|获取室内定位楼层|√|×
gpsAccuracyStatus|string|获取GPS当前状态，返回值可参考AMapLocation类提供的常量|√|×
locationType|string|获取定位结果来源|√|×
locationDetail|string|定位信息描述|√|×


#### 6.Ionic4使用方法（插件版本 2.0.5 ）<div id="Mark"></div>
```typescript
// app.module.ts ionic3-
import { GaoDeLocation , PositionOptions } from 'cordova-plugin-gaodelocation-chenyu/ionic/gao-de-location';
//ionic 4+ 
import {
  GaoDeLocation,
  PositionOptions,
  LocationModeEnum,
  LocationProtocolEnum,
  DesiredAccuracyEnum,
  PositionRes
} from 'cordova-plugin-gaodelocation-chenyu/ionic/gao-de-location/ngx';

...

@NgModule({
  ...

  providers: [
    ...
    GaoDeLocation
    ...
  ]
  ...
})
export class AppModule { }
```
```typescript

// app.component.ts
// ionic3-
import { GaoDeLocation , PositionOptions } from 'cordova-plugin-gaodelocation-chenyu/ionic/gao-de-location';
// ionic 4+ 
import {
  GaoDeLocation,
  PositionOptions,
  LocationModeEnum,
  LocationProtocolEnum,
  DesiredAccuracyEnum,
  PositionRes
} from 'cordova-plugin-gaodelocation-chenyu/ionic/gao-de-location/ngx';


@Component({ ... })
export class xxxComponent {
  //注入
  constructor(private gaoDeLocation: GaoDeLocation) {}
  //调用定位
  async getCurrentPosition() {
      const positionOptions: PositionOptions = {
        androidOption: {
          locationMode: LocationModeEnum.Hight_Accuracy,
          gpsFirst: false,
          HttpTimeOut: 30000,
          interval: 2000,
          needAddress: true,
          onceLocation: false,
          onceLocationLatest: false,
          locationProtocol: LocationProtocolEnum.HTTP,
          sensorEnable: false,
          wifiScan: true,
          locationCacheEnable: true
        }, iosOption: {
          desiredAccuracy: DesiredAccuracyEnum.kCLLocationAccuracyBest,
          pausesLocationUpdatesAutomatically: 'YES',
          allowsBackgroundLocationUpdates: 'NO',
          locationTimeout: 10,
          reGeocodeTimeout: 5,
        }
      };
      const positionRes: PositionRes = await this.gaoDeLocation.getCurrentPosition(positionOptions).catch((e: any) => {
        console.log(e);
      }) || null;
      console.log(JSON.stringify(positionRes));
    }
  
    startSerialLocation() {
      const positionOptions: PositionOptions = {
        androidOption: {
          locationMode: LocationModeEnum.Hight_Accuracy,
          gpsFirst: false,
          HttpTimeOut: 30000,
          interval: 2000,
          needAddress: true,
          onceLocation: false,
          onceLocationLatest: false,
          locationProtocol: LocationProtocolEnum.HTTP,
          sensorEnable: false,
          wifiScan: true,
          locationCacheEnable: true
        }, iosOption: {
          desiredAccuracy: DesiredAccuracyEnum.kCLLocationAccuracyBest,
          pausesLocationUpdatesAutomatically: 'YES',
          allowsBackgroundLocationUpdates: 'NO',
          locationTimeout: 10,
          reGeocodeTimeout: 5,
        }
      };
      this.gaoDeLocation.startSerialLocation(positionOptions).subscribe((positionRes: PositionRes) => {
        console.log(JSON.stringify(positionRes));
      });
    }
  
    stopSerialLocation() {
      const positionRes: any = this.gaoDeLocation.stopSerialLocation().catch((e) => {
        console.log(e);
      }) || null;
      console.log(JSON.stringify(positionRes));
    }
}
```
#### 7.联系我:QQ群 390736068

#### 8.插件调用方式已经传到ionic官网 （官网对应的插件版本 2.0.4 ）
[ionic官网快捷链接](https://ionicframework.com/docs/native/gao-de-location)
