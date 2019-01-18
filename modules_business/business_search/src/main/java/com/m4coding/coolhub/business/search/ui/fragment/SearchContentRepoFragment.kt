package com.m4coding.coolhub.business.search.ui.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.cc.core.component.CC
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.base.component.ComponentConstants
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.base.utils.PrintComponentMsgUtils
import com.m4coding.coolhub.business.search.contract.SearchContentRepoContract
import com.m4coding.coolhub.business.search.misc.SearchEvent
import com.m4coding.coolhub.business.search.model.bean.SearchBean
import com.m4coding.coolhub.business.search.model.bean.SearchSelectLanguageBean
import com.m4coding.coolhub.business.search.model.bean.SearchSelectRepoTypeBean
import com.m4coding.coolhub.business.search.presenter.SearchContentRepoPresenter
import com.m4coding.coolhub.business_search.R
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_CONTENT
import kotlinx.android.synthetic.main.business_search_fragment_search_content_repo.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SearchContentRepoFragment : BaseListContentFragment<SearchContentRepoPresenter>(),
        SearchContentRepoContract.View, View.OnClickListener {

    private var mTypePopup: SearchRepoTypeFragment? = null
    private var mLanguagePopup: SearchRepoLanguageFragment? = null
    private var mRepoSortOptionBean: SearchSelectRepoTypeBean? = null
    private var mLanguageStr: String? = null

    companion object {
        fun newInstance(): SearchContentRepoFragment {
            return SearchContentRepoFragment()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_search_fragment_search_content_repo, container, false)
    }

    override fun initData() {
        mRepoSortOptionBean = SearchSelectRepoTypeBean.get()
        mLanguageStr = SearchSelectLanguageBean.get()

        mHashMap = HashMap()
        mHashMap?.put(ParamsMapKey.SEARCH_QUERY, "")
        mHashMap?.put(ParamsMapKey.SEARCH_SORT, mRepoSortOptionBean?.sort ?: "")
        mHashMap?.put(ParamsMapKey.SEARCH_ORDER, mRepoSortOptionBean?.order ?: "")
        mHashMap?.put(ParamsMapKey.SEARCH_LANGUAGE, mLanguageStr?:"")
    }

    override fun initListPresenter() {

        business_search_content_top_tv_type.setOnClickListener(this)
        business_search_content_top_tv_language.setOnClickListener(this)

        business_search_content_top_tv_type.text = mRepoSortOptionBean?.showName
        business_search_content_top_tv_language.text = SearchSelectLanguageBean.getNameByLanguage(mLanguageStr?:"")

        mMultiStateView = mRootView.findViewById(R.id.business_search_repo_state_view)
        mRecyclerView = mRootView.findViewById(R.id.business_search_repo_recyclerview)
        mRefreshView = mRootView.findViewById(R.id.business_search_repo_sm_refresh_l)

        mPresenter = SearchContentRepoPresenter(this)

        mPresenter?.getAdapter()?.setOnItemClickListener{
            adapter, view, position ->
            val repoBean = adapter.data[position] as RepoBean
            val username = repoBean.owner?.login ?: ""
            val repoName = repoBean.name ?: ""
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(repoName)) {
                //跳转仓库详情页面
                val cc = CC.obtainBuilder(ComponentConstants.HOMEPAGE)
                        .setActionName(ComponentConstants.REPOSITORY_DETAILS_ACTION_SHOW)
                        .addParam(ParamsMapKey.USER_NAME, username)
                        .addParam(ParamsMapKey.REPO, repoName)
                        .build()
                PrintComponentMsgUtils.showResult(cc, cc.call())
            }
        }

        mPresenter?.getAdapter()?.setOnItemChildClickListener{
            adapter, view, position ->
            val repoBean = adapter.data[position] as RepoBean
            val username = repoBean.owner?.login ?: ""
            when(view.id) {
                R.id.business_search_repo_iv_avatar -> {
                    //跳转仓库详情页面
                    val cc = CC.obtainBuilder(ComponentConstants.HOMEPAGE)
                            .setActionName(ComponentConstants.USER_DETAILS_ACTION_SHOW)
                            .addParam(ParamsMapKey.USER_NAME, username)
                            .build()
                    PrintComponentMsgUtils.showResult(cc, cc.call())
                }
            }
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
                if (mRefreshView?.isRefreshing == true || mMultiStateView?.viewState == MultiStateView.VIEW_STATE_LOADING) {
                    showMessage(getString(R.string.now_refresh))
                    return
                }
                if (mTypePopup == null) {
                    mTypePopup = SearchRepoTypeFragment()
                    mTypePopup?.setArrowView(business_search_content_iv_type_arrow)

                    mTypePopup?.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener {
                        adapter, view, position ->
                        mRepoSortOptionBean = adapter.data[position] as SearchSelectRepoTypeBean
                        mHashMap?.put(ParamsMapKey.SEARCH_SORT, mRepoSortOptionBean?.sort ?: "")
                        mHashMap?.put(ParamsMapKey.SEARCH_ORDER, mRepoSortOptionBean?.order ?: "")

                        business_search_content_top_tv_type.text = mRepoSortOptionBean?.showName

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
            R.id.business_search_content_top_tv_language -> {
                if (mRefreshView?.isRefreshing == true || mMultiStateView?.viewState == MultiStateView.VIEW_STATE_LOADING) {
                    showMessage(getString(R.string.now_refresh))
                    return
                }
                if (mLanguagePopup == null) {
                    mLanguagePopup = SearchRepoLanguageFragment()
                    mLanguagePopup?.setArrowView(business_search_content_iv_language_arrow)

                    mLanguagePopup?.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener {
                        adapter, view, position ->
                        val bean = adapter.data[position] as SearchSelectLanguageBean
                        mLanguageStr = bean.language
                        mHashMap?.put(ParamsMapKey.SEARCH_LANGUAGE, bean.language ?: "")

                        business_search_content_top_tv_language.text = bean.name

                        mRefreshView?.autoRefresh()
                        mLanguagePopup?.dismiss()
                    })

                    mLanguagePopup?.show(business_search_content_iv_language_arrow)
                } else {
                    if (mLanguagePopup?.isShowing == true) {
                        mLanguagePopup?.dismiss()
                    } else {
                        mLanguagePopup?.show(business_search_content_iv_language_arrow)
                    }
                }
            }
        }
    }
}