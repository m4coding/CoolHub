package com.m4coding.coolhub.base.utils.imageloader.listener;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author mochangsheng
 * @description 图像加载监听接口
 */

public interface ImageLoadingListener {

    void onLoadingStarted(String imageUri, View view);


    void onLoadingFailed(String imageUri, View view, @Nullable Exception e);


    void onLoadingComplete(String imageUri, View view, @Nullable Bitmap bitmap);

}
