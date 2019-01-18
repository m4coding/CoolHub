package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.FollowBean
import com.m4coding.coolhub.api.datasource.bean.IssueBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.contract.DataListContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.DataListPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.DataListActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity

class DataListFragment : BaseListContentFragment<DataListPresenter>(), DataListContract.View {

    private var mType = 0
    private var mRepoName: String? = null
    private var mUserName: String? = null
    private var mIssueBean: IssueBean? = null

    companion object {

        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_TYPE = "key_type"
        private const val KEY_REPO = "key_repo"
        private const val KEY_ISSUE = "key_issue"
        private const val KEY_USE_IN_VIEWPAGER = "key_use_in_view_pager" //是否使用在ViewPager中


        fun newInstance(username: String, repo: String, type: Int): DataListFragment {
            return newInstance(true, username, repo, type)
        }

        fun newInstance(useInViewPager: Boolean, username: String, repo: String, type: Int): DataListFragment {
            val fragment = DataListFragment()

            val bundle = Bundle()
            bundle.putString(KEY_USER_NAME, username)
            bundle.putString(KEY_REPO, repo)
            bundle.putInt(KEY_TYPE, type)
            bundle.putBoolean(KEY_USE_IN_VIEWPAGER, useInViewPager)
            fragment.arguments = bundle

            return fragment
        }

        /**
         * issue详情
         */
        fun newInstanceForIssueDetails(useInViewPager: Boolean, username: String, repo: String,
                                       issueBean: IssueBean?): DataListFragment {
            val fragment = DataListFragment()

            val bundle = Bundle()
            bundle.putString(KEY_USER_NAME, username)
            bundle.putString(KEY_REPO, repo)
            bundle.putParcelable(KEY_ISSUE, issueBean)
            bundle.putInt(KEY_TYPE, DataListBean.TYPE_ISSUE_TIME_LINE)
            bundle.putBoolean(KEY_USE_IN_VIEWPAGER, useInViewPager)
            fragment.arguments = bundle

            return fragment
        }
    }


    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        //默认使用在ViewPager中，懒加载才有效果
        mIsUseInViewPager = arguments?.getBoolean(KEY_USE_IN_VIEWPAGER) ?: true

        return inflater?.inflate(R.layout.layout_list, container, false)
    }

    override fun initListPresenter() {

        mMultiStateView = mRootView.findViewById(R.id.business_base_state_view)
        mRecyclerView = mRootView.findViewById(R.id.business_base_recyclerview)
        mRefreshView = mRootView.findViewById(R.id.business_base_sm_refresh_l)

        mType = arguments?.getInt(KEY_TYPE) ?: 0
        mRepoName = arguments?.getString(KEY_REPO)
        mUserName = arguments?.getString(KEY_USER_NAME)
        mIssueBean = arguments?.getParcelable(KEY_ISSUE)

        mHashMap = HashMap()
        mHashMap?.put(ParamsMapKey.USER_NAME, mUserName ?: "")
        mHashMap?.put(ParamsMapKey.REPO, mRepoName ?: "")
        mHashMap?.put(ParamsMapKey.LIST_TYPE, mType.toString())
        mHashMap?.put(ParamsMapKey.ISSUE_NUMBER, mIssueBean?.number.toString())


        mPresenter = DataListPresenter(this)

        mPresenter.getAdapter()?.setOnItemClickListener { adapter, _, position ->

            val dataListBean = adapter.data[position] as DataListBean
            var username: String? = null

            when(dataListBean.type) {
                DataListBean.TYPE_FOLLOWERS, DataListBean.TYPE_FOLLOWING  -> {
                    username = (dataListBean.value as FollowBean).login
                    goToUserDetails(username)
                }
                DataListBean.TYPE_FORKERS -> {
                    username = (dataListBean.value as RepoBean).owner?.login
                    goToUserDetails(username)
                }
                DataListBean.TYPE_STARTERS -> {
                    username = (dataListBean.value as UserBean).login
                    goToUserDetails(username)
                }
                DataListBean.TYPE_WATCHERS -> {
                    username = (dataListBean.value as UserBean).login
                    goToUserDetails(username)
                }
                DataListBean.TYPE_ISSUE_OPEN -> {
                    goToIssueDetails(dataListBean)
                }
                DataListBean.TYPE_ISSUE_CLOSE -> {
                    goToIssueDetails(dataListBean)
                }
                DataListBean.TYPE_ISSUE_TIME_LINE -> {

                }
            }
        }
    }

    private fun goToUserDetails(username: String?) {
        context?.let { it1 -> context
            username?.let {
                if (!TextUtils.isEmpty(it)) {
                    UserDetailsActivity.newInstance(it1, it)
                }
            }
        }
    }

    /**
     * issue详情页
     */
    private fun goToIssueDetails(dataListBean: DataListBean) {
        val issueBean = dataListBean.value
        if (issueBean is IssueBean) {
            context?.let {
                DataListActivity.newInstanceForIssueDetails(it, mUserName ?: "",
                        mRepoName ?: "", issueBean)
            }
        }
    }

    override fun getSpecial(): Any? {
        return when(mType) {
            DataListBean.TYPE_ISSUE_TIME_LINE -> {
                mIssueBean
            }
            else -> {
                null
            }
        }
    }
}