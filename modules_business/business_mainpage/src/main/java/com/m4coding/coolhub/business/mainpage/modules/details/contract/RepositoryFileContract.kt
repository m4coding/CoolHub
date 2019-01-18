package com.m4coding.coolhub.business.mainpage.modules.details.contract

import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.business.base.list.IListView
import io.reactivex.Observable

class RepositoryFileContract {
    interface Model : IModel {
        fun getRepoFiles(owner: String, repo: String, path: String, branch: String): Observable<List<FileBean>>
    }
    interface View : IListView
}