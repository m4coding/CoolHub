package com.m4coding.coolhub.business.start.presenter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.business.start.contract.StartContract
import com.m4coding.coolhub.business.start.model.StartModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import com.m4coding.coolhub.api.datasource.dao.ApiDBManager
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils


/**
 * @author mochangsheng
 * @description
 */
class StartPresenter(rooView: StartContract.View) : BasePresenter<StartContract.Model, StartContract.View>(rooView) {

    init {
        mModel = StartModel()
    }

    @SuppressLint("CheckResult")
    fun start() {
        LoginDataSource.getLoginUserInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        //跳转首页
                        mRootView?.onGotoMainPage(it)
                    } else {
                        goToLoginPage()
                    }
                 }, {
                    it.printStackTrace()
                    goToLoginPage()
                })
    }

    private fun goToLoginPage() {
        //跳转登录页
        val cc = CC.obtainBuilder(ComponentConstants.LOGIN)
                .setActionName(ComponentConstants.LOGIN_ACTION_SHOW)
                .build()
        PrintComponentMsgUtils.showResult(cc, cc.call())

        mRootView?.onGotoLoginPage()
    }

}