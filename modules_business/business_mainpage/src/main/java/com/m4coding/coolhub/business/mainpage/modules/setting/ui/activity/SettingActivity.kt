package com.m4coding.coolhub.business.mainpage.modules.setting.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.billy.cc.core.component.CC
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.activity.BaseToolbarActivity
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.setting.contract.SettingContract
import com.m4coding.coolhub.business.mainpage.modules.setting.model.bean.SettingBean
import com.m4coding.coolhub.business.mainpage.modules.setting.presenter.SettingPresenter
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.*
import com.tencent.bugly.beta.Beta
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers



class SettingActivity : BaseToolbarActivity<SettingPresenter>(), SettingContract.View {

    private var mMultiStateView: MultiStateView? = null
    private var mRecyclerView: RecyclerView? = null


    companion object {
        fun newInstance(context: Context) {
            val intent = Intent()
            intent.component = ComponentName(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        setContentView(R.layout.business_mainpage_activity_setting, R.string.business_mainpage_title_setting)
        mMultiStateView = findViewById(R.id.business_mainpage_setting_state_view)
        mRecyclerView = findViewById(R.id.business_mainpage_setting_recyclerview)

        mRecyclerView?.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        mPresenter = SettingPresenter(this)

        mRecyclerView?.adapter = mPresenter.getAdapter()

        mPresenter.getAdapter()?.setOnItemClickListener{adapter, view, position ->
            val dataListBean = adapter.data[position] as DataListBean
            if (dataListBean.value is SettingBean) {
                val settingBean = dataListBean.value as SettingBean
                when(settingBean.type) {
                    SettingBean.TYPE_APP_VERSION -> {
                        val upgradeInfo = Beta.getUpgradeInfo()
                        if (upgradeInfo != null) {
                            //检查升级
                            Beta.checkUpgrade()
                        } else {
                            //提示已是最新版本
                            showMessage(getString(R.string.business_mainpage_about_is_new))
                        }
                    }
                    SettingBean.TYPE_LOGIN_OUT -> {
                        val dialog = NormalDialog(this@SettingActivity)
                        dialog.isTitleShow(false)
                                .content(getString(R.string.business_mainpage_login_out_ask))
                                .btnNum(2)
                                .btnText(getString(R.string.ok),getString(R.string.cancel)).setOnBtnClickL(OnBtnClickL {
                                    dialog.dismiss()
                                    LoginDataSource.logout()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .compose(RxLifecycleUtils.bindToLifecycle(this@SettingActivity, ActivityEvent.DESTROY))
                                            .subscribe({
                                                //退出所有的Activity
                                                AppManager.getInstance().killAll()
                                                //跳转登录页
                                                val cc = CC.obtainBuilder(ComponentConstants.LOGIN)
                                                        .setActionName(ComponentConstants.LOGIN_ACTION_SHOW)
                                                        .build()
                                                PrintComponentMsgUtils.showResult(cc, cc.call())
                                            },{
                                                it.printStackTrace()
                                                showMessage(NetExceptionHelper.wrapException(it).displayMessage)
                                            })
                                }, OnBtnClickL{
                                    dialog.dismiss()
                                })

                        dialog.show()
                    }
                }
            }
        }

        mPresenter.update()

    }


    override fun startLoad(type: Int) {
        mMultiStateView?.viewState = VIEW_STATE_LOADING
    }

    override fun successLoad(type: Int) {
        mMultiStateView?.viewState = VIEW_STATE_CONTENT
    }

    override fun errorLoad(type: Int) {
        mMultiStateView?.viewState = VIEW_STATE_ERROR
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

}