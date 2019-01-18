package com.m4coding.coolhub.api.datasource

import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.api.datasource.bean.FollowBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.api.datasource.service.UserService
import com.m4coding.coolhub.base.manager.RepositoryManager
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author mochangsheng
 * @description 用户服务数据源
 */
object UserDataSource : UserService {

    /**
     * 获取接受到的事件  （例如github首页中的browse动态信息）
     */
    override fun getReceivedEvents(username: String, page: Int): Observable<List<EventBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getReceivedEvents(username, page)
                .subscribeOn(Schedulers.io())
    }

    /**
     * 获取用户事件
     */
    override fun getEvents(username: String, page: Int): Observable<List<EventBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getEvents(username, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getUserInfo(username: String): Observable<UserBean> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getUserInfo(username)
                .subscribeOn(Schedulers.io())
    }

    override fun getMineInfo(): Observable<UserBean> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getMineInfo()
                .subscribeOn(Schedulers.io())
    }

    override fun getMineFollowing(page: Int): Observable<List<FollowBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getMineFollowing(page)
                .subscribeOn(Schedulers.io())
    }

    override fun getFollowing(username: String, page: Int): Observable<List<FollowBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getFollowing(username, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getMineFollowers(page: Int): Observable<List<FollowBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getMineFollowers(page)
                .subscribeOn(Schedulers.io())
    }

    override fun getFollowers(username: String, page: Int): Observable<List<FollowBean>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.getFollowers(username, page)
                .subscribeOn(Schedulers.io())
    }


    override fun following(username: String): Observable<Response<ResponseBody>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.following(username)
                .subscribeOn(Schedulers.io())
    }

    override fun unFollow(username: String): Observable<Response<ResponseBody>> {
        val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
        return userService.unFollow(username)
                .subscribeOn(Schedulers.io())
    }

}