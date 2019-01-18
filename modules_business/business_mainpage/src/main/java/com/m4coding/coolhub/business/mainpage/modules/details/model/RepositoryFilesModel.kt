package com.m4coding.coolhub.business.mainpage.modules.details.model

import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryFileContract
import io.reactivex.Observable

class RepositoryFilesModel : RepositoryFileContract.Model {
    override fun getRepoFiles(owner: String, repo: String, path: String, branch: String): Observable<List<FileBean>> {
        return RepoDataSource.getRepoFiles(owner, repo, path, branch)
    }


    override fun onDestroy() {

    }

}