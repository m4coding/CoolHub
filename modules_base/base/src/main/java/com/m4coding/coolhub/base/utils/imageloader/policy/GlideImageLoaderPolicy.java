package com.m4coding.coolhub.base.utils.imageloader.policy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConfig;
import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConstant;
import com.m4coding.coolhub.base.utils.imageloader.listener.ImageLoadingListener;

/**
 * @author mochangsheng
 * @description Glide图像加载策略类
 */

public class GlideImageLoaderPolicy extends BaseImageLoaderPolicy {

    public static final Option<String> OPTION_URL = Option.memory(GlideImageLoaderPolicy.class.getName() + ".url");

    @Override
    public void displayImage(String url, ImageView imageView) {
        displayImage(url, imageView, null);
    }

    @Override
    public void displayImage(final String url, final ImageView imageView, ImageLoaderConfig configuration) {

        if (configuration == null) {
            configuration = mQFImageLoaderConfig;
        }

        //url为空，不处理返回 如果有默认图，就设置默认图后再返回
        if (TextUtils.isEmpty(url)) {
            if (configuration.getImageResOnDefault() > 0) {
                imageView.setImageResource(configuration.getImageResOnDefault());
            } else {
                imageView.setImageDrawable(configuration.getImageOnDefault());
            }

            return;
        }

        RequestBuilder requestBuilder ;
        RequestManager requestManager  = Glide.with(imageView.getContext());

        RequestOptions options = new RequestOptions();


        if (configuration.isAsBitmap()) {
            requestBuilder = requestManager.asBitmap().load(url);
        }else{
            requestBuilder = requestManager.load(url);
        }

        options.skipMemoryCache(!configuration.isCacheInMemory());

        boolean isOnlyDecodeImage = configuration.isOnlyDecodeImage();

        //设置默认图
        if (configuration.getImageResOnDefault() > 0) {
            if (isOnlyDecodeImage) {
                imageView.setImageResource(configuration.getImageResOnDefault());
            } else {
                options.placeholder(configuration.getImageResOnDefault());
            }
        } else {
            if (configuration.getImageOnDefault() != null) {
                if (isOnlyDecodeImage) {
                    imageView.setImageDrawable(configuration.getImageOnDefault());
                } else {
                    options.placeholder(configuration.getImageOnDefault());
                }
            }
        }

        if (configuration.getImageResOnFail() > 0) {
            if (!isOnlyDecodeImage) {
                options.placeholder(configuration.getImageResOnFail());
            }
        } else {
            if (configuration.getImageOnFail() != null) {
                if (!isOnlyDecodeImage) {
                    options.placeholder(configuration.getImageOnFail());
                }
            }
        }

        //设置缩放类型
        switch (configuration.getScaleType()) {
            case ImageLoaderConstant.FIT_CENTER:
                options.fitCenter();
                break;
            case ImageLoaderConstant.CENTER_CROP:
                options.centerCrop();
                break;
            default:
                break;
        }

        switch (configuration.getAnimationType()) {
            case ImageLoaderConstant.CROSS_FADE:
                if (configuration.isAsBitmap()) {
                    requestBuilder.transition(BitmapTransitionOptions.withCrossFade(250));
                } else {
                    requestBuilder.transition(new DrawableTransitionOptions().crossFade(250));
                }
                break;
            default:
                break;
        }

        //是否缓存在本地
        if (!configuration.isCacheOnDisk()) {
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        }

        //这是一处有bug，但回调使用时，configuration可能已被改变（待解决）
        final ImageLoaderConfig finalConfiguration = configuration;
        if (isOnlyDecodeImage) {//只解码图像不显示
            requestBuilder.into(new SimpleTarget() {

                @Override
                public void onResourceReady(Object resource, Transition transition) {
                    //资源加载完回调
                    if (finalConfiguration.getImageLoadingListener() != null) {
                        Bitmap bitmap = null;
                        if (resource instanceof GifDrawable) {
                            bitmap = ((GifDrawable)resource).getFirstFrame();
                        } else if (resource instanceof BitmapDrawable) {
                            bitmap = ((BitmapDrawable)resource).getBitmap();
                        }

                        if (null == bitmap) {
                            onLoadFailed(null);
                        } else {
                            finalConfiguration.getImageLoadingListener().onLoadingComplete(url, imageView, bitmap);
                        }
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (finalConfiguration.getImageResOnFail() > 0) {
                        imageView.setImageResource(finalConfiguration.getImageResOnFail());
                    }

                    if (finalConfiguration.getImageOnFail() != null) {
                        imageView.setImageDrawable(finalConfiguration.getImageOnFail());
                    }
                    //异常回调
                    if (finalConfiguration.getImageLoadingListener() != null) {
                        finalConfiguration.getImageLoadingListener().onLoadingFailed(url, imageView, null);
                    }
                }
            });
        } else {
            if (configuration.getImageLoadingListener() != null) {
                requestBuilder.listener(new RequestListener() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {

                        if (finalConfiguration.getImageResOnFail() > 0) {
                            imageView.setImageResource(finalConfiguration.getImageResOnFail());
                        }

                        if (finalConfiguration.getImageOnFail() != null) {
                            imageView.setImageDrawable(finalConfiguration.getImageOnFail());
                        }

                        //异常回调
                        if (finalConfiguration.getImageLoadingListener() != null) {
                            finalConfiguration.getImageLoadingListener().onLoadingFailed((String) model, imageView, e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                        //资源加载完回调  onResourceReady在对imageView set src之前
                        if (finalConfiguration.getImageLoadingListener() != null) {
                            Bitmap bitmap = null;
                            if (resource instanceof GifDrawable) {
                                bitmap = ((GifDrawable)resource).getFirstFrame();
                            } else if (resource instanceof BitmapDrawable) {
                                bitmap = ((BitmapDrawable)resource).getBitmap();
                            }

                            if (bitmap == null) {
                                onLoadFailed(new GlideException("bitmap is null"), model, target, false);
                            } else {
                                finalConfiguration.getImageLoadingListener().onLoadingComplete((String)model, imageView, bitmap);
                            }
                        }

                        return false;
                    }

                });
            }
            options.set(OPTION_URL,url);
            requestBuilder
                    .apply(options)
                    .into(imageView);
        }

        //资源加载开始回调
        if (configuration.getImageLoadingListener() != null) {
            configuration.getImageLoadingListener().onLoadingStarted(url, imageView);
        }

    }

    @Override
    public void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //在主线程调用时，应该放在异步线程去clearDiskCache，避免阻塞UI
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context.getApplicationContext()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context.getApplicationContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearImageMemoryCache(Context context) {
        Glide.get(context.getApplicationContext()).clearMemory();
    }


    @Override
    public BaseImageLoaderPolicy setImageOnDefault(int resOnDefault) {
        mQFImageLoaderConfig.setImageOnDefault(resOnDefault);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setImageOnDefault(Drawable imageOnDefault) {
        mQFImageLoaderConfig.setImageOnDefault(imageOnDefault);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setImageOnFail(int resOnFail) {
        mQFImageLoaderConfig.setImageOnFail(resOnFail);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setImageOnFail(Drawable imageOnFail) {
        mQFImageLoaderConfig.setImageOnFail(imageOnFail);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setCacheInMemory(boolean cacheInMemory) {
        mQFImageLoaderConfig.setCacheInMemory(cacheInMemory);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setCacheOnDisk(boolean cacheOnDisk) {
        mQFImageLoaderConfig.setCacheOnDisk(cacheOnDisk);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setOnlyDecodeImage(boolean onlyDecodeImage) {
        mQFImageLoaderConfig.setOnlyDecodeImage(onlyDecodeImage);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setImageLoadingListener(ImageLoadingListener imageLoadingListener) {
        mQFImageLoaderConfig.setImageLoadingListener(imageLoadingListener);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setScaleType(int scaleType) {
        mQFImageLoaderConfig.setScaleType(scaleType);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy setAnimationType(int animationType) {
        mQFImageLoaderConfig.setAnimationType(animationType);
        return this;
    }

    @Override
    public BaseImageLoaderPolicy resetConfig() {
        mQFImageLoaderConfig.resetConfig();
        return this;
    }

    @Override
    public BaseImageLoaderPolicy asBitmap(boolean isAsBitmap) {
        mQFImageLoaderConfig.asBitmap(isAsBitmap);
        return this;
    }
}
