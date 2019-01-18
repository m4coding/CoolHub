package com.m4coding.coolhub.api.datasource

import android.annotation.SuppressLint
import android.text.TextUtils
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.google.gson.Gson
import com.m4coding.coolhub.api.datasource.bean.AuthRequestBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.api.datasource.dao.ApiDBManager
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.api.misc.Utils
import com.m4coding.coolhub.api.datasource.service.LoginService
import com.m4coding.coolhub.api.datasource.service.UserService
import com.m4coding.coolhub.api.interceptor.AuthInterceptor
import com.m4coding.coolhub.base.helper.RetrofitHelper
import com.m4coding.coolhub.base.manager.RepositoryManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * @author mochangsheng
 * @description 登录服务数据源
 */
object LoginDataSource {

    @SuppressLint("CheckResult")
            /**
     * 登录
     */
    fun login(username: String, password: String): Observable<AuthUser> {
        val loginService = RepositoryManager.getInstance().obtainRetrofitService(LoginService::class.java)

        val authRequestBean = AuthRequestBean.create()
        val token = Utils.basicAuthorizationHeader(username, password)

        val authHeaderValue = if (token.startsWith("Basic")) token else "token $token"

        val authUser = AuthUser()

        //网页登录
        GithubWebPageDataSource.login(username, password).subscribe({ }, { it.printStackTrace()})

        return loginService.login(authHeaderValue, authRequestBean)
                .subscribeOn(Schedulers.io())
                .flatMap { it ->  //lamba输入
                    authUser.token = it.token
                    authUser.scopes = Gson().toJson(it.scopes)
                    AuthInterceptor.instance.setToken(it.token)
                    val userService = RepositoryManager.getInstance().obtainRetrofitService(UserService::class.java)
                    userService.getMineInfo()  //lamba输出,不用添加return
                }
                .map { t1: UserBean ->
                    authUser.userName = t1.login
                    authUser.userAvatar = t1.avatarUrl
                    ApiDBManager.getInstance().insertAuthUser(authUser)
                    authUser
                }
    }

    /**
     * 退出登录
     */
    fun logout(): Observable<Boolean> {
        return Observable.create<Boolean> {
                    //清理cookie
                    val cookieJar = RetrofitHelper.getInstance().client.cookieJar()
                    if (cookieJar is ClearableCookieJar) {
                        cookieJar.clear()
                    }
                    //清空token
                    AuthInterceptor.instance.setToken(null)
                    ApiDBManager.getInstance().deleteAuthUser()
                    it.onNext(true)
                    it.onComplete()
                }
                .subscribeOn(Schedulers.io())
    }

    /**
     * 是否已登录
     */
    fun isLogin(): Observable<Boolean> {
        return Observable.create<Boolean> {
            val authUser = ApiDBManager.getInstance().authUser
            val isLogin = !TextUtils.isEmpty(authUser?.token)
            it.onNext(isLogin)
            it.onComplete()
        }
    }

    /**
     * 获取登录用户信息
     */
    fun getLoginUserInfo(): Observable<AuthUser> {
        return Observable.create<AuthUser> {
            val authUser = ApiDBManager.getInstance().authUser
            AuthInterceptor.instance.setToken(authUser.token)
            it.onNext(authUser)
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }
}