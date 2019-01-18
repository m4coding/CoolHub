package com.m4coding.coolhub.business.mainpage.modules.main.presenter

import com.m4coding.coolhub.api.datasource.GithubWebPageDataSource
import com.m4coding.coolhub.api.datasource.bean.RecommendBean
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.main.contract.RecommendContract
import com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter.RecommendAdapter
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class RecommendPresenter(rootView: RecommendContract.View) : BaseListContentPresenter<RecommendBean,
        RecommendContract.Model, RecommendContract.View>(rootView) {

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<RecommendBean>> {
        return GithubWebPageDataSource.getDiscover(dataIndex).
                map{
                    val limitSize = 100
                    if ((mList?.size ?: 0) >= limitSize) { //限制100个
                        ArrayList()
                    } else {
                        it
                    }
                }
    }


    override fun initAdapter() {
        mAdapter = RecommendAdapter(mList)
    }
}