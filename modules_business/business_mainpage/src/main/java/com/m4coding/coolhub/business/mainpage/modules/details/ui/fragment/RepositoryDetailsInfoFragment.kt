package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.BranchBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.base.BasePresenterFragment
import com.m4coding.coolhub.base.manager.AppManager
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.utils.BusinessFormatUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryInfoContract
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.RepositoryInfoPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.DataListActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.UserDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.RepoDetailsEvent
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.SourceEditor
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.*
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import kotlinx.android.synthetic.main.business_mainpage_fragment_repository_details_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author mochangsheng
 * @description 仓库详情页
 */
class RepositoryDetailsInfoFragment : BasePresenterFragment<RepositoryInfoPresenter>(),
        RepositoryInfoContract.View, HeaderScrollHelper.ScrollableContainer, View.OnClickListener {


    private var mSourceEditor: SourceEditor? = null
    private var mRepoBean: RepoBean? = null

    companion object {

        private val KEY_REPO = "key_repo"

        fun newInstance(repoBean: RepoBean): RepositoryDetailsInfoFragment {
            val fragment = RepositoryDetailsInfoFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_info, container, false)
    }

    override fun initData() {

    }

    override fun useEventBus(): Boolean {
        return true
    }

    override fun initLazyData() {

        mRepoBean = arguments?.getParcelable(KEY_REPO)

        val createIndex = mRepoBean?.createdAt?.indexOf("T")
        val updateIndex = mRepoBean?.updatedAt?.indexOf("T")

        business_mainpage_repo_details_info_tv_time.text =
                getString(R.string.business_mainpage_repo_details_update_time,
                        mRepoBean?.createdAt?.substring(0, createIndex?:0), mRepoBean?.updatedAt?.substring(0, updateIndex?:0))

        business_mainpage_repo_details_info_tv_author.text =
                getString(R.string.business_mainpage_repo_details_author, mRepoBean?.owner?.login)

        business_mainpage_repo_details_info_tv_language.text =
                getString(R.string.business_mainpage_repo_details_language, mRepoBean?.language)

        if (mRepoBean?.fork == true) {
            business_mainpage_repo_details_info_tv_fork_from.visibility = View.VISIBLE
            business_mainpage_repo_details_info_tv_fork_from.text =
                    getString(R.string.business_mainpage_forked_from, mRepoBean?.parent?.fullName)
        } else {
            business_mainpage_repo_details_info_tv_fork_from.visibility = View.GONE
        }

        business_mainpage_repo_details_info_tv_fork_from.setOnClickListener(this)
        business_mainpage_repo_details_info_tv_author.setOnClickListener(this)

        business_mainpage_repo_details_info_tv_starers.text = BusinessFormatUtils.formatNumber(mRepoBean?.stargazersCount ?: 0)
        business_mainpage_repo_details_info_tv_watchers.text = BusinessFormatUtils.formatNumber(mRepoBean?.subscribersCount ?: 0)
        business_mainpage_repo_details_info_tv_forkers.text = BusinessFormatUtils.formatNumber(mRepoBean?.forksCount ?: 0)

        business_mainpage_repo_details_info_ll_starers.setOnClickListener(this)
        business_mainpage_repo_details_info_ll_watchers.setOnClickListener(this)
        business_mainpage_repo_details_info_ll_forkers.setOnClickListener(this)

        mPresenter = RepositoryInfoPresenter(this)
        business_mainpage_repository_details_info_state_view?.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(com.m4coding.business_base.R.id.business_base_cl_error)
                ?.setOnClickListener{
                    mPresenter?.getRepoReadme(false,
                            mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "", getCurrentBranch()?.name ?: "")
                }


        business_base_sm_refresh_l.setOnRefreshListener {
            mPresenter?.getRepoReadme(true,
                    mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "", getCurrentBranch()?.name ?: "")
        }

        mPresenter?.getRepoReadme(false,
                mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "", getCurrentBranch()?.name ?: "")
    }

    /**
     * 获取当前分支
     */
    private fun getCurrentBranch(): BranchBean? {
        return if (context is RepositoryDetailsActivity) {
            val activity = context as RepositoryDetailsActivity
            activity.getCurrentBranch()
        } else {
            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mRepoBean = null
    }

    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }


    override fun onGetInfoBegin(isPullRefresh: Boolean) {
        if (!isPullRefresh) {
            business_mainpage_repository_details_info_state_view.viewState = VIEW_STATE_LOADING
        }
    }

    override fun onGetInfoSuccess(content: String) {
        if (null == mSourceEditor) {
            mSourceEditor = SourceEditor(business_mainpage_repository_details_info_web_view)
            mSourceEditor?.setMarkdownViewer(true)
        }

        mSourceEditor?.setSource("readme", content, false)
        business_mainpage_repository_details_info_state_view.viewState = VIEW_STATE_CONTENT
        business_base_sm_refresh_l.finishRefresh()
    }


    override fun onGetInfoError(isPullRefresh: Boolean) {
        business_base_sm_refresh_l.finishRefresh()
        if (!isPullRefresh) {
            business_mainpage_repository_details_info_state_view.viewState = VIEW_STATE_ERROR
        } else {
            showMessage(getString(R.string.business_base_network_not_good))
        }
    }

    override fun getScrollableView(): View {
        return business_mainpage_repo_details_info_view_nested_scroll
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_mainpage_repo_details_info_tv_fork_from -> {
                mRepoBean?.parent?.let {repoBean ->
                    context?.let {
                        RepositoryDetailsActivity.newInstance(it,
                                repoBean.owner?.login ?: "", repoBean.name ?: "")
                    }
                }
            }

            R.id.business_mainpage_repo_details_info_tv_author-> {
                mRepoBean?.let {repoBean ->
                    context?.let {
                        UserDetailsActivity.newInstance(it,
                                repoBean.owner?.login ?: "")
                    }
                }
            }

            R.id.business_mainpage_repo_details_info_ll_starers -> {
                context?.let {
                    DataListActivity.newInstance(it, mRepoBean?.owner?.login ?: "",
                            mRepoBean?.name ?: "", DataListBean.TYPE_STARTERS)
                }
            }

            R.id.business_mainpage_repo_details_info_ll_watchers -> {
                context?.let {
                    DataListActivity.newInstance(it, mRepoBean?.owner?.login ?: "",
                            mRepoBean?.name ?: "", DataListBean.TYPE_WATCHERS)
                }
            }

            R.id.business_mainpage_repo_details_info_ll_forkers -> {
                context?.let {
                    DataListActivity.newInstance(it, mRepoBean?.owner?.login ?: "",
                            mRepoBean?.name ?: "", DataListBean.TYPE_FORKERS)
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMessage(repoDetailsEvent: RepoDetailsEvent) {
        when(repoDetailsEvent.type) {
            RepoDetailsEvent.TYPE_UPDATE_BRANCH -> {
                if (AppManager.getInstance().currentActivity == mActivity) {
                    val branchBean = repoDetailsEvent.value as BranchBean?
                    mPresenter?.getRepoReadme(false,
                            mRepoBean?.owner?.login ?: "", mRepoBean?.name ?: "", branchBean?.name ?: "")
                }
            }
        }
    }

}