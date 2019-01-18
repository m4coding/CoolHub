package com.m4coding.coolhub.business.mainpage.modules.hot.presenter

import com.m4coding.coolhub.api.datasource.GithubWebPageDataSource
import com.m4coding.coolhub.api.datasource.bean.HotDataBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.hot.contract.HotContract
import com.m4coding.coolhub.business.mainpage.modules.hot.ui.adapter.HotAdapter
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description
 */
class HotPresenter(rootView: HotContract.View) :
        BaseListContentPresenter<HotDataBean, HotContract.Model, HotContract.View>(rootView) {

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>):
            Observable<List<HotDataBean>> {
        var language = ""
        var since = ""
        if ( map[ParamsMapKey.LANGUAGE] is String) {
            language = map[ParamsMapKey.LANGUAGE] as String
        }
        if ( map[ParamsMapKey.SINCE] is String) {
            since = map[ParamsMapKey.SINCE] as String
        }
        return GithubWebPageDataSource.getTrending(language, since)
    }

    override fun initAdapter() {
        mAdapter = HotAdapter(mList)
    }
}