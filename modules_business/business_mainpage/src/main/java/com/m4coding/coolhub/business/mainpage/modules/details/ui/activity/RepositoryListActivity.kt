package com.m4coding.coolhub.business.mainpage.modules.details.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentActivity
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.contract.RepositoryListContract
import com.m4coding.coolhub.business.mainpage.modules.details.presenter.RepositoryListPresenter

/**
 * @author mochangsheng
 * @description 仓库列表Activity
 */
class RepositoryListActivity : BaseListContentActivity<RepositoryListPresenter>(), RepositoryListContract.View {

    companion object {

        private const val KEY_USER_NAME = "user_name"

        fun newInstance(context: Context, username: String) {
            val intent = Intent()
            intent.component = ComponentName(context, RepositoryListActivity::class.java)
            intent.putExtra(KEY_USER_NAME, username)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        setContentView(R.layout.layout_list, getString(R.string.business_mainpage_repository_list_title))

        mMultiStateView = findViewById(R.id.business_base_state_view)
        mRecyclerView = findViewById(R.id.business_base_recyclerview)
        mRefreshView = findViewById(R.id.business_base_sm_refresh_l)

        mHashMap = HashMap()
        val username = intent.getStringExtra(KEY_USER_NAME)
        username?.let {
            mHashMap?.put(ParamsMapKey.USER_NAME, it)
        }
    }

    override fun initListPresenter() {
        mPresenter = RepositoryListPresenter(this)

        mPresenter.getAdapter()?.setOnItemClickListener { adapter, view, position ->
            val repoBean = adapter.data[position] as RepoBean
            val username = repoBean.owner?.login ?: ""
            val repoName = repoBean.name ?: ""
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(repoName)) {
                RepositoryDetailsActivity
                        .newInstance(this@RepositoryListActivity, username, repoName)
            }
        }
    }

}