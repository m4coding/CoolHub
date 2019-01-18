package com.m4coding.coolhub.base.utils.imageloader;


import android.graphics.drawable.Drawable;

import com.m4coding.coolhub.base.utils.imageloader.listener.ImageLoadingListener;
import com.m4coding.coolhub.base.utils.imageloader.policy.IImageLoaderConfig;

/**
 * @author mochangsheng
 * @description 图像配置类
 */

public class ImageLoaderConfig implements IImageLoaderConfig<ImageLoaderConfig> {

    //默认loading图
    private int mImageResOnDefault;
    private Drawable mImageOnDefault;
    //错误加载后的显示图
    private int mImageResOnFail;
    private Drawable mImageOnFail;
    //是否缓存在内存中
    private boolean mCacheInMemory;
    //是否缓存在本地中
    private boolean mCacheOnDisk;
    //是否只解码
    private boolean mOnlyDecodeImage;
    //监听回调
    private ImageLoadingListener mImageLoadingListener;
    //缩放类型
    private int mScaleType;
    //动画类型
    private int mAnimationType;
    //判断是否配置是否已经被改变
    private boolean mHasChanged = false;

    private boolean mIsAsBitmap;

    public ImageLoaderConfig(Builder builder) {
        mImageResOnDefault = builder.imageResOnDefault;
        mImageOnDefault = builder.imageOnDefault;
        mImageResOnFail = builder.imageResOnFail;
        mImageOnFail = builder.imageOnFail;
        mCacheInMemory = builder.cacheInMemory;
        mCacheOnDisk = builder.cacheOnDisk;
        mOnlyDecodeImage = builder.onlyDecodeImage;
        mImageLoadingListener = builder.imageLoadingListener;
        mScaleType = builder.scaleType;
        mAnimationType = builder.animationType;
        mHasChanged = true;

    }

    public int getImageResOnDefault() {
        return mImageResOnDefault;
    }

    @Override
    public ImageLoaderConfig setImageOnDefault(int resOnDefault) {
        if (resOnDefault != mImageResOnDefault) {
            mImageResOnDefault = resOnDefault;
            mHasChanged = true;
        }

        return this;
    }

    public Drawable getImageOnDefault() {
        return mImageOnDefault;
    }

    @Override
    public ImageLoaderConfig setImageOnDefault(Drawable imageOnDefault) {
        if (!imageOnDefault.equals(mImageOnDefault)) {
            mImageOnDefault = imageOnDefault;
            mHasChanged = true;
        }

        return this;
    }

    public int getImageResOnFail() {
        return mImageResOnFail;
    }

    @Override
    public ImageLoaderConfig setImageOnFail(int resOnFail) {
        if (resOnFail != mImageResOnFail) {
            mImageResOnFail = resOnFail;
            mHasChanged = true;
        }

        return this;
    }

    public Drawable getImageOnFail() {
        return mImageOnDefault;
    }

    @Override
    public ImageLoaderConfig setImageOnFail(Drawable imageOnFail) {
        if (imageOnFail.equals(mImageOnFail)) {
            mImageOnFail = imageOnFail;
            mHasChanged = true;
        }

        return this;
    }

    public boolean isCacheInMemory() {
        return mCacheInMemory;
    }

    @Override
    public ImageLoaderConfig setCacheInMemory(boolean cacheInMemory) {
        if (cacheInMemory != mCacheInMemory) {
            mCacheInMemory = cacheInMemory;
            mHasChanged = true;
        }

        return this;
    }

    public boolean isCacheOnDisk() {
        return mCacheOnDisk;
    }

    @Override
    public ImageLoaderConfig setCacheOnDisk(boolean cacheOnDisk) {
        if (cacheOnDisk != mCacheOnDisk) {
            mCacheOnDisk = cacheOnDisk;
            mHasChanged = true;
        }

        return this;
    }

    public boolean isOnlyDecodeImage() {
        return mOnlyDecodeImage;
    }

    @Override
    public ImageLoaderConfig setOnlyDecodeImage(boolean onlyDecodeImage) {
        if (onlyDecodeImage != mOnlyDecodeImage) {
            mOnlyDecodeImage = onlyDecodeImage;
            mHasChanged = true;
        }

        return this;
    }

    public ImageLoadingListener getImageLoadingListener() {
        return mImageLoadingListener;
    }

    @Override
    public ImageLoaderConfig setImageLoadingListener(ImageLoadingListener imageLoadingListener) {
        if (!imageLoadingListener.equals(mImageLoadingListener)) {
            mImageLoadingListener = imageLoadingListener;
            mHasChanged = true;
        }

        return this;
    }

    public int getScaleType() {
        return mScaleType;
    }

    @Override
    public ImageLoaderConfig setScaleType(int scaleType) {
        if (scaleType != mScaleType) {
            mScaleType = scaleType;
            mHasChanged = true;
        }

        return this;
    }

    public int getAnimationType() {
        return mAnimationType;
    }

    @Override
    public ImageLoaderConfig setAnimationType(int animationType) {
        if (animationType != mAnimationType) {
            mAnimationType = animationType;
            mHasChanged = true;
        }
        return this;
    }

    @Override
    public ImageLoaderConfig resetConfig() {
        /*if (mHasChanged) {
            mImageResOnDefault = 0;
            mImageOnDefault = null;
            mImageResOnFail = 0;
            mImageOnFail = null;
            mCacheInMemory = true;
            mCacheOnDisk = true;
            mOnlyDecodeImage = false;
            mImageLoadingListener = null;
            mScaleType = ImageLoaderConstant.INVALID;
            mAnimationType = ImageLoaderConstant.INVALID;

            mHasChanged = false;
        }*/

        mImageResOnDefault = 0;
        mImageOnDefault = null;
        mImageResOnFail = 0;
        mImageOnFail = null;
        mCacheInMemory = true;
        mCacheOnDisk = true;
        mOnlyDecodeImage = false;
        mImageLoadingListener = null;
        mScaleType = ImageLoaderConstant.INVALID;
        mAnimationType = ImageLoaderConstant.INVALID;
        mIsAsBitmap = false;

        return this;
    }

    @Override
    public ImageLoaderConfig asBitmap(boolean isAsBitmap) {
        mIsAsBitmap = isAsBitmap;
        return this;
    }

    public boolean isAsBitmap() {//特定于glide
        return mIsAsBitmap;
    }

    //builder configuration
    public static class Builder {
        private int imageResOnDefault;
        private Drawable imageOnDefault;
        private int imageResOnFail;
        private Drawable imageOnFail;
        private boolean cacheInMemory;
        private boolean cacheOnDisk;
        private boolean onlyDecodeImage;
        private ImageLoadingListener imageLoadingListener;
        private int scaleType;
        private int animationType;
        private boolean isAsBitmap;

        public Builder() {
            this.imageResOnDefault = 0;
            this.imageOnDefault = null;
            this.imageResOnFail = 0;
            this.imageOnFail = null;
            this.cacheInMemory = true;
            this.cacheOnDisk = true;
            this.onlyDecodeImage = false;
            this.imageLoadingListener = null;
            this.scaleType = ImageLoaderConstant.INVALID;
            this.animationType = ImageLoaderConstant.INVALID;
            this.isAsBitmap = false;
        }

        public Builder imageResOnDefault(int imageResOnDefault) {
            this.imageResOnDefault = imageResOnDefault;
            return this;
        }

        public Builder imageResOnDefault(Drawable imageOnDefault) {
            this.imageOnDefault = imageOnDefault;
            return this;
        }

        public Builder imageResOnFail(int imageResOnFail) {
            this.imageResOnFail = imageResOnFail;
            return this;
        }

        public Builder imageResOnFail(Drawable imageOnFail) {
            this.imageOnFail = imageOnFail;
            return this;
        }

        public Builder cacheInMemory(boolean cacheInMemory) {
            this.cacheInMemory = cacheInMemory;
            return this;
        }

        public Builder cacheOnDisk(boolean cacheOnDisk) {
            this.cacheOnDisk = cacheOnDisk;
            return this;
        }

        public Builder onlyDecodeImage(boolean onlyDecodeImage) {
            this.onlyDecodeImage = onlyDecodeImage;
            return this;
        }

        public Builder setListener(ImageLoadingListener imageLoadingListener) {
            this.imageLoadingListener = imageLoadingListener;
            return this;
        }

        public Builder scaleType(int scaleType) {
            this.scaleType = scaleType;
            return this;
        }

        public Builder animationType(int animationType) {
            this.animationType = animationType;
            return this;
        }

        public Builder asBitmap(boolean isAsBitmap) {
            this.isAsBitmap = isAsBitmap;
            return this;
        }

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(this);
        }

    }
}
