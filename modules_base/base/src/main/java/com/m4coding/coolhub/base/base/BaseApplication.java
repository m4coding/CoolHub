package com.m4coding.coolhub.base.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.m4coding.coolhub.base.integration.ActivityLifecycle;
import com.m4coding.coolhub.base.integration.IConfigModule;
import com.m4coding.coolhub.base.integration.GlobalConfig;
import com.m4coding.coolhub.base.integration.ManifestParser;

import java.util.List;


/**
 * @author mochangsheng
 * @description 基本Application
 */

public abstract class BaseApplication extends Application {

    protected final String TAG = this.getClass().getSimpleName();

    private static  BaseApplication sApplication;
    protected ActivityLifecycle mActivityLifecycle;

    private List<IConfigModule> mModules;
    private static GlobalConfig sGlobalConfig;

    static {
        //为了兼容svg 矢量图 需要这个
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;

        //从Manifest中解析出ConfigModule
        mModules = new ManifestParser(this).parse();
        //初始化全局配置
        sGlobalConfig = parseGlobalConfigModule();


        mActivityLifecycle = new ActivityLifecycle();
        registerActivityLifecycleCallbacks(mActivityLifecycle);
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(base);
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

        if (mActivityLifecycle != null) {
            unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }

        sApplication = null;
        sGlobalConfig = null;
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return sApplication;
    }

    /**
     * 返回资源
     *
     * @return
     */
    public static Resources getAppResources() {
        return sApplication.getResources();
    }

    public static GlobalConfig getGlobalConfig() {
        return sGlobalConfig;
    }


    /**
     * 读取ConfigModules的信息，产生全局GlobalConfig
     * 需要在AndroidManifest中声明{@link IConfigModule}的实现类,和Glide的配置方式相似
     *
     * @return
     */
    private GlobalConfig parseGlobalConfigModule() {

        GlobalConfig.Builder builder = GlobalConfig
                .builder();

        for (IConfigModule module : mModules) {
            module.applyOptions(this, builder);
        }

        return builder.build();
    }

}
