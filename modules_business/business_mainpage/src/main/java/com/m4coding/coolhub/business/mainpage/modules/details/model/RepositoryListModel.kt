package com.m4coding.coolhub.business.mainpage.modules.details.model

import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryListContract
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryListModel : RepositoryListContract.Model {

    override fun getRepository(username: String, page: Int): Observable<List<RepoBean>> {
        return LoginDataSource.getLoginUserInfo().flatMap {
            //判断是不是本人
            if (it.userName == username) {
                RepoDataSource.getMineRepo(page)
            } else {
                RepoDataSource.getUserRepo(username, page)
            }
        }
    }

    override fun onDestroy() {

    }

}