package com.m4coding.coolhub.api.datasource.service

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * @author mochangsheng
 * @description  github网页内容获取
 */
interface GithubWebPageService {

    companion object {
        const val BASE_URL = "https://github.com"
    }

    /**
     * 获取登录网页的内容
     */
    @GET("https://github.com/login")
    fun getLoginPage(): Observable<Response<ResponseBody>>


    @POST("https://github.com/session")
    @FormUrlEncoded
    fun postSession(@FieldMap map: Map<String, String>): Observable<Response<ResponseBody>>

    /**
     * 获取发现页的内容
     */
    @GET("https://github.com/discover")
    fun getDiscover(@Query("recommendations_after") index: Int): Observable<Response<ResponseBody>>

    @GET("https://github.com/discover")
    fun getDiscover(): Observable<Response<ResponseBody>>

    /**
     * 获取趋势内容
     * @param language  指定要获取的语言
     * @param since   时间   （daily  weekly  monthly）
     */
    @GET("https://github.com/trending/{language}")
    fun getTrending(@Path(value = "language", encoded = true) language: String,
                    @Query("since") since: String): Observable<Response<ResponseBody>>
}