package com.m4coding.coolhub.business.mainpage.modules.details.ui.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.IssueBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.activity.BaseToolbarActivity
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.base.widgets.BusinessProgressDialog
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.EmptyPresenter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.DataListFragment
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * @author mochangsheng
 * @description 跟随、被跟随的Activity (粉丝、关注)
 */
class DataListActivity : BaseToolbarActivity<EmptyPresenter>() {

    private var mType = 0
    private var mRepoName: String? = null
    private var mUserName: String? = null
    private var mIssueBean: IssueBean? = null

    companion object {

        private const val KEY_USER_NAME = "key_user_name"
        private const val KEY_TYPE = "key_type"
        private const val KEY_REPO = "key_repo"
        private const val KEY_ISSUE = "key_issue"


        fun newInstance(context: Context, username: String, repo: String, type: Int) {
            val intent = Intent()
            intent.component = ComponentName(context, DataListActivity::class.java)
            intent.putExtra(KEY_USER_NAME, username)
            intent.putExtra(KEY_REPO, repo)
            intent.putExtra(KEY_TYPE, type)
            context.startActivity(intent)
        }

        /**
         * issue详情
         *
         * 参数issueBean为问题bean
         */
        fun newInstanceForIssueDetails(context: Context, username: String, repo: String,
                                       issueBean: IssueBean?) {
            val intent = Intent()
            intent.component = ComponentName(context, DataListActivity::class.java)
            intent.putExtra(KEY_USER_NAME, username)
            intent.putExtra(KEY_REPO, repo)
            intent.putExtra(KEY_ISSUE, issueBean)
            intent.putExtra(KEY_TYPE, DataListBean.TYPE_ISSUE_TIME_LINE)
            context.startActivity(intent)
        }


        /**
         * issue详情
         * 通过问题number获取
         */
        @SuppressLint("CheckResult")
        fun newInstanceForIssueDetails(context: Context, username: String, repo: String,
                                       issueNumber: Int) {
            val dialog = BusinessProgressDialog.show(context)

            RepoDataSource.getIssueByNumber(username, repo, issueNumber)
                    .compose(RxLifecycleUtils.bindToLifecycle(context, ActivityEvent.DESTROY))
                    .subscribe({
                        dialog.dismiss()
                        newInstanceForIssueDetails(context, username, repo, it)
                    }, {
                        dialog.dismiss()
                        it.printStackTrace()
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    })
        }
    }

    override fun initView() {

        mType = intent.getIntExtra(KEY_TYPE, 0)
        mRepoName = intent.getStringExtra(KEY_REPO)
        mUserName = intent.getStringExtra(KEY_USER_NAME)
        mIssueBean = intent.getParcelableExtra(KEY_ISSUE)

        val title = when(mType) {
            DataListBean.TYPE_FOLLOWERS -> {
                getString(R.string.business_mainpage_followers_list_title, mUserName)
            }
            DataListBean.TYPE_FOLLOWING -> {
                getString(R.string.business_mainpage_following_list_title, mUserName)
            }
            DataListBean.TYPE_FORKERS -> {
                getString(R.string.business_mainpage_forkers_list_title, mRepoName)
            }
            DataListBean.TYPE_STARTERS -> {
                getString(R.string.business_mainpage_starers_list_title, mRepoName)
            }
            DataListBean.TYPE_WATCHERS -> {
                getString(R.string.business_mainpage_watchers_list_title, mRepoName)
            }
            DataListBean.TYPE_ISSUE_TIME_LINE -> {
                getString(R.string.business_mainpage_issue_details_list_title, mIssueBean?.number.toString())
            }
            else -> {
                ""
            }
        }

        setContentView(R.layout.business_mainpage_activity_data_list, title)
    }

    override fun initData() {

        val fragment = when(mType) {
            DataListBean.TYPE_STARTERS ,
            DataListBean.TYPE_WATCHERS,
            DataListBean.TYPE_FORKERS,
            DataListBean.TYPE_FOLLOWING, DataListBean.TYPE_FOLLOWERS -> {
                DataListFragment.newInstance(false,
                        mUserName?:"",mRepoName?:"",  mType)
            }
            DataListBean.TYPE_ISSUE_TIME_LINE -> {
                DataListFragment.newInstanceForIssueDetails(false,
                        mUserName?:"",mRepoName?:"", mIssueBean)
            }
            else -> {
                DataListFragment.newInstance(
                        mUserName?:"",mRepoName?:"",  mType)
            }

        }

        supportFragmentManager.beginTransaction()
                .replace(R.id.business_mainpage_data_list_container, fragment).commit()
    }

}
