### 高德地图定位

cordova-android >= 7.0.0

#### 1.申请密钥
请参照：
<br>
[申请android密钥定位SDK](http://lbs.amap.com/api/android-location-sdk/guide/create-project/get-key/)
<br>
[申请ios密钥定位SDK](https://lbs.amap.com/api/ios-location-sdk/guide/create-project/get-key)
#### 2.安装插件



```
1.通过npm 安装

$ cordova plugin add cordova-plugin-gaodelocation-chenyu --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key
$ npm install --save @ionic-native/gao-de-location

2.通过github安装
$ cordova plugin add https://github.com/waliu/cordova-plugin-gaodelocation-chenyu  --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key

3.或者本地安装
$ cordova plugin add 文件路径  --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key

```

#### 3.js/ts使用方法

```
// js项目调用
window.GaoDe.getCurrentPosition(successCallback, failedCallback,option);
window.GaoDe.startSerialLocation(successCallback, failedCallback,option);
window.GaoDe.stopSerialLocation(successCallback, failedCallback);
// ts项目调用。
(<any>window).GaoDe.getCurrentPosition(successCallback, failedCallback,option);
(<any>window).GaoDe.startSerialLocation(successCallback, failedCallback,option);
(<any>window).GaoDe.stopSerialLocation(successCallback, failedCallback);
```


### 获取单次定位

##### getCurrentPosition(successCallback,failedCallback,option);

参数|类型|说明
--|:--:|--
successCallback|funtion|回调函数
failedCallback|funtion|回调函数
option|PositionOption|定位参数

### <font color=red>PositionOption</font>

参数|类型|说明
--|:--:|--
androidOption|androidOption|android定位参数
iosOption|androidOption|ios定位参数

### androidOption

参数|类型|说明
--|:--:|--
locationMode|Number|1.精确定位 2.仅设备定位模式；3.低功耗定位模式
gpsFirst|Bool|设置是否gps优先，只在高精度模式下有效。默认关闭
HttpTimeOut|Number|可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
interval|Number|设置定位间隔。默认为2秒 连续定位有效
```
//调用实例
getCurrentPosition() {
    let obj={
      androidOption:{
        locationMode:1,//定位精度 1.精确定位 2.仅设备定位模式；3.低功耗定位模式
        gpsFirst:false,//设置是否gps优先，只在高精度模式下有效。默认关闭
        HttpTimeOut:30000,//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
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

startSerialLocation(successCallback,failedCallback,option);

参数|类型|说明
--|:--:|--:
successCallback|funtion|回调函数
failedCallback|funtion|回调函数
option|待续|定位参数

```
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

stopSerialLocation(successCallback,failedCallback,ages);

参数|类型|说明
--|:--:|--:
successCallback|funtion|回调函数
failedCallback|funtion|回调函数









获得定位信息，返回JSON格式数据:

```
{
  accuracy: 水平精度

  adcode: 邮编

  address: 具体地址

  city: 城市

  citycode: 国家编码

  country: 国家

  district: 区域

  latitude: 经度

  longitude: 纬度

  poi: 地址名称

  province: 省

  status: 是否成功

  type: ""
}
```

#### 4.Ionic4使用方法
```typescript
// app.module.ts ionic3-
import { GaoDeLocation , PositionOptions } from '@ionic-native/gao-de-location';
//ionic 4+
import { GaoDeLocation , PositionOptions } from '@ionic-native/gao-de-location/ngx';
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
```
import { GaoDeLocation,PositionOptions } from '@ionic-native/gao-de-location';
@Component({ ... })
export class xxxComponent {
  //注入
  constructor(private gaoDeLocation: GaoDeLocation) {}
  //调用定位
  getCurrentPosition(){
    this.gaoDeLocation.getCurrentPosition()
    .then((res: PositionOptions) => {
       return console.log(res);
    })
    .catch((error) => console.error(error));
  }
}
```
#### 5.联系我:QQ群 390736068
