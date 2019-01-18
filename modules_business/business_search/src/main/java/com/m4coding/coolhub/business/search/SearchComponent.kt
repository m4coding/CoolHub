package com.m4coding.coolhub.business.search

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.search.ui.activity.SearchActivity

/**
 * @author mochangsheng
 * @description  搜索组件通信类
 */
class SearchComponent : IComponent {

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        when(actionName) {
            ComponentConstants.SEARCH_ACTION_SHOW -> {
                cc.context?.let {
                    SearchActivity.newInstance(it)
                    CC.sendCCResult(cc.callId, CCResult.success())}
            }
            else -> {
                CC.sendCCResult(cc?.callId, CCResult.error("has not support for action:" + cc?.actionName))
            }
        }

        return false
    }

    override fun getName(): String {
        return ComponentConstants.SEARCH
    }

}