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
window.GaoDe.getCurrentPosition(successCallback, failedCallback,”“);
window.GaoDe.startSerialLocation(successCallback, failedCallback,”“);
window.GaoDe.stopSerialLocation(successCallback, failedCallback,”“);
// ts项目调用。
(<any>window).GaoDe.getCurrentPosition(successCallback, failedCallback,”“);
(<any>window).GaoDe.startSerialLocation(successCallback, failedCallback,”“);
(<any>window).GaoDe.stopSerialLocation(successCallback, failedCallback,”“);
```


获取单次定位

getCurrentPosition(successCallback,failedCallback,ages);

参数|类型|说明
--|:--:|--:
successCallback|funtion|回调函数
failedCallback|funtion|回调函数
option|待续|定位参数

```
//调用实例
getCurrentPosition() {
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
        desiredAccuracy:4,
        pausesLocationUpdatesAutomatically:"YES",
        allowsBackgroundLocationUpdates:"NO",
        locationTimeout:10,
        reGeocodeTimeout:5,
        locatingWithReGeocode:"YES"
      }
    };
    (<any>window).GaoDe.getCurrentPosition( (res) => {
      console.log(JSON.stringify(res));
    }, () => {

    },obj);
  }
```

开启持续定位

startSerialLocation(successCallback,failedCallback,ages);

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
