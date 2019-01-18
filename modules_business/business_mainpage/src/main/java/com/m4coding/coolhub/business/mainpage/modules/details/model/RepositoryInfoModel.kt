package com.m4coding.coolhub.business.mainpage.modules.details.model

import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryInfoContract
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryInfoModel : RepositoryInfoContract.Model {

    override fun getRepoReadme(url: String): Observable<Response<ResponseBody>> {
        return RepoDataSource.getFileAsHtmlStream(url)
    }

    override fun onDestroy() {
    }
}