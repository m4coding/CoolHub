# [点我可看中文介绍-Chinese introduction](README-cn.md)

# CoolHub

Open source Github Android client, based on Kotlin, componentized development

## function

* supports the recommendation of repository based on personal interests
* support hot repository view
* supports searching for repositories and users
* support repository details view
* support user details view
* support for star and watch
* support in Chinese and English

## App screenshots

| Recommend | Focus | Hot |
|:-:|:-:|:-:|
| ![recommend](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-recommend.png) | ![focus](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-focus.png) | ![hot](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-homepage-hot-trend.png) |

| Search | Users | Repository |
|:-:|:-:|:-:|
| ![search](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-search.png) | ![user](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-user-details.png) | ![repository](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-repository-details.png) |

| Setting | SourceView | Issue |
|:-:|:-:|:-:|
| ![setting](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-setting.png)  | ![source](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-source-view.png)  | ![issue](https://github.com/m4coding/CoolHub/blob/master/app_art/app-screenshot-issue-details.png) |

## Download

### [Download address](https://www.pgyer.com/0YCe)

![](https://github.com/m4coding/CoolHub/blob/master/app_art/CoolHub-Download-url.png)

## App architecture

![Architecture](https://github.com/m4coding/CoolHub/blob/master/app_art/CoolHub-Architecture.png)

The whole App is divided into basic module **Module_base** and business module **Module_Business**

    (1) the basic modules are divided into base(basic function), api (network business component) and widgets (basic UI component).

    (2) business module is divided into business_base, business_start, business_mainpage, business_login and business_search.

    (3) communication between business components is handled through the CC framework


    Profile description:
    (1) Appmodules.properties: componentized configuration.
        For example, business_login is true, and the login component runs in debug App mode and false in library mode

    (2) gradle.properties:
        -> IS_SIMULATOR true means support for running on the simulator, and false means no support for running on the simulator

## Third party library used

* [Glide](https://github.com/bumptech/glide)
* [jsoup](https://github.com/jhy/jsoup)
* [GreenDao](https://github.com/greenrobot/greenDAO)
* [EventBus](https://github.com/greenrobot/EventBus)
* [RxJava](https://github.com/ReactiveX/RxJava)
* [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Retrofit](https://github.com/square/retrofit)
* [AndroidAutoSize](https://github.com/JessYanCoding/AndroidAutoSize)
* [ImmersionBar](https://github.com/gyf-dev/ImmersionBar)
* [CC](https://github.com/luckybilly/CC)


## LICENSE

CoolHub is licensed under the [GPL-3.0](https://www.gnu.org/licenses/gpl.html)