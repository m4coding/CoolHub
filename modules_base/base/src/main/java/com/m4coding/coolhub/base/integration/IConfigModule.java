package com.m4coding.coolhub.base.integration;

import android.content.Context;

/**
 * 此接口可以给框架配置一些参数,需要实现类实现后,并在AndroidManifest中声明该实现类
 */

public interface IConfigModule {
    /**
     * 给框架配置一些配置参数
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobalConfig.Builder builder);
}
