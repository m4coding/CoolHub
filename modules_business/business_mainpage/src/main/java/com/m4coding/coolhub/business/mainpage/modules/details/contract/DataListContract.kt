package com.m4coding.coolhub.business.mainpage.modules.details.contract

import com.m4coding.coolhub.api.datasource.bean.*
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.business.base.list.IListView
import io.reactivex.Observable

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DataListContract {
    interface View : IListView {
        /**
         * 获取特定值，根据type需要，可用于改变List
         */
        fun getSpecial(): Any?
    }

    interface Model : IModel {
        /**
         *  获取粉丝
         */
        fun getFollowers(username: String, page: Int): Observable<List<FollowBean>>

        /**
         * 获取关注
         */
        fun getFollowing(username: String, page: Int): Observable<List<FollowBean>>


        /**
         * 获取仓库点赞者
         */
        fun getStarers(username: String, repo: String, page: Int): Observable<List<UserBean>>

        /**
         * 获取仓库forkers
         */
        fun getForkers(username: String, repo: String, page: Int): Observable<List<RepoBean>>

        /**
         * 获取仓库watchers
         */
        fun getWatchers(username: String, repo: String, page: Int): Observable<List<UserBean>>

        /**
         * 获取仓库open issue
         */
        fun getOpenIssue(username: String, repo: String, page: Int): Observable<List<IssueBean>>

        /**
         * 获取仓库close issue
         */
        fun getCloseIssue(username: String, repo: String, page: Int): Observable<List<IssueBean>>

        /**
         * 获取仓库issue timeline
         */
        fun getIssueTimeLine(username: String, repo: String, issueNumber: Int, page: Int): Observable<List<IssueEventBean>>
    }
}