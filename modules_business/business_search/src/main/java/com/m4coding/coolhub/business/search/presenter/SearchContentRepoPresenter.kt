package com.m4coding.coolhub.business.search.presenter

import com.m4coding.coolhub.api.datasource.SearchDataSource
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.search.contract.SearchContentRepoContract
import com.m4coding.coolhub.business.search.ui.adapter.SearchContentRepoAdapter
import io.reactivex.Observable

class SearchContentRepoPresenter(rootView: SearchContentRepoContract.View) :
        BaseListContentPresenter<RepoBean, SearchContentRepoContract.Model, SearchContentRepoContract.View>(rootView) {


    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<RepoBean>> {

        if(mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }

        var query = map[ParamsMapKey.SEARCH_QUERY] as String
        val language = map[ParamsMapKey.SEARCH_LANGUAGE] as String
        if (language.isNotEmpty()) {
            query += "+language:$language"
        }
        val sort = map[ParamsMapKey.SEARCH_SORT] as String
        val order = map[ParamsMapKey.SEARCH_ORDER] as String
        return SearchDataSource.searchRepos(query, sort, order, page).map {
            it.items
        }
    }

    override fun initAdapter() {
        mAdapter = SearchContentRepoAdapter(mList)
    }

}