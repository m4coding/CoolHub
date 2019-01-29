package com.m4coding.coolhub.business.start.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import com.billy.cc.core.component.CC
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.base.base.BasePresenterActivity
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.start.contract.StartContract
import com.m4coding.coolhub.business.start.presenter.StartPresenter
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.android.ActivityEvent


/**
 * @author mochangsheng
 * @description
 */
class StartActivity : BasePresenterActivity<StartPresenter>(), StartContract.View  {

    override fun initView() {
        //setContentView(R.layout.business_start_layout_start)
        mPresenter = StartPresenter(this)
    }

    @SuppressLint("CheckResult")
    override fun initData() {
        val rxPermission = RxPermissions(this)
        rxPermission
                .requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .compose(RxLifecycleUtils.bindToLifecycle(this, ActivityEvent.DESTROY))
                .subscribe{
                    when {
                        it.granted -> {
                            // 用户已经同意该权限
                        }
                        it.shouldShowRequestPermissionRationale -> {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                        }
                        else -> {
                            // 用户拒绝了该权限，而且选中『不再询问』
                        }
                    }

                    mPresenter.start()
                }
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }


    /**
     * 跳转到首页时的回调
     */
    override fun onGotoMainPage(authUser: AuthUser) {
        //跳转首页
        val cc = CC.obtainBuilder(ComponentConstants.HOMEPAGE)
                .setActionName(ComponentConstants.HOMEPAGE_ACTION_SHOW)
                .build()
        PrintComponentMsgUtils.showResult(cc, cc.call())

        finish()
    }


    /**
     * 跳转到登录页时的回调
     */
    override fun onGotoLoginPage() {
        finish()
    }
}