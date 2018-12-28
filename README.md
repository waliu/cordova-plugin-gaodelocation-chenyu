### 高德地图定位Android,iOS采用gps定位
本插件利用高德地图提供的定位功能进行Android版手机定位。

cordova-android >= 7.0.0

#### 1.申请密钥
请参照：[申请密钥Android定位SDK](http://lbs.amap.com/api/android-location-sdk/guide/create-project/get-key/)

#### 2.安装插件

```
cordova plugin add cordova-plugin-gaodelocation-chenyu --variable API_KEY=your key
npm i gaodelocation-chenyu
```

#### 3.js使用方法

```
// 进行定位
window.GaoDe.getCurrentPosition(successCallback, failedCallback);
```

获得定位信息，返回JSON格式数据:

```
{
  latitude : 纬度,
  lontitude: 经度,
  ...
}
```

#### 4.Ionic2/3/4使用方法
```typescript
// app.module.ts
import {GaoDe} from "gaodelocation-chenyu";
...

@NgModule({
  ...

  providers: [
    ...
    GaoDe
    ...
  ]
  ...
})
export class AppModule { }
```
```
@Component({ ... })
export class xxxComponent {
  //注入
  constructor(private gaode:GaoDe) {}
  //调用定位
  getCurrentPosition(){
    this.gaode.getCurrentPosition().then((res=>{
       console.log(res);
    })).catch((err=>{}))
  }
}
```
#### 5.[关于插件的来源](https://blog.csdn.net/u010730897/article/details/54969638)
#### 6.联系我:QQ群 390736068