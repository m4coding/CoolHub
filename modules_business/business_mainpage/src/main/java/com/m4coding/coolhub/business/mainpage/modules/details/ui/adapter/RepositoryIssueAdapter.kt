package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.DataListFragment
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import java.util.*

class RepositoryIssueAdapter(repoBean: RepoBean, list: ArrayList<TabBean>, fragmentManager: FragmentManager) : CustomFragmentStatePagerAdapter(fragmentManager){


    private var mList: ArrayList<TabBean> = list

    private var mRepoBean: RepoBean = repoBean

    override fun getItem(position: Int): Fragment? {
        return when(mList[position].tabType) {
            TabType.TYPE_REPO_DETAILS_ISSUE_OPEN -> {
                DataListFragment.newInstance(mRepoBean.owner?.login ?: "",
                        mRepoBean.name ?: "", DataListBean.TYPE_ISSUE_OPEN)
            }
            TabType.TYPE_REPO_DETAILS_ISSUE_CLOSE -> {
                DataListFragment.newInstance(mRepoBean.owner?.login ?: "",
                        mRepoBean.name ?: "", DataListBean.TYPE_ISSUE_CLOSE)
            }
            else -> {
                null
            }
        }
    }

    /**
     * Return the number of views available.
     */
    override fun getCount(): Int {
        return mList.size
    }
}