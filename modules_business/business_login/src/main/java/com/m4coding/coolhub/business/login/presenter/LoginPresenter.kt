package com.m4coding.coolhub.business.login.presenter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.exception.ApiException
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.login.contract.LoginContract
import com.m4coding.coolhub.business.login.model.LoginModel
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author mochangsheng
 * @description
 */
class LoginPresenter(rootView: LoginContract.View) : BasePresenter<LoginContract.Model, LoginContract.View>(rootView) {

    init {
        mModel = LoginModel()
    }


    @SuppressLint("CheckResult")
    fun login(username: String?, password: String?) {
        username?.let { password?.let { it1 -> LoginDataSource.login(it, it1) } }
                ?.doOnSubscribe { mRootView?.startLogin() }
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.compose(RxLifecycleUtils.bindToLifecycle(mRootView, ActivityEvent.DESTROY))
                ?.subscribe({
                    mRootView?.onLoginSuccess(it)
                }, {
                    mRootView?.onLoginError(NetExceptionHelper.wrapException(it))
                })
    }
}