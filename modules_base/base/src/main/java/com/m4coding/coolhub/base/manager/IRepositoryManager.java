package com.m4coding.coolhub.base.manager;



public interface IRepositoryManager {
    
    /**
     * 根据传入的Class获取对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

   /* *//**
     * 根据传入的Class获取对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     *//*
    <T> T obtainCacheService(Class<T> cache);

    *//**
     * 清理所有缓存
     *//*
    void clearAllCache();*/

}
