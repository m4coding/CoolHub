package com.m4coding.coolhub.api.datasource

import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.datasource.bean.SearchResultBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.api.datasource.service.SearchService
import com.m4coding.coolhub.base.manager.RepositoryManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * @author mochangsheng
 * @description 搜索服务数据源
 */
object SearchDataSource : SearchService {

    override fun searchUsers(query: String, sort: String, order: String, page: Int): Observable<SearchResultBean<UserBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(SearchService::class.java)
        return repoService.searchUsers(query, sort, order, page)
                .subscribeOn(Schedulers.io())
    }

    override fun searchRepos(query: String, sort: String, order: String, page: Int): Observable<SearchResultBean<RepoBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(SearchService::class.java)
        return repoService.searchRepos(query, sort, order, page)
                .subscribeOn(Schedulers.io())
    }

}