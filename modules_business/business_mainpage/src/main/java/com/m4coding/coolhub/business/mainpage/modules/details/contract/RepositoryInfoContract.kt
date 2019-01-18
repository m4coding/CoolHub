package com.m4coding.coolhub.business.mainpage.modules.details.contract

import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.mvp.IView
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author mochangsheng
 * @description 仓库信息Contract
 */
class RepositoryInfoContract {

    interface View : IView {
        fun onGetInfoBegin(isPullRefresh: Boolean)
        fun onGetInfoSuccess(content: String)
        fun onGetInfoError(isPullRefresh: Boolean)
    }

    interface Model : IModel {
        fun getRepoReadme(url: String): Observable<Response<ResponseBody>>
    }
}