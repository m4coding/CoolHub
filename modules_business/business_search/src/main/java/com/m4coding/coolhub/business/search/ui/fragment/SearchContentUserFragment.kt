package com.m4coding.coolhub.business.search.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.cc.core.component.CC
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.search.contract.SearchContentUserContract
import com.m4coding.coolhub.business.search.misc.SearchEvent
import com.m4coding.coolhub.business.search.model.bean.SearchBean
import com.m4coding.coolhub.business.search.model.bean.SearchSelectUserTypeBean
import com.m4coding.coolhub.business.search.presenter.SearchContentUserPresenter
import com.m4coding.coolhub.business_search.R
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_CONTENT
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_LOADING
import kotlinx.android.synthetic.main.business_search_fragment_search_content_user.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 搜索用户内容Fragment
 */
class SearchContentUserFragment : BaseListContentFragment<SearchContentUserPresenter>()
        , SearchContentUserContract.View, View.OnClickListener {

    private var mTypePopup: SearchUserTypeFragment? = null
    private var mUserSortOptionBean: SearchSelectUserTypeBean? = null

    companion object {

        fun newInstance(): SearchContentUserFragment {
            return SearchContentUserFragment()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_search_fragment_search_content_user, container, false)
    }

    override fun initData() {

        mUserSortOptionBean = SearchSelectUserTypeBean.get()

        mHashMap = HashMap()
        mHashMap?.put(ParamsMapKey.SEARCH_QUERY, "")
        mHashMap?.put(ParamsMapKey.SEARCH_SORT, mUserSortOptionBean?.sort ?: "")
        mHashMap?.put(ParamsMapKey.SEARCH_ORDER, mUserSortOptionBean?.order ?: "")
    }

    override fun initListPresenter() {
        business_search_content_top_tv_type.setOnClickListener(this)
        mMultiStateView = mRootView.findViewById(R.id.business_search_user_state_view)
        mRecyclerView = mRootView.findViewById(R.id.business_search_user_recyclerview)
        mRefreshView = mRootView.findViewById(R.id.business_search_user_sm_refresh_l)

        mPresenter = SearchContentUserPresenter(this)


        business_search_content_top_tv_type.text = mUserSortOptionBean?.showName

        mPresenter?.getAdapter()?.setOnItemClickListener{
            adapter, view, position ->
            val userBean = adapter.data[position] as UserBean
            val username = userBean.login ?: ""
            //跳转仓库详情页面
            val cc = CC.obtainBuilder(ComponentConstants.HOMEPAGE)
                    .setActionName(ComponentConstants.USER_DETAILS_ACTION_SHOW)
                    .addParam(ParamsMapKey.USER_NAME, username)
                    .build()
            PrintComponentMsgUtils.showResult(cc, cc.call())
        }

        mHashMap?.let {
            mPresenter?.refreshData(false, it)
        }
    }

    override fun firstUpdate() {
        mHashMap?.let {
            val query = it[ParamsMapKey.SEARCH_QUERY]
            if (query?.isEmpty() == false) {
                mPresenter.refreshData(false, it)
            } else {
                mMultiStateView?.viewState = VIEW_STATE_CONTENT
            }
        }
    }

    override fun useEventBus(): Boolean {
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(searchEvent: SearchEvent) {
        when(searchEvent.type) {
            SearchEvent.TYPE_SEARCH -> {
                val message = searchEvent.value
                if (message is SearchBean) {
                    mHashMap?.let {
                        it[ParamsMapKey.SEARCH_QUERY] = message.query
                        mPresenter?.refreshData(false, it)
                    }
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_search_content_top_tv_type -> {
                if (mRefreshView?.isRefreshing == true || mMultiStateView?.viewState == VIEW_STATE_LOADING) {
                    showMessage(getString(R.string.now_refresh))
                    return
                }
                if (mTypePopup == null) {
                    mTypePopup = SearchUserTypeFragment()
                    mTypePopup?.setArrowView(business_search_content_iv_type_arrow)

                    mTypePopup?.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener {
                        adapter, view, position ->
                        mUserSortOptionBean = adapter.getItem(position) as SearchSelectUserTypeBean
                        mHashMap?.put(ParamsMapKey.SEARCH_SORT, mUserSortOptionBean?.sort ?: "")
                        mHashMap?.put(ParamsMapKey.SEARCH_ORDER, mUserSortOptionBean?.order ?: "")

                        business_search_content_top_tv_type.text = mUserSortOptionBean?.showName

                        mRefreshView?.autoRefresh()
                        mTypePopup?.dismiss()
                    })

                    mTypePopup?.show(business_search_content_iv_type_arrow)
                } else {
                    if (mTypePopup?.isShowing == true) {
                        mTypePopup?.dismiss()
                    } else {
                        mTypePopup?.show(business_search_content_iv_type_arrow)
                    }
                }
            }
        }
    }

}