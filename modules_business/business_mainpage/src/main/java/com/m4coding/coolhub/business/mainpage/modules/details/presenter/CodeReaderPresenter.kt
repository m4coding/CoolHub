package com.m4coding.coolhub.business.mainpage.modules.details.presenter

import android.annotation.SuppressLint
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.BranchBean
import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.base.utils.StringUtils
import com.m4coding.coolhub.business.base.config.AppUrlConstants
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.modules.details.contract.CodeReaderContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.CodeReaderModel
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers

class CodeReaderPresenter(rootView: CodeReaderContract.View) : BasePresenter<CodeReaderContract.Model, CodeReaderContract.View>(rootView) {

    init {
        mModel = CodeReaderModel()
    }

    @SuppressLint("CheckResult")
    fun loadContent(owner: String, repo: String, fileSha: String) {
        RepoDataSource.getBlob(owner, repo, fileSha)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    mRootView.onGetBlobBegin()
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, ActivityEvent.DESTROY))
                .subscribe({
                    mRootView.onGetBlobSuccess(it)
                }, {
                    it.printStackTrace()
                    mRootView.onGetBlobFail()
                    mRootView.showMessage(NetExceptionHelper.wrapException(it).displayMessage)
                })
    }

    @SuppressLint("CheckResult")
    fun loadMd(repoBean: RepoBean, fileBean: FileBean, branchBean: BranchBean?) {
        RepoDataSource.getFileAsHtmlStream(fileBean.htmlUrl ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    mRootView.onGetBlobBegin()
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, ActivityEvent.DESTROY))
                .subscribe({
                    if (it.isSuccessful) {
                        mRootView.onGetMdSuccess(it.body()?.string() ?: "")
                    } else {
                        mRootView.onGetBlobFail()
                    }
                }, {
                    it.printStackTrace()
                    mRootView.onGetBlobFail()
                    mRootView.showMessage(NetExceptionHelper.wrapException(it).displayMessage)
                })
    }

}