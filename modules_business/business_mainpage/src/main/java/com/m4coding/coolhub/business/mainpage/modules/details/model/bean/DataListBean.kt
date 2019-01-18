package com.m4coding.coolhub.business.mainpage.modules.details.model.bean

class DataListBean {

    companion object {
        const val TYPE_FOLLOWING = 0x1001 //关注
        const val TYPE_FOLLOWERS = 0x1002 //粉丝
        const val TYPE_STARTERS = 0x1003 //仓库点赞者  对仓库而言
        const val TYPE_WATCHERS = 0x1004 //仓库Watcher
        const val TYPE_FORKERS = 0x1005  //仓库Forker
        const val TYPE_ISSUE_OPEN = 0x1006 //打开的issue
        const val TYPE_ISSUE_CLOSE = 0x1007 //关闭的issue
        const val TYPE_ISSUE_TIME_LINE = 0x1008 //issue的timeline （详情页）


        const val TYPE_SETTING_TITLE_VALUE = 0x2001  //setting项  标题-值
        const val TYPE_SETTING_TITLE_SUBTITLE_VALUE = 0x2002  //标题-子标题-值
        const val TYPE_SETTING_ICON_TITLE_VALUE_GO = 0x2003 //icon-标题-值-可跳转
        const val TYPE_SETTING_TITLE_VALUE_GO = 0x2004 //标题-值-可跳转
        const val TYPE_SETTING_TITLE_SUBTITLE_VALUE_GO = 0x2005 //标题-子标题—值-可跳转
        const val TYPE_SETTING_ICON_TITLE_SUBTITLE_VALUE_GO = 0x2006 //icon-标题-子标题-值-可跳转
        const val TYPE_SETTING_TITLE_SWITCH = 0x2007 //标题-开关
        const val TYPE_SETTING_TITLE_SUBTITLE_SWITCH = 0x2008 //标题-子标题-开关
        const val TYPE_SETTING_LOGIN_OUT = 0x2009 //退出登录

    }

    constructor(type: Int, value: Any?) {
        this.type = type
        this.value = value
    }

    constructor()

    var type = 0
    var value: Any? = null
}