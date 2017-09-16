
 # AddressDialog 
 
  　　因为连续几个项目都用到了地址选择dialog，虽然样式差不多，但是，数据源的处理逻辑却不一样 (〃＞皿＜)。几次修改之后，抽离出这个项目，假设之后遇上需要实现多种效果的地址选择dialog，可以整合以防不备。
 
```java
dependencies {
    compile 'cn.zengcanxiang:addressDialog:1.0.1'
}
```

 ## 效果图
 
[demo.apk](https://github.com/zengcanxiang/AddressDialog/blob/master/app-debug.apk) 
 
![示例](https://github.com/zengcanxiang/AddressDialog/blob/master/Animation.gif) 
 
 ## Model
 
 项目分离数据层和UI层，将数据提供分离成接口：LocalModel、NetworkModel。
  
-  BaseAddressModel
		<br/>不管是本地还是网络数据都需要展示给用户看的是名称，便将这部分抽离在BaseModel里面 
		<br/>
   ```java
 
    /**
     * 显示的省份名称
     */
    String showProvinceName(P province);

    /**
     * 显示的城市名称
     */
    String showCityName(C city);

    /**
     * 显示的区县名称
     */
    String showDistrictName(D district);

    /**
     * 获取选择的数据
     */
    String getSelect(P province, C city, D district);
 ```
- LocalModel
 		<br/>根据现有的项目，抽离出的本地model 
		<br/>
  ```java
 
  /**
     * 提供省份数据
     */
    List<P> getProvince();

    /**
     * 提供选择省份之后的城市数据
     */
    List<C> getCity(P province);

    /**
     * 提供选择城市之后的区县数据
     */
    List<D> getDistrict(C city);
 ```
 
 ## UI
 
 一开始萌生这个想法的项目的是模仿网易严选APP，自然地址选择dialog也就和网易严选的一样。后来，便想模仿其余开发商的地址选择，因为项目的缘故，只实现了两个--网易严选和京东。而且这个两个还长一样(⊙﹏⊙)b
 
 ## TODO 

- [ ] 滚轮选择dialog(模仿淘宝)

- [ ] 左右双列表dialog(模仿顺丰)
 
- [ ] 布局可配置(修改已经实现的dialog样式，但是不需要自己实现大部分逻辑)
 
 
 期待有更多的人实现并且分享给大家
 
 
 
 
 ### License

This library is licensed under the [Apache Software License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

See [`License`](License) for full of the license text.

    Copyright (C) 2017 [Hanks](https://github.com/zengcanxiang)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 
   
 
 
