package com.m4coding.coolhub.api.datasource

import com.m4coding.coolhub.api.datasource.bean.HotDataBean
import com.m4coding.coolhub.api.datasource.bean.RecommendBean
import com.m4coding.coolhub.api.datasource.service.GithubWebPageService
import com.m4coding.coolhub.api.misc.GithubHtmlUtils
import com.m4coding.coolhub.base.manager.RepositoryManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * @author mochangsheng
 * @description
 */
object GithubWebPageDataSource {

    fun login(username: String, password: String): Observable<Response<ResponseBody>> {
        val webpageService = RepositoryManager.getInstance().obtainRetrofitService(GithubWebPageService::class.java)

        return webpageService.getLoginPage()
                .subscribeOn(Schedulers.io())
                .flatMap { it ->
                    if (it.raw().request().url().toString() == "https://github.com/") {
                        //get login，如果是登录状态会重定向到github.com
                        Observable.just(it)
                    } else {
                        val map: HashMap<String, String> =  HashMap()
                        map["authenticity_token"] = GithubHtmlUtils.parseAuthenticityToken(it.body()?.string() ?: "") ?: ""
                        map["login"] = username
                        map["password"] = password
                        map["utf8"] = "✓"
                        map["commit"] = "Sign in"
                        webpageService.postSession(map)
                    }
                }
    }

    fun getDiscover(index: Int): Observable<List<RecommendBean>> {
        val webpageService = RepositoryManager.getInstance().obtainRetrofitService(GithubWebPageService::class.java)

        return webpageService.getDiscover(index)
                .subscribeOn(Schedulers.io())
                .map { it ->
                    GithubHtmlUtils.parseDiscoverData(it.body()?.string() ?: "")
                } .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTrending(language: String, since: String): Observable<List<HotDataBean>> {
        val webpageService = RepositoryManager.getInstance().obtainRetrofitService(GithubWebPageService::class.java)

        return webpageService.getTrending(language, since)
                .subscribeOn(Schedulers.io())
                .map { it ->
                    GithubHtmlUtils.parseTrendData(it.body()?.string() ?: "", since)
                } .observeOn(AndroidSchedulers.mainThread())
    }
}