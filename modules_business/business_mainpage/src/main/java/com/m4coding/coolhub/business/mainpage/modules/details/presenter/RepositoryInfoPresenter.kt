package com.m4coding.coolhub.business.mainpage.modules.details.presenter

import android.annotation.SuppressLint
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.base.utils.StringUtils
import com.m4coding.coolhub.business.base.config.AppUrlConstants
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryInfoContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.RepositoryInfoModel
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryInfoPresenter(rootView: RepositoryInfoContract.View) :
        BasePresenter<RepositoryInfoContract.Model, RepositoryInfoContract.View>(rootView) {

    init {
        mModel = RepositoryInfoModel()
    }


    @SuppressLint("CheckResult")
    fun getRepoReadme(isPullRefresh: Boolean, user: String, repo: String, branch: String) {

        val readmeFileUrl = (AppUrlConstants.GITHUB_API_BASE_URL + "repos/$user/$repo/readme"
                + if (StringUtils.isBlank(branch)) "" else "?ref=$branch")

        mModel.getRepoReadme(readmeFileUrl)
                .doOnSubscribe {
                    mRootView.onGetInfoBegin(isPullRefresh)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, FragmentEvent.DESTROY))
                .subscribe({
                    mRootView.onGetInfoSuccess(it.body()?.string() ?: "")
                }, {
                    mRootView.onGetInfoError(isPullRefresh)
                })
    }
}