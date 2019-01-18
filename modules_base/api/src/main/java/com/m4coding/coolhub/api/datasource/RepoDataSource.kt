package com.m4coding.coolhub.api.datasource

import com.m4coding.coolhub.api.datasource.bean.*
import com.m4coding.coolhub.api.datasource.service.RepositoryService
import com.m4coding.coolhub.base.manager.RepositoryManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author mochangsheng
 * @description 仓库数据源
 */
object RepoDataSource : RepositoryService {

    override fun getMineRepo(page: Int): Observable<List<RepoBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getMineRepo(page)
                .subscribeOn(Schedulers.io())
    }

    override fun getUserRepo(user: String, page: Int): Observable<List<RepoBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getUserRepo(user, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getRepoInfo(user: String, repo: String): Observable<RepoBean> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getRepoInfo(user, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun getRepoReadme(user: String, repo: String, branch: String): Observable<FileBean> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getRepoReadme(user, repo, branch)
                .subscribeOn(Schedulers.io())
    }

    override fun getFileAsHtmlStream(url: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getFileAsHtmlStream(url)
                .subscribeOn(Schedulers.io())
    }

    override fun getRepoFiles(owner: String, repo: String, path: String, branch: String): Observable<List<FileBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getRepoFiles(owner, repo, path, branch)
                .subscribeOn(Schedulers.io())
    }


    override fun getStargazers(username: String, repo: String, page: Int): Observable<List<UserBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getStargazers(username, repo, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getWatchers(username: String, repo: String, page: Int): Observable<List<UserBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getWatchers(username, repo, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getForks(username: String, repo: String, page: Int): Observable<List<RepoBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getForks(username, repo, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getRepoIssues(username: String, repo: String, state: String, sort: String, direction: String, page: Int): Observable<List<IssueBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getRepoIssues(username, repo, state, sort, direction, page)
                .subscribeOn(Schedulers.io())
    }


    override fun getIssueTimeline(owner: String, repo: String, issueNumber: Int, page: Int): Observable<List<IssueEventBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getIssueTimeline(owner, repo, issueNumber, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getIssueByNumber(owner: String, repo: String, issueNumber: Int): Observable<IssueBean> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getIssueByNumber(owner, repo, issueNumber)
                .subscribeOn(Schedulers.io())
    }

    override fun starRepo(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.starRepo(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun unstarRepo(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.unstarRepo(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun checkRepoStarred(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.checkRepoStarred(owner, repo)
                .subscribeOn(Schedulers.io())
    }


    override fun checkRepoWatched(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.checkRepoWatched(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun watchRepo(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.watchRepo(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun unwatchRepo(owner: String, repo: String): Observable<Response<ResponseBody>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.unwatchRepo(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun getBranches(owner: String, repo: String): Observable<List<BranchBean>> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getBranches(owner, repo)
                .subscribeOn(Schedulers.io())
    }

    override fun getBlob(owner: String, repo: String, fileSha: String): Observable<BlobBean> {
        val repoService = RepositoryManager.getInstance().obtainRetrofitService(RepositoryService::class.java)
        return repoService.getBlob(owner, repo, fileSha)
                .subscribeOn(Schedulers.io())
    }

}