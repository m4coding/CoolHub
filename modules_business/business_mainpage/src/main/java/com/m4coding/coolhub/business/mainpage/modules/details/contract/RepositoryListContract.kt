package com.m4coding.coolhub.business.mainpage.modules.details.contract

import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.business.base.list.IListView
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryListContract {
    interface View : IListView
    interface Model: IModel {
        fun getRepository(username: String, page: Int): Observable<List<RepoBean>>
    }
}