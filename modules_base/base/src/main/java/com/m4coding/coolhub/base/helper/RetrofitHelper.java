package com.m4coding.coolhub.base.helper;


import com.m4coding.coolhub.base.base.BaseApplication;
import com.m4coding.coolhub.base.integration.GlobalConfig;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author mochangsheng
 * @description Retrofit助手
 */

public class RetrofitHelper {

    private static final int TIME_OUT = 10;

    private static RetrofitHelper sRetrofitHelper;

    private Retrofit mRetrofit;
    private OkHttpClient mClient;

    private RetrofitHelper() {

    }

    public static RetrofitHelper getInstance() {
        if (sRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (sRetrofitHelper == null) {
                    sRetrofitHelper = new RetrofitHelper();
                    Retrofit.Builder builderRetrofit = new Retrofit.Builder();
                    GlobalConfig globalConfig = BaseApplication.getGlobalConfig();
                    if (globalConfig != null) {
                        sRetrofitHelper.mClient = configureOkHttpClient(globalConfig).build();
                        builderRetrofit.baseUrl(globalConfig.getBaseUrl())
                                .client(sRetrofitHelper.mClient);

                        if (globalConfig.getRetrofitConfiguration() != null) {
                            globalConfig.getRetrofitConfiguration()
                                    .configRetrofit(BaseApplication.getContext(), builderRetrofit);
                        }

                    }

                    sRetrofitHelper.mRetrofit = builderRetrofit.build();
                }
            }
        }

        return sRetrofitHelper;
    }

    public OkHttpClient getClient() {
        return mClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


    private static OkHttpClient.Builder configureOkHttpClient(final GlobalConfig globalConfig) {
        OkHttpClient.Builder builderOkHttp = new OkHttpClient.Builder();

        if (globalConfig.getInterceptors() != null) {//如果外部提供了interceptor的集合则遍历添加
            for (Interceptor interceptor : globalConfig.getInterceptors()) {
                builderOkHttp.addInterceptor(interceptor);
            }
        }


        if (globalConfig.getOkhttpConfiguration() != null)
            globalConfig.getOkhttpConfiguration().configOkhttp(BaseApplication.getContext(), builderOkHttp);

        return builderOkHttp;
    }
}
