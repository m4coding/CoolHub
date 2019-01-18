package com.m4coding.coolhub.business.mainpage.modules.details.presenter

import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryListContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.RepositoryListModel
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.RepositoryListAdapter
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryListPresenter(view: RepositoryListContract.View) :
        BaseListContentPresenter<RepoBean,RepositoryListContract.Model, RepositoryListContract.View>(view) {

    init {
        mModel = RepositoryListModel()
    }

    override fun initAdapter() {
        mAdapter = RepositoryListAdapter(mList)
    }

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<RepoBean>> {
        return mModel.getRepository(map[ParamsMapKey.USER_NAME] as String, page)
    }
}