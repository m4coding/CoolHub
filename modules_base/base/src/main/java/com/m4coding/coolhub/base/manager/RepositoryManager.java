package com.m4coding.coolhub.base.manager;

import com.m4coding.coolhub.base.helper.RetrofitHelper;

import java.util.LinkedHashMap;
import java.util.Map;

//import io.rx_cache.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 用来管理网络请求层,以及数据缓存层,以后可以添加数据库请求层
 */
public class RepositoryManager implements IRepositoryManager {

    private Retrofit mRetrofit;
//    private RxCache mRxCache;
    private final Map<String, Object> mRetrofitServiceCache = new LinkedHashMap<>();
//    private final Map<String, Object> mCacheServiceCache = new LinkedHashMap<>();

    private static RepositoryManager sRepositoryManager;

    private RepositoryManager() {
        mRetrofit = RetrofitHelper.getInstance().getRetrofit();
//        mRxCache = CacheHelper.getCacheInstance();
    }

    public static RepositoryManager getInstance() {
        if (sRepositoryManager == null) {
            synchronized (RepositoryManager.class) {
                if (sRepositoryManager == null) {
                    sRepositoryManager = new RepositoryManager();
                }
            }
        }

        return sRepositoryManager;
    }

    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T obtainRetrofitService(Class<T> service) {
        T retrofitService;
        synchronized (mRetrofitServiceCache) {
            retrofitService = (T) mRetrofitServiceCache.get(service.getName());
            if (retrofitService == null) {
                retrofitService = mRetrofit.create(service);
                mRetrofitServiceCache.put(service.getName(), retrofitService);
            }
        }
        return retrofitService;
    }

    /**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
   /* @Override
    public <T> T obtainCacheService(Class<T> cache) {
        T cacheService;
        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null) {
                cacheService = mRxCache.using(cache);
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }

    *//**
     * 清理所有缓存
     *//*
    @Override
    public void clearAllCache() {
//        mRxCache.evictAll();
    }*/
}
