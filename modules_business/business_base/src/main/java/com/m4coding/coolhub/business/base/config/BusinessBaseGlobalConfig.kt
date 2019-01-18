package com.m4coding.coolhub.business.base.config

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.m4coding.coolhub.api.BuildConfig
import com.m4coding.coolhub.api.converter.CustomGsonConverterFactory
import com.m4coding.coolhub.api.interceptor.AuthInterceptor
import com.m4coding.coolhub.api.interceptor.log.LogInterceptor
import com.m4coding.coolhub.base.integration.GlobalConfig
import com.m4coding.coolhub.base.integration.IConfigModule
import com.m4coding.coolhub.base.utils.log.MLog
import com.m4coding.coolhub.base.net.Tls12SocketFactory.enableTls12OnPreLollipop
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit


/**
 * @author mochangsheng
 * @description 基本业务的全局配置
 */
class BusinessBaseGlobalConfig : IConfigModule {

    override fun applyOptions(context: Context?, builder: GlobalConfig.Builder?) {
        builder?.baseUrl(AppUrlConstants.GITHUB_API_BASE_URL)
        builder?.retrofitConfiguration { context, builder ->
            builder.addConverterFactory(CustomGsonConverterFactory.create())
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }

        builder?.okhttpConfiguration { _, builder ->
            //配置ssl协议版本，避免4.4以下手机https崩溃 (github服务器不支持tls1.1和1.0)
            enableTls12OnPreLollipop(builder)

            //配置cookie
            val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
            builder.cookieJar(cookieJar)

            builder.retryOnConnectionFailure(true)//默认重试一次，若需要重试N次，则要实现拦截器。
            builder.connectTimeout(10, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)
            builder.writeTimeout(20, TimeUnit.SECONDS)
        }

        builder?.addInterceptor(AuthInterceptor.instance)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = LogInterceptor(LogInterceptor.Logger {
                MLog.e("CoolHttpRequest", it)
            })
            builder?.addInterceptor(loggingInterceptor)
        }
    }
}