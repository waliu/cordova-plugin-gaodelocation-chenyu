### 高德地图定位Android,iOS采用gps定位
本插件利用高德地图提供的定位功能进行Android版手机定位。

cordova-android >= 7.0.0

#### 1.申请密钥
请参照：
<br>
[申请android密钥定位SDK](http://lbs.amap.com/api/android-location-sdk/guide/create-project/get-key/)
<br>
[申请ios密钥定位SDK](https://lbs.amap.com/api/ios-location-sdk/guide/create-project/get-key)
#### 2.安装插件

```
cordova plugin add cordova-plugin-gaodelocation-chenyu --variable  ANDROID_API_KEY=your android key --variable  IOS_API_KEY=your ios key
npm install --save @ionic-native/gao-de-location
```

#### 3.js/ionic2、3使用方法

```
// js项目调用
window.GaoDe.getCurrentPosition(successCallback, failedCallback);
// ts项目调用。
(<any>window).GaoDe.getCurrentPosition(successCallback, failedCallback);
```

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
#### 5.[关于Androd插件的来源](https://blog.csdn.net/u010730897/article/details/54969638)
#### 6.ios版本本人自制
#### 7.联系我:QQ群 390736068
