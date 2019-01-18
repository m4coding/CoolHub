package com.m4coding.coolhub.api.datasource.service

import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.api.datasource.bean.FollowBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author mochangsheng
 * @description 用户相关服务
 */
interface UserService {

    /**
     * 获取个人信息
     */
    @GET("user")
    fun getMineInfo(): Observable<UserBean>

    /**
     * 获取特定用户信息
     */
    @GET("users/{username}")
    fun getUserInfo(@Path("username") username: String): Observable<UserBean>


    /**
     * 获取特定用户跟随的其他用户信息
     */
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String, @Query("page") page: Int): Observable<List<FollowBean>>

    /**
     * 获取个人跟随的其他用户信息 （需要登录）
     */
    @GET("user/following")
    fun getMineFollowing(@Query("page") page: Int): Observable<List<FollowBean>>

    /**
     * 获取特定用户被跟随信息
     */
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String, @Query("page") page: Int): Observable<List<FollowBean>>

    /**
     * 获取个人被跟随信息 （需要登录）
     */
    @GET("user/followers")
    fun getMineFollowers(@Query("page") page: Int): Observable<List<FollowBean>>


    /**
     * 跟随特定用户
     */
    @PUT("user/following/{username}")
    fun following(@Path("username") username: String): Observable<Response<ResponseBody>>

    /**
     * 取消跟随特定用户
     */
    @DELETE("user/following/{username}")
    fun unFollow(@Path("username") username: String): Observable<Response<ResponseBody>>


    /**
     * 获取接受到的事件  （例如github首页中的browse动态信息 关注者的动态事件）
     */
    @GET("users/{username}/received_events")
    fun getReceivedEvents(@Path("username") username: String, @Query("page") page: Int): Observable<List<EventBean>>

    /**
     * 获取用户事件  （username用户的动态事件）
     */
    @GET("users/{username}/events")
    fun getEvents(@Path("username") username: String, @Query("page") page: Int): Observable<List<EventBean>>
}