package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.m4coding.business_base.R
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.UserDetailsEvent
import com.m4coding.coolhub.business.mainpage.modules.main.presenter.DynamicPresenter
import com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment.DynamicFragment
import com.m4coding.coolhub.widgets.MultiStateView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class UserDetailsDynamicFragment : DynamicFragment() {

    companion object {
        fun newInstance(username: String?): UserDetailsDynamicFragment {
            val fragment = UserDetailsDynamicFragment()
            val bundle = Bundle()
            bundle.putString(ParamsMapKey.USER_NAME, username)
            fragment.arguments = bundle

            return fragment
        }
    }


    override fun initLazyData() {
        initListPresenter()

        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mPresenter.getAdapter()?.setPreLoadNumber(15)
        mPresenter.getAdapter()?.setOnLoadMoreListener({
            mHashMap?.let { it ->
                mPresenter.loadMore(it)
            }
        }, mRecyclerView)

        mRecyclerView?.adapter = mPresenter.getAdapter()

        mMultiStateView?.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(R.id.business_base_cl_error)
                ?.setOnClickListener{
                    mHashMap?.let { it1 -> mPresenter.refreshData(false, it1) }
                }


        mRefreshView?.setOnRefreshListener {
            mHashMap?.let { it1 ->
                mPresenter.refreshData(true, it1)
            }
        }

        mHashMap?.let {
            //首次加载
            mPresenter.refreshData(false, it)
        }
    }

    /**
     * 初始化动态类型
     */
    override fun initDynamicType() {
        mHashMap?.put(ParamsMapKey.DYNAMIC_TYPE, DynamicPresenter.DYNAMIC_OWN)
    }

    override fun useEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(userDetailsEvent: UserDetailsEvent) {
        mRefreshView?.isEnableRefresh = false
        when(userDetailsEvent.type) {
            UserDetailsEvent.TYPE_UPDATE -> {
                //当前Activity位于前台时，才去刷新
                if (AppManager.getInstance().currentActivity == mActivity) {
                    if (mRefreshView?.isRefreshing == false) {
                        mHashMap?.let { mPresenter.refreshData(true, it) }
                    }
                }
            }
        }
    }
}