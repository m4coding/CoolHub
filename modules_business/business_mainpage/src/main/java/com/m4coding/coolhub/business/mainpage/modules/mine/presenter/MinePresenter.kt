package com.m4coding.coolhub.business.mainpage.modules.mine.presenter

import android.annotation.SuppressLint
import android.text.TextUtils
import com.m4coding.coolhub.api.datasource.UserDataSource
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.mine.contract.MineContract
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author mochangsheng
 * @description
 */
class MinePresenter(rootView: MineContract.View) : BasePresenter<MineContract.Model, MineContract.View>(rootView) {

    private var mUserBean: UserBean? = null

    @SuppressLint("CheckResult")
    fun getMineInfo() {
        UserDataSource.getMineInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, FragmentEvent.PAUSE))
                .doOnSubscribe {
                    mRootView.onLoadingMineInfo()
                }
                .subscribe({
                    mUserBean = it
                    mRootView.onSuccessMineInfo(it)
                }, {
                    it.printStackTrace()
                    mRootView.onFailMineInfo()
                })
    }

    fun goUserDetails() {
        val context = mRootView.getOwnContext()
        val username: String = mUserBean?.login ?: ""
        if (!TextUtils.isEmpty(username) && context != null) {
            UserDetailsActivity.newInstance(context, username)
        }
    }
}