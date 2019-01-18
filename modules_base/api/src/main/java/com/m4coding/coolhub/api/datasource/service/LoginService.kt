package com.m4coding.coolhub.api.datasource.service

import android.support.annotation.NonNull
import com.m4coding.coolhub.api.datasource.bean.AuthRequestBean
import com.m4coding.coolhub.api.datasource.bean.AuthorizationBean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @author mochangsheng
 * @description 登录服务
 */
interface LoginService {

    /**
     * 获取授权的token bean
     */
    @POST("authorizations")
    @Headers("Accept: application/json")
    fun login(@Header("Authorization") authHeader: String, @NonNull @Body authRequestBean: AuthRequestBean): Observable<AuthorizationBean>
}