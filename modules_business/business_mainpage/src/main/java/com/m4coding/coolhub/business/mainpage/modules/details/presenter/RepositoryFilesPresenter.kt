package com.m4coding.coolhub.business.mainpage.modules.details.presenter

import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryFileContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.RepositoryFilesModel
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.RepositoryDetailsFilesAdapter
import io.reactivex.Observable

class RepositoryFilesPresenter(view: RepositoryFileContract.View) : BaseListContentPresenter<FileBean,
        RepositoryFileContract.Model, RepositoryFileContract.View>(view) {

    init {
        mModel = RepositoryFilesModel()
    }

    override fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<FileBean>> {
        val owner = map[ParamsMapKey.USER_NAME]
        val repo = map[ParamsMapKey.REPO]
        val path = map[ParamsMapKey.PATH]
        var branch = map[ParamsMapKey.BRANCH]
        return mModel.getRepoFiles(owner as? String ?: "",
                repo as? String ?: "", path as? String ?: "", branch as? String ?: "")
    }


    override fun initAdapter() {
        mAdapter = RepositoryDetailsFilesAdapter(mList)
    }
}