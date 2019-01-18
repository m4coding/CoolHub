package com.m4coding.coolhub.base.utils.imageloader;


import com.m4coding.coolhub.base.utils.imageloader.policy.BaseImageLoaderPolicy;
import com.m4coding.coolhub.base.utils.imageloader.policy.GlideImageLoaderPolicy;

/**
 * @author mochangsheng
 * @description 图像加载类  单例
 */

public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();
    private BaseImageLoaderPolicy mBaseImageLoaderPolicy;

    private static ImageLoader sInstance;

    private ImageLoader() {
        //默认使用glide加载策略
        mBaseImageLoaderPolicy = new GlideImageLoaderPolicy();
    }

    private static ImageLoader getInstance() {

        if (sInstance == null) {
            synchronized (ImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ImageLoader();
                }
            }
        }

        return sInstance;
    }

    /**
     * 设置图像解码策略
     * @param imageLoaderPolicy
     * @return
     */
    public static ImageLoader setImageLoaderPolicy(BaseImageLoaderPolicy imageLoaderPolicy) {
        if (imageLoaderPolicy == null) {
            return ImageLoader.getInstance();
        }
        ImageLoader.getInstance().mBaseImageLoaderPolicy = imageLoaderPolicy;

        return ImageLoader.getInstance();
    }

    /**
     * 开始操作
     * @return
     */
    public  static BaseImageLoaderPolicy begin() {
        ImageLoader.getInstance().mBaseImageLoaderPolicy.resetConfig();
        return ImageLoader.getInstance().mBaseImageLoaderPolicy;
    }

    /**
     * 开始操作  这个方法是不会重置默认配置的 (用于自定义配置的开启)
     * @return
     */
    public  static BaseImageLoaderPolicy beginNoDefaultConfig() {
        return ImageLoader.getInstance().mBaseImageLoaderPolicy;
    }
}
