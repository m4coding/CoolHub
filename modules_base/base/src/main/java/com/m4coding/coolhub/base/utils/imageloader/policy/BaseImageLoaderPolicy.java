package com.m4coding.coolhub.base.utils.imageloader.policy;


import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConfig;

/**
 * @author mochangsheng
 * @description 基本策略类
 */

public abstract class BaseImageLoaderPolicy implements IImageLoaderPolicy, IImageLoaderConfig<BaseImageLoaderPolicy> {

    protected ImageLoaderConfig mQFImageLoaderConfig;

    public BaseImageLoaderPolicy() {
        //初始化默认的加载配置
        mQFImageLoaderConfig = new ImageLoaderConfig.Builder().build();
    }
}
