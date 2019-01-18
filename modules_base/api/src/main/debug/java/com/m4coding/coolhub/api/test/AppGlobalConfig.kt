package com.m4coding.coolhub.api.test

import android.content.Context
import com.m4coding.coolhub.api.BuildConfig
import com.m4coding.coolhub.api.interceptor.AuthInterceptor
import com.m4coding.coolhub.api.interceptor.log.LogInterceptor
import com.m4coding.coolhub.base.integration.GlobalConfig
import com.m4coding.coolhub.base.integration.IConfigModule
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.PersistentCookieJar



/**
 * @author mochangsheng
 * @description
 */
class AppGlobalConfig : IConfigModule {

    override fun applyOptions(context: Context?, builder: GlobalConfig.Builder?) {
        builder?.baseUrl("https://api.github.com/")
        builder?.retrofitConfiguration { context, builder ->
            //builder.addConverterFactory(CustomGsonConverterFactory.create())
            builder.addConverterFactory(GsonConverterFactory.create())
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }

        builder?.okhttpConfiguration { context, builder ->
            val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
            builder.cookieJar(cookieJar)
        }

        builder?.addInterceptor(AuthInterceptor.instance)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = LogInterceptor(LogInterceptor.Logger {
                LogUtils.e("CoolHttpRequest", it)
            })
            builder?.addInterceptor(loggingInterceptor)
        }
    }
}