package com.m4coding.coolhub.business.mainpage.test

import android.view.View
import com.m4coding.coolhub.api.BuildConfig
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.base.base.BaseActivity
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.mainpage.MainPageActivity
import com.m4coding.coolhub.business.mainpage.R
import kotlinx.android.synthetic.main.business_mainpage_activity_test_main.*
import java.util.concurrent.TimeUnit

/**
 * @author mochangsheng
 * @description
 */
class TestMainActivity : BaseActivity(), View.OnClickListener {


    override fun initView() {
        setContentView(R.layout.business_mainpage_activity_test_main)
    }

    override fun initData() {
        business_mainpage_bt_test.setOnClickListener(this)
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.business_mainpage_bt_test -> {
                LoginDataSource.login(BuildConfig.MY_USERNAME, BuildConfig.MY_PASSWORD)
                        .doOnNext {
                            ToastUtils.showShort("登录成功进入首页")
                        }
                        .delay(2000, TimeUnit.MILLISECONDS)
                        .subscribe({
                            MainPageActivity.newInstance(this@TestMainActivity)
                        }, {
                            it.printStackTrace()
                        })
            }
        }
    }
}