package com.m4coding.coolhub.business.login.contract

import com.m4coding.coolhub.api.datasource.bean.AuthorizationBean
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.api.exception.ApiException
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.mvp.IView

/**
 * @author mochangsheng
 * @description
 */
class LoginContract {

    interface View : IView {
        fun onLoginSuccess(authUser: AuthUser)
        fun onLoginError(apiException: ApiException?)
        fun startLogin()
    }
    interface Model : IModel
}