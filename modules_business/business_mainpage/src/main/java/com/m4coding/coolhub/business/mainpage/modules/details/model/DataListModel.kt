package com.m4coding.coolhub.business.mainpage.modules.details.model

import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.UserDataSource
import com.m4coding.coolhub.api.datasource.bean.*
import com.m4coding.coolhub.business.mainpage.modules.details.contract.DataListContract
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DataListModel : DataListContract.Model {

    override fun getFollowers(username: String, page: Int): Observable<List<FollowBean>> {
        return UserDataSource.getFollowers(username, page)
    }

    override fun getFollowing(username: String, page: Int): Observable<List<FollowBean>> {
        return UserDataSource.getFollowing(username, page)
    }

    override fun getStarers(username: String, repo: String, page: Int): Observable<List<UserBean>> {
        return RepoDataSource.getStargazers(username, repo, page)

    }

    override fun getForkers(username: String, repo: String, page: Int): Observable<List<RepoBean>> {
        return RepoDataSource.getForks(username, repo, page)
    }

    override fun getWatchers(username: String, repo: String, page: Int): Observable<List<UserBean>> {
        return RepoDataSource.getWatchers(username, repo, page)
    }

    override fun getCloseIssue(username: String, repo: String, page: Int): Observable<List<IssueBean>> {
        return RepoDataSource.getRepoIssues(username, repo, "closed", "created", "desc", page)
    }

    override fun getOpenIssue(username: String, repo: String, page: Int): Observable<List<IssueBean>> {
        return RepoDataSource.getRepoIssues(username, repo, "open", "created", "desc", page)
    }

    override fun getIssueTimeLine(username: String, repo: String, issueNumber: Int, page: Int): Observable<List<IssueEventBean>> {
        return RepoDataSource.getIssueTimeline(username, repo, issueNumber, page)
    }

    override fun onDestroy() {

    }
}