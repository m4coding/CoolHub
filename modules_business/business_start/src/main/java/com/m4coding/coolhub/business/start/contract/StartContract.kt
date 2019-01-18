package com.m4coding.coolhub.business.start.contract

import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.mvp.IView
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class StartContract {

    interface View : IView {
        /**
         * 跳转到登录页时的回调
         */
        fun onGotoLoginPage()

        /**
         * 跳转到首页时的回调
         */
        fun onGotoMainPage(authUser: AuthUser)
    }

    interface Model : IModel {

    }
}