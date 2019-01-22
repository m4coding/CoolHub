package com.m4coding.coolhub

import com.m4coding.coolhub.api.BuildConfig
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.base.utils.log.MLog
import com.m4coding.coolhub.business.base.utils.CrashHandler
import com.m4coding.coolhub.business.base.utils.PhoneInformationManager
import com.m4coding.coolhub.business.login.ui.activity.LoginActivity
import com.m4coding.coolhub.business.mainpage.MainPageActivity
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import io.reactivex.plugins.RxJavaPlugins

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class CoolHubApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        //注册异常捕获
        CrashHandler.getInstance().init()
        initBugly()
        initRxjavaPlugins()
        PhoneInformationManager.getInstance().init(this)
    }

    private fun initBugly() {
        if (BuildConfig.BUGLY_ID_DEBUG.isNotEmpty() || BuildConfig.BUGLY_ID.isNotEmpty()) {
            if (AppConstants.sIsBetaApp) {
                if (BuildConfig.BUGLY_ID_DEBUG.isNotEmpty()) {
                    MLog.e("isBugly", "debug==" + BuildConfig.BUGLY_ID_DEBUG)
                    Bugly.init(applicationContext, BuildConfig.BUGLY_ID_DEBUG, true)
                }
            } else {
                if (BuildConfig.BUGLY_ID.isNotEmpty()) {
                    MLog.e("isBugly", "release==" + BuildConfig.BUGLY_ID)
                    Bugly.init(applicationContext, BuildConfig.BUGLY_ID, false)
                }
            }
            //设置允许升级弹窗出现的Activity
            Beta.canShowUpgradeActs.add(MainPageActivity::class.java)
            Beta.canShowUpgradeActs.add(LoginActivity::class.java)
        }
    }

    private fun initRxjavaPlugins() {
        RxJavaPlugins.setErrorHandler {
            NetExceptionHelper.wrapException(it)
        }
    }

}