package com.m4coding.coolhub.base.utils.imageloader.listener;

import android.graphics.Bitmap;
import android.view.View;

/**
 * @author mochangsheng
 * @description 图像加载监听类，使用时可以避免实现全部方法
 */

public class SimpleImageLoadingListener implements ImageLoadingListener {

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        //空实现
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, Exception e) {
        //空实现
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
        //空实现
    }
}
