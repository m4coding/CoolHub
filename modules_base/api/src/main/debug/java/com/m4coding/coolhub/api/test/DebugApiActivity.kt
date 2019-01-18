package com.m4coding.coolhub.api.test

import android.view.View
import com.m4coding.coolhub.api.BuildConfig
import com.m4coding.coolhub.api.R
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.datasource.UserDataSource
import com.m4coding.coolhub.api.datasource.dao.ApiDBManager
import com.m4coding.coolhub.base.base.BaseActivity
import com.m4coding.coolhub.base.base.BaseApplication
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_debug_api.*
import okhttp3.*
import java.io.IOException
import okhttp3.Cookie
import android.R.attr.host
import android.annotation.SuppressLint
import com.m4coding.coolhub.api.datasource.GithubWebPageDataSource
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.misc.GithubHtmlUtils
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.base.utils.log.MLog
import okhttp3.HttpUrl
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


/**
 * @author mochangsheng
 * @description Api调试
 */
class DebugApiActivity : BaseActivity(), View.OnClickListener {


    override fun initView() {
        setContentView(R.layout.activity_debug_api)
    }

    override fun initData() {
        bt_test_login.setOnClickListener(this)
        bt_test_mine.setOnClickListener(this)
        bt_test_user.setOnClickListener(this)
        bt_read_login_info.setOnClickListener(this)
        bt_test_logout.setOnClickListener(this)
        bt_test_event.setOnClickListener(this)
        bt_test_recommend.setOnClickListener(this)
        bt_test_trend.setOnClickListener(this)
        bt_test_star.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.bt_test_login -> {
                LoginDataSource.login(BuildConfig.MY_USERNAME, BuildConfig.MY_PASSWORD)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ authorizationBean ->
                            tv_response.text = authorizationBean.toString()
                        }, {
                                    it.printStackTrace()
                                    tv_response.text = LogUtils.obtainExceptionInfo(it) })
            }
            R.id.bt_test_mine -> {
                UserDataSource.getMineInfo()
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    tv_response.text = it.toString()
                },{
                    tv_response.text = LogUtils.obtainExceptionInfo(it)
                })
            }
            R.id.bt_test_user -> {
                UserDataSource.getUserInfo("NiuBi")
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    tv_response.text = it.toString()
                },{
                    tv_response.text = LogUtils.obtainExceptionInfo(it)
                })
            }
            R.id.bt_read_login_info -> {
                tv_response.text = ApiDBManager.getInstance().authUser?.toString()
            }
            R.id.bt_test_logout -> {
                LoginDataSource.logout().observeOn(AndroidSchedulers.mainThread()).subscribe({
                    if (it) {
                        tv_response.text = "登录退出成功"
                    }
                }, {
                    tv_response.text = LogUtils.obtainExceptionInfo(it)
                })
            }
            R.id.bt_test_event -> {
                UserDataSource.getReceivedEvents("m4coding", 0)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            tv_response.text = it.toString()
                        }, {
                            tv_response.text = LogUtils.obtainExceptionInfo(it)
                        })
            }
            R.id.bt_test_recommend -> {
//                getGithubDiscover()
                Thread{
                    testGithub()
                }.start()
            }
            R.id.bt_test_trend -> {
                getTrend()
            }
            R.id.bt_test_star -> {
                testStar()
            }
        }
    }

    fun testStar() {
        RepoDataSource.starRepo("Blankj","AndroidUtilCode")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    MLog.d(it.isSuccessful)
                }, {
                    it.printStackTrace()
                })
    }

    fun testGithub() {
        System.out.println("testGithub")

        val url: String = "https://github.com/login"//"https://github.com/session"
        // 使用ConcurrentMap存储cookie信息，因为数据在内存中，所以只在程序运行阶段有效，程序结束后即清空
        val storage = ConcurrentHashMap<String, List<Cookie>>()
        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .cookieJar(object : CookieJar {

                    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                        val host = url.host()
                        if (cookies != null && !cookies.isEmpty()) {
                            storage.put(host, cookies)
                        }
                    }

                    override fun loadForRequest(url: HttpUrl): List<Cookie> {
                        val host = url.host()
                        val list = storage.get(host)
                        return if (list == null) ArrayList() else list
                    }
                })
                .build()

        val request: Request = Request.Builder()
                .url(url)
                .build()

        val call: Call = okHttpClient.newCall(request)
        val response: Response = call.execute()

        //System.out.println(response.body()?.string())


        //########

        val body2: RequestBody = FormBody.Builder()
                .add("utf8", "✓")
                .add("commit", "Sign in")
                .add("authenticity_token", GithubHtmlUtils.parseAuthenticityToken(response.body()?.string() ?: "") ?: "")
                .add("login", BuildConfig.MY_USERNAME)
                .add("password", BuildConfig.MY_PASSWORD)
                .build()

        val url2 = "https://github.com/session"
        val request2: Request = Request.Builder()
                .url(url2)
                .post(body2)
                .build()

        val call2: Call = okHttpClient.newCall(request2)
        val response2: Response = call2.execute()
        //System.out.println(response2.body()?.string())


        //推荐
        val request3: Request = Request.Builder()
                .url("https://github.com/discover")
                .build()

        val call3: Call = okHttpClient.newCall(request3)
        val response3: Response = call3.execute()
        GithubHtmlUtils.parseDiscoverData(response3.body()?.string() ?: "")
    }

    @SuppressLint("CheckResult")
    fun getGithubDiscover() {
        GithubWebPageDataSource.login(BuildConfig.MY_USERNAME, BuildConfig.MY_PASSWORD)
                .flatMap { it ->
                    GithubWebPageDataSource.getDiscover(1)
                }.subscribe({
                    MLog.d(it.toString())
                }, {
                    it.printStackTrace()
                })
    }


    @SuppressLint("CheckResult")
    fun getTrend() {
        GithubWebPageDataSource.login(BuildConfig.MY_USERNAME, BuildConfig.MY_PASSWORD)
                .flatMap { it ->
                    GithubWebPageDataSource.getTrending("all", "monthly")
                }.subscribe({
                    tv_response.text = it.toString()
                }, {
                    tv_response.text = LogUtils.obtainExceptionInfo(it)
                })
    }

}