# [README of English](README.md)

# CoolHub

开源的Github Android客户端，基于Kotlin，组件化开发

## 功能

* 支持根据个人兴趣推荐仓库
* 支持热点仓库查看
* 支持搜索仓库和用户
* 支持仓库详情查看
* 支持用户详情查看
* 支持star和watch
* 支持中英文

## App截图

| 推荐 | 关注 | 热点 |
|:-:|:-:|:-:|
| ![recommend](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-recommend.png) | ![focus](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-focus.png) | ![hot](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-hot-trend.png) |

| 搜索 | 用户 | 仓库 |
|:-:|:-:|:-:|
| ![search](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-search.png) | ![user](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-user-details.png) | ![repository](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-repository-details.png) |

| 设置 | 源码查看 | 问题|
|:-:|:-:|:-:|
| ![setting](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-setting.png)  | ![source](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-source-view.png)  | ![issue](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-issue-details.png) |

## 下载

### [下载地址](https://www.pgyer.com/0YCe)

![](https://github.com/m4coding/CoolHub/blob/master/app_art/CoolHub-Download-url.png)

## App架构

![Architecture](https://github.com/m4coding/CoolHub/blob/master/app_art/CoolHub-Architecture.png)

整个App分为基础模块Module_base和业务模块Module_Business


    （1）基础模块分为base(基础功能)、api（网络业务组件）、widgets（基础ui组件）

    （2）业务模块分为business_base（基础业务模块）、business_start（启动页业务组件）、business_mainpage（首页业务组件）、business_login（登录业务组件）、business_search（搜索业务组件）

    （3）各业务组件之间的通信通过CC框架来处理

    配置文件说明：
    （1）AppModules.properties 组件化配置，例如business_login为true，登录组件就以调试App模式运行，false为library模式
    （2）gradle.properties
            -> IS_SIMULATOR  true表示支持在模拟器上运行，false表示不支持在模拟器上运行

## 用到的第三方框架

* [Glide](https://github.com/bumptech/glide) 图像加载和缓存库
* [jsoup](https://github.com/jhy/jsoup) HTML解析器
* [GreenDao](https://github.com/greenrobot/greenDAO) 数据库
* [EventBus](https://github.com/greenrobot/EventBus) 事件库
* [RxJava](https://github.com/ReactiveX/RxJava) 一个专注于异步编程与控制可观察数据（或者事件）流的API
* [RxAndroid](https://github.com/ReactiveX/RxAndroid) 为了在Android中使用RxJava
* [Retrofit](https://github.com/square/retrofit) HTTP请求
* [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize) 今日头条屏幕适配方案
* [ImmersionBar](https://github.com/gyf-dev/ImmersionBar)  沉浸式状态栏和沉浸式导航栏管理
* [CC](https://github.com/luckybilly/CC)  Android组件化框架


## LICENSE

CoolHub is licensed under the [GPL-3.0](https://www.gnu.org/licenses/gpl.html)