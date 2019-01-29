package com.m4coding.coolhub

import android.annotation.SuppressLint
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
import com.tencent.bugly.crashreport.CrashReport.UserStrategy



/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class CoolHubApplication : BaseApplication() {

    @SuppressLint("CheckResult")
    override fun onCreate() {
        super.onCreate()

        PhoneInformationManager.getInstance().init(this)
                .subscribe({
                   init()
                }, {
                   init()
                })
    }

    private fun init() {
        //注册异常捕获
        CrashHandler.getInstance().init()
        initRxjavaPlugins()
        initBugly()
    }

    private fun initBugly() {
        if (BuildConfig.BUGLY_ID_DEBUG.isNotEmpty() || BuildConfig.BUGLY_ID.isNotEmpty()) {
            if (AppConstants.sIsBetaApp) {
                if (BuildConfig.BUGLY_ID_DEBUG.isNotEmpty()) {
                    MLog.e("isBugly", "debug==" + BuildConfig.BUGLY_ID_DEBUG)
                    Bugly.init(applicationContext, BuildConfig.BUGLY_ID_DEBUG, true, getBuglyStrategy())
                }
            } else {
                if (BuildConfig.BUGLY_ID.isNotEmpty()) {
                    MLog.e("isBugly", "release==" + BuildConfig.BUGLY_ID)
                    Bugly.init(applicationContext, BuildConfig.BUGLY_ID, false, getBuglyStrategy())
                }
            }
            //设置允许升级弹窗出现的Activity
            Beta.canShowUpgradeActs.add(MainPageActivity::class.java)
            Beta.canShowUpgradeActs.add(LoginActivity::class.java)
        }
    }

    private fun getBuglyStrategy(): UserStrategy {
        val strategy = UserStrategy(getContext())
        strategy.appChannel = PhoneInformationManager.getInstance().getValue(PhoneInformationManager.KEY_FROM)
        strategy.deviceID = PhoneInformationManager.getInstance().getValue(PhoneInformationManager.KEY_UDID)
        return strategy
    }

    private fun initRxjavaPlugins() {
        RxJavaPlugins.setErrorHandler {
            NetExceptionHelper.wrapException(it)
        }
    }

}