package com.m4coding.coolhub.business.mainpage.modules.setting.model.bean

class SettingBean {

    companion object {
        const val TYPE_APP_VERSION = 0x8001 //app版本信息
        const val TYPE_LOGIN_OUT = 0x8002 //登录退出
    }

    constructor(title: String?, subTitle: String?, value: String?, type: Int) {
        this.title = title
        this.subTitle = subTitle
        this.value = value
        this.type = type
    }

    constructor()

    var title: String? = null
    var subTitle: String? = null
    var value: String? = null
    var type: Int = 0
}