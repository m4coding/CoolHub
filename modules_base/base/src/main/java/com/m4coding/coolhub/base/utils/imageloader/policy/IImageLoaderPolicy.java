package com.m4coding.coolhub.base.utils.imageloader.policy;

import android.content.Context;
import android.widget.ImageView;

import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConfig;


/**
 * @author mochangsheng
 * @description 通用功能接口
 */

public interface IImageLoaderPolicy {

    //无占位图
    void displayImage(String url, ImageView imageView);

    void displayImage(String url, ImageView imageView, ImageLoaderConfig configuration);

    //清除硬盘缓存
    void clearImageDiskCache(Context context);

    //清除内存缓存
    void clearImageMemoryCache(Context context);

}
