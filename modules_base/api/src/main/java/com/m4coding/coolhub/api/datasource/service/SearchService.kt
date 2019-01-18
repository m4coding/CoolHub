package com.m4coding.coolhub.api.datasource.service

import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.datasource.bean.SearchResultBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import retrofit2.http.GET
import io.reactivex.Observable
import retrofit2.http.Query


/**
 * 搜索相关Service
 */
interface SearchService {

    /**
     * 搜索用户
     */
    @GET("search/users")
    fun searchUsers(
            @Query(value="q", encoded = true) query: String,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("page") page: Int): Observable<SearchResultBean<UserBean>>

    /**
     * 搜索仓库
     */
    @GET("search/repositories")
    fun searchRepos(
            @Query(value="q", encoded = true) query: String,
            @Query("sort") sort: String,
            @Query("order") order: String,
            @Query("page") page: Int): Observable<SearchResultBean<RepoBean>>
}