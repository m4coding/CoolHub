package com.m4coding.coolhub.business.mainpage

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity

/**
 * @author mochangsheng
 * @description  首页组件通信类
 */
class MainPageComponent : IComponent {

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        when(actionName) {
            ComponentConstants.HOMEPAGE_ACTION_SHOW -> {
                cc.context?.let {
                    MainPageActivity.newInstance(it)
                    CC.sendCCResult(cc.callId, CCResult.success())}
            }
            ComponentConstants.REPOSITORY_DETAILS_ACTION_SHOW -> {
                cc.context?.let {
                    RepositoryDetailsActivity.newInstance(it,
                            cc.getParamItem<String>(ParamsMapKey.USER_NAME),
                            cc.getParamItem<String>(ParamsMapKey.REPO))
                    CC.sendCCResult(cc.callId, CCResult.success())}
            }
            ComponentConstants.USER_DETAILS_ACTION_SHOW -> {
                cc.context?.let {
                    UserDetailsActivity.newInstance(it, cc.getParamItem<String>(ParamsMapKey.USER_NAME))
                    CC.sendCCResult(cc.callId, CCResult.success())}
            }
            else -> {
                CC.sendCCResult(cc?.callId, CCResult.error("has not support for action:" + cc?.actionName))
            }
        }

        return false
    }

    override fun getName(): String {
        return ComponentConstants.HOMEPAGE
    }

}