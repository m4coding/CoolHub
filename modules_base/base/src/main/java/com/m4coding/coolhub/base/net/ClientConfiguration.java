package com.m4coding.coolhub.base.net;

import android.content.Context;

import com.google.gson.GsonBuilder;

//import io.rx_cache.internal.RxCache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author mochangsheng
 * @description 网络客户端配置
 */
public class ClientConfiguration {


    //Retrofit配置
    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    //OkHttp配置
    public interface OkHttpConfiguration {
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

/*    //RxCache缓存配置
    public interface RxCacheConfiguration {
        RxCache configRxCache(Context context, RxCache.Builder builder);
    }*/

    //Gson配置
    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }
}
