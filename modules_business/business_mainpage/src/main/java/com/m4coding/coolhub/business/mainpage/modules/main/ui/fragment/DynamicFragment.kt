package com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.DataListActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.main.contract.DynamicContract
import com.m4coding.coolhub.business.mainpage.modules.main.presenter.DynamicPresenter

/**
 * @author mochangsheng
 * @description 用户关注者的动态
 */
open class DynamicFragment : BaseListContentFragment<DynamicPresenter>(), DynamicContract.View{


    companion object {
        fun newInstance(username: String?): DynamicFragment {
            val fragment = DynamicFragment()
            val bundle = Bundle()
            bundle.putString(ParamsMapKey.USER_NAME, username)
            fragment.arguments = bundle

            return fragment
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
        initDynamicType()
        val username = arguments?.getString(ParamsMapKey.USER_NAME)
        username?.let {
            mHashMap?.put(ParamsMapKey.USER_NAME, it)
        }
    }

    /**
     * 初始化动态类型
     */
    protected open fun initDynamicType() {
        mHashMap?.put(ParamsMapKey.DYNAMIC_TYPE, DynamicPresenter.DYNAMIC_OTHERS_FOCUS)
    }

    override fun initListPresenter() {
        mPresenter = DynamicPresenter(this)
        mPresenter.getAdapter()?.setOnItemClickListener { adapter, view, position ->
            val eventBean = mPresenter.getList()?.get(position) as EventBean
            val owner = eventBean.repo?.name?.split("/")?.get(0)
            val repoName = eventBean.repo?.name?.split("/")?.get(1)
            when(eventBean.type) {
                EventBean.EventType.ForkEvent -> {
                    context?.let {
                        RepositoryDetailsActivity.newInstance(it,
                                eventBean.actor?.login ?: "", repoName ?: "")
                    }
                }
                EventBean.EventType.ReleaseEvent -> {
                    showMessage(getString(R.string.business_mainpage_no_support_release_event_look))
                }
                EventBean.EventType.IssueCommentEvent, EventBean.EventType.IssuesEvent-> {
                    context?.let {
                        DataListActivity.newInstanceForIssueDetails(it, owner?:"",
                                repoName?:"", eventBean.payload?.issue?.number ?: 0)
                    }
                }
                EventBean.EventType.PushEvent -> {
                    showMessage(getString(R.string.business_mainpage_no_support_push_event_look))
                }
                else -> {
                    context?.let {
                        RepositoryDetailsActivity.newInstance(it, owner?:"", repoName?:"")
                    }
                }
            }
        }

        mPresenter?.getAdapter()?.setOnItemChildClickListener{ adapter, view, i ->
            val eventBean = mPresenter.getList()?.get(i) as EventBean
            context?.let {
                UserDetailsActivity.newInstance(it, eventBean.actor?.login ?: "")
            }
        }
    }
}