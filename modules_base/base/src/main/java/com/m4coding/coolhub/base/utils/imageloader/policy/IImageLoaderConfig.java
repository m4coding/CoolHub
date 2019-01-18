package com.m4coding.coolhub.base.utils.imageloader.policy;

import android.graphics.drawable.Drawable;

import com.m4coding.coolhub.base.utils.imageloader.listener.ImageLoadingListener;


/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */

public interface IImageLoaderConfig<T> {

    public T setImageOnDefault(int resOnDefault);

    public T setImageOnDefault(Drawable imageOnDefault);

    public T setImageOnFail(int resOnFail);

    public T setImageOnFail(Drawable imageOnFail);

    public T setCacheInMemory(boolean cacheInMemory);

    public T setCacheOnDisk(boolean cacheOnDisk);

    public T setOnlyDecodeImage(boolean onlyDecodeImage);

    public T setImageLoadingListener(ImageLoadingListener imageLoadingListener);

    public T setScaleType(int scaleType);

    public T setAnimationType(int animationType);

    public T resetConfig();

    public T asBitmap(boolean isAsBitmap);//特定于glide框架的
}
