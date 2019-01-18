package com.m4coding.coolhub.business.search.presenter

import com.m4coding.coolhub.api.datasource.SearchDataSource
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.search.contract.SearchContentUserContract
import com.m4coding.coolhub.business.search.ui.adapter.SearchContentUserAdapter
import io.reactivex.Observable

class SearchContentUserPresenter(rootView: SearchContentUserContract.View) :
        BaseListContentPresenter<UserBean, SearchContentUserContract.Model, SearchContentUserContract.View>(rootView) {


    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<UserBean>> {
        if(mDisposable?.isDisposed == false) {
            mDisposable?.dispose()
        }

        val query = map[ParamsMapKey.SEARCH_QUERY] as String
        val sort = map[ParamsMapKey.SEARCH_SORT] as String
        val order = map[ParamsMapKey.SEARCH_ORDER] as String
        return SearchDataSource.searchUsers(query, sort, order, page).map {
            it.items
        }
    }

    override fun initAdapter() {
        mAdapter = SearchContentUserAdapter(mList)
    }

}