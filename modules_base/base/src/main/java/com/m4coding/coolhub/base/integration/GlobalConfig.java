package com.m4coding.coolhub.base.integration;


import android.app.Application;
import android.text.TextUtils;

import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import com.m4coding.coolhub.base.net.ClientConfiguration;
import com.m4coding.coolhub.base.utils.FileUtils;
import com.m4coding.coolhub.base.utils.imageloader.policy.BaseImageLoaderPolicy;
import com.m4coding.coolhub.base.utils.imageloader.policy.GlideImageLoaderPolicy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * 应用全局配置构建 （一定要配置的，否则不能运行起来）
 */
public class GlobalConfig {
    private HttpUrl mBaseUrl;
    private BaseImageLoaderPolicy mLoaderStrategy;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;
    private ClientConfiguration.RetrofitConfiguration mRetrofitConfiguration;
    private ClientConfiguration.OkHttpConfiguration mOkhttpConfiguration;
//    private ClientConfiguration.RxCacheConfiguration mRxCacheConfiguration;
    private ClientConfiguration.GsonConfiguration mGsonConfiguration;


    private GlobalConfig(Builder builder) {
        this.mBaseUrl = builder.baseUrl;
        this.mLoaderStrategy = builder.loaderStrategy;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErrorListener;
        this.mCacheFile = builder.cacheFile;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okHttpConfiguration;
//        this.mRxCacheConfiguration = builder.rxCacheConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
    }

    public List<Interceptor> getInterceptors() {
        return mInterceptors;
    }

    public HttpUrl getBaseUrl() {
        //一定要返回一个baseUrl，避免为空
        return mBaseUrl == null ? HttpUrl.parse("https://api.github.com/") : mBaseUrl;
    }

    public BaseImageLoaderPolicy getImageLoaderStrategy() {//图片加载框架默认使用glide
        return mLoaderStrategy == null ? new GlideImageLoaderPolicy() : mLoaderStrategy;
    }


    /**
     * 提供缓存文件
     */
    public File getCacheFile(Application application) {
        return mCacheFile == null ? FileUtils.getCacheFile(application) : mCacheFile;
    }


    /**
     * 提供处理Rxjava错误的管理器的回调
     *
     * @return
     */
    public ResponseErrorListener getResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }

    public ClientConfiguration.RetrofitConfiguration getRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    public ClientConfiguration.OkHttpConfiguration getOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

/*    public ClientConfiguration.RxCacheConfiguration getRxCacheConfiguration() {
        return mRxCacheConfiguration;
    }*/

    public ClientConfiguration.GsonConfiguration getGsonConfiguration() {
        return mGsonConfiguration;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private BaseImageLoaderPolicy loaderStrategy;
        private List<Interceptor> interceptors;
        private ResponseErrorListener responseErrorListener;
        private File cacheFile;
        private ClientConfiguration.RetrofitConfiguration retrofitConfiguration;
        private ClientConfiguration.OkHttpConfiguration okHttpConfiguration;
//        private ClientConfiguration.RxCacheConfiguration rxCacheConfiguration;
        private ClientConfiguration.GsonConfiguration gsonConfiguration;

        private Builder() {
        }

        public Builder baseUrl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("BaseUrl can not be empty");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder imageLoaderStrategy(BaseImageLoaderPolicy loaderStrategy) {//用来请求网络图片
            this.loaderStrategy = loaderStrategy;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            if (interceptors == null)
                interceptors = new ArrayList<>();
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder responseErrorListener(ResponseErrorListener listener) {//处理所有Rxjava的onError逻辑
            this.responseErrorListener = listener;
            return this;
        }


        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder retrofitConfiguration(ClientConfiguration.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(ClientConfiguration.OkHttpConfiguration okHttpConfiguration) {
            this.okHttpConfiguration = okHttpConfiguration;
            return this;
        }

//        public Builder rxCacheConfiguration(ClientConfiguration.RxCacheConfiguration rxCacheConfiguration) {
//            this.rxCacheConfiguration = rxCacheConfiguration;
//            return this;
//        }

        public Builder gsonConfiguration(ClientConfiguration.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }


        public GlobalConfig build() {
            return new GlobalConfig(this);
        }


    }


}
