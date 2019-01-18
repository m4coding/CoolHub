package com.m4coding.coolhub.business.login

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.datasource.dao.ApiDBManager
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.login.ui.activity.LoginActivity

/**
 * @author mochangsheng
 * @description
 */
class LoginComponent : IComponent {

    override fun onCall(cc: CC?): Boolean {
        val actionName = cc?.actionName
        when(actionName) {
            ComponentConstants.LOGIN_ACTION_SHOW -> {
                cc.context?.let {
                    LoginActivity.newInstance(it)
                    CC.sendCCResult(cc.callId, CCResult.success())}
            }
            else -> {
                CC.sendCCResult(cc?.callId, CCResult.error("has not support for action:" + cc?.actionName))
            }
        }

        return false
    }

    override fun getName(): String {
        return ComponentConstants.LOGIN
    }

}