package com.m4coding.coolhub.business.mainpage.modules.mine.ui.fragment

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.dialog.listener.OnBtnClickL
import com.flyco.dialog.widget.NormalDialog
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.base.BasePresenterFragment
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.base.widgets.BusinessProgressDialog
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.mine.contract.MineContract
import com.m4coding.coolhub.business.mainpage.modules.mine.presenter.MinePresenter
import com.m4coding.coolhub.business.mainpage.modules.setting.ui.activity.SettingActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.business_mainpage_fragment_mine.*

/**
 * @author mochangsheng
 * @description
 */
class MineFragment : BasePresenterFragment<MinePresenter>(), MineContract.View, View.OnClickListener {

    private var mUserBean: UserBean? = null
    private var mProgressDialog: BusinessProgressDialog? = null

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_mine, container, false)
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()

        if (!mIsFirstLoad) {
            mPresenter?.getMineInfo()
        }
    }

    override fun initLazyData() {
        business_mainpage_mine_tv_info_error.setOnClickListener(this)
        business_mainpage_mine_cd_mine_info.setOnClickListener(this)
        business_mainpage_mine_cd_setting.setOnClickListener(this)
        business_mainpage_mine_cd_about.setOnClickListener(this)
        mPresenter = MinePresenter(this@MineFragment)
        mPresenter.getMineInfo()
    }

    override fun onSuccessMineInfo(userBean: UserBean) {

        mUserBean = userBean

        business_mainpage_mine_tv_info_error.visibility = View.GONE
        business_mainpage_mine_progress_info_loading.visibility = View.GONE
        business_mainpage_mine_cl_info.visibility = View.VISIBLE

        business_mainpage_mine_tv_name.text = userBean.login
        if (TextUtils.isEmpty(userBean.location)) {
            business_mainpage_mine_tv_location.setText(R.string.business_mainpage_mine_unknow)
        } else {
            business_mainpage_mine_tv_location.text = userBean.location
        }

        if (TextUtils.isEmpty(userBean.email)) {
            business_mainpage_mine_tv_mail.setText(R.string.business_mainpage_mine_unknow)
        } else {
            business_mainpage_mine_tv_mail.text = userBean.email
        }
    }

    override fun onFailMineInfo() {
        if (null == mUserBean) {
            business_mainpage_mine_tv_info_error.visibility = View.VISIBLE
            business_mainpage_mine_cl_info.visibility = View.GONE
            business_mainpage_mine_progress_info_loading.visibility = View.GONE
        }
    }

    override fun onLoadingMineInfo() {
        if (null == mUserBean) {
            business_mainpage_mine_progress_info_loading.visibility = View.VISIBLE
            business_mainpage_mine_cl_info.visibility = View.GONE
            business_mainpage_mine_tv_info_error.visibility = View.GONE
        }
    }

    override fun getOwnContext(): Context? {
        return context
    }

    /**
     * 显示信息
     */
    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.business_mainpage_mine_cd_mine_info -> {
                mPresenter.goUserDetails()
            }
            R.id.business_mainpage_mine_tv_info_error -> {
                mPresenter.getMineInfo()
            }
            R.id.business_mainpage_mine_cd_setting -> {
                context?.let {
                    SettingActivity.newInstance(it)
                }
            }
            R.id.business_mainpage_mine_cd_about -> {
                val dialog = NormalDialog(context)
                dialog.isTitleShow(false)
                        .content(getString(R.string.business_mainpage_about_text))
                        .btnNum(2)
                        .btnText(getString(R.string.business_mainpage_about_source),
                                getString(R.string.business_mainpage_about_star_me))
                        .setOnBtnClickL(OnBtnClickL{
                            dialog.dismiss()
                            context?.let {
                                RepositoryDetailsActivity.newInstance(it, "m4coding", "CoolHub")
                            }
                        }, OnBtnClickL{
                            showLoading()
                            dialog.dismiss()
                            RepoDataSource.starRepo("m4coding", "CoolHub")
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY))
                                    .subscribe({
                                        if (!it.isSuccessful) {
                                            ToastUtils.showShort(R.string.business_mainpage_operate_fail)
                                        } else {
                                            ToastUtils.showShort(R.string.business_mainpage_operate_success)
                                        }
                                        dismissLoading()
                                    }, {
                                        dismissLoading()
                                        it.printStackTrace()
                                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                                    })
                        })

                dialog.show()
            }
        }
    }

    private fun showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = BusinessProgressDialog.show(context)
        } else {
            mProgressDialog?.show()
        }
    }

    private fun dismissLoading() {
        if (mProgressDialog?.isShowing == true) {
            mProgressDialog?.dismiss()
        }
    }

}