package com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.cc.core.component.CC
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.business_mainpage_layout_quick_header.*

/**
 * @author mochangsheng
 * @description  首页头部信息Fragment
 */
class HeaderInfoFragment : BaseFragment(), View.OnClickListener {

    private var mIsFristLoad: Boolean = true
    private var mAuthUser: AuthUser? = null

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_layout_quick_header, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mIsFristLoad = true
    }

    override fun initData() {
        business_mainpage_quick_rl_search.setOnClickListener(this)
        business_mainpage_quick_iv_avatar.setOnClickListener(this)
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

        if (mIsFristLoad) {
            mIsFristLoad = false
            LoginDataSource.getLoginUserInfo()
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY_VIEW))
                    .subscribe({
                        mAuthUser = it
                        ImageLoader.begin().setImageOnDefault(R.color.text_gray).displayImage(it.userAvatar, business_mainpage_quick_iv_avatar)
                    }, {
                        it.printStackTrace()
                    })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_mainpage_quick_rl_search -> {
                //跳转搜索页面
                val cc = CC.obtainBuilder(ComponentConstants.SEARCH)
                        .setActionName(ComponentConstants.SEARCH_ACTION_SHOW)
                        .build()
                PrintComponentMsgUtils.showResult(cc, cc.call())
            }
            R.id.business_mainpage_quick_iv_avatar -> {
                context?.let {
                    UserDetailsActivity.newInstance(it, mAuthUser?.userName ?: "")
                }
            }
        }
    }

}