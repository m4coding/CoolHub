package com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.RecommendBean
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.main.contract.RecommendContract
import com.m4coding.coolhub.business.mainpage.modules.main.presenter.RecommendPresenter

/**
 * @author mochangsheng
 * @description 推荐Fragment
 */
class RecommendFragment : BaseListContentFragment<RecommendPresenter>(), RecommendContract.View {

    companion object {
        fun newInstance(): RecommendFragment {
            return RecommendFragment()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.layout_list, container, false)
    }

    override fun initData() {

        mMultiStateView = mRootView.findViewById(R.id.business_base_state_view)
        mRecyclerView = mRootView.findViewById(R.id.business_base_recyclerview)
        mRefreshView = mRootView.findViewById(R.id.business_base_sm_refresh_l)

        mHashMap = HashMap()
    }

    override fun initListPresenter() {
        mPresenter = RecommendPresenter(this)

        mPresenter.getAdapter()?.setOnItemClickListener { adapter, view, position ->
            val recommendBean = adapter.data[position] as RecommendBean
            val index = recommendBean.repositoryName?.indexOf("/") ?: -1
            if (index != -1) {
                val username = recommendBean.repositoryName?.substring(0, index) ?: ""
                val repoName =  recommendBean.repositoryName?.substring(index + 1,
                        recommendBean.repositoryName?.length ?: 0) ?: ""
                context.let {
                    if (it != null && !TextUtils.isEmpty(username) && !TextUtils.isEmpty(repoName)) {
                        RepositoryDetailsActivity
                                .newInstance(it, username, repoName)
                    }
                }
            }
        }

        mPresenter.getAdapter()?.setOnItemChildClickListener { baseQuickAdapter, view, i ->
            val recommendBean = baseQuickAdapter.data[i] as RecommendBean
            val index = recommendBean.repositoryName?.indexOf("/") ?: -1
            var username = ""
            if (index != -1) {
                username = recommendBean.repositoryName?.substring(0, index) ?: ""
            }
            when(view.id) {
                R.id.business_mainpage_recommend_iv_avatar -> {
                    context.let {
                        if (it != null && !TextUtils.isEmpty(username)) {
                            UserDetailsActivity
                                    .newInstance(it, username)
                        }
                    }
                }
            }
        }
    }
}