package com.m4coding.coolhub.business.search.misc

/**
 * 搜索相关事件（EventBus)
 */
class SearchEvent(var type: Int, var value: Any?) {
    companion object {
        const val TYPE_INVALID = 0x00  //无效类型
        const val TYPE_INPUT= 0x01 //输入
        const val TYPE_SEARCH= 0x02 //搜索
    }
}