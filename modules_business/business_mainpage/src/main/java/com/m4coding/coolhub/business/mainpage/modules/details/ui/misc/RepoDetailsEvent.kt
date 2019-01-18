package com.m4coding.coolhub.business.mainpage.modules.details.ui.misc

/**
 * @author mochangsheng
 * @description 仓库详情eventBus事件定义
 */
class RepoDetailsEvent(type: Int, value: Any?) {

    companion object {
        const val TYPE_INVALID = 0x00
        const val TYPE_UPDATE_BRANCH = 0x01 //分支切换
    }

    var type: Int = type
    var value: Any? = value
}