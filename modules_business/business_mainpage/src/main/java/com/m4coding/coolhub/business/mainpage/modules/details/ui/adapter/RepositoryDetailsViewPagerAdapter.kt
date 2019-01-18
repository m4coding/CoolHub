package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.*
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import java.util.ArrayList

/**
 * @author mochangsheng
 * @description 仓库详情ViewPager适配器
 */
class RepositoryDetailsViewPagerAdapter (repoBean: RepoBean, list: ArrayList<TabBean>, fragmentManager: FragmentManager) : CustomFragmentStatePagerAdapter(fragmentManager){

    private var mList: ArrayList<TabBean> = list

    private var mRepoBean: RepoBean = repoBean

    override fun getItem(position: Int): Fragment? {
        return when(mList[position].tabType) {
            TabType.TYPE_REPO_DETAILS_INFO -> {
                RepositoryDetailsInfoFragment.newInstance(mRepoBean)
            }
            TabType.TYPE_REPO_DETAILS_FILE -> {
                RepositoryDetailsFileFragment.newInstance(mRepoBean, null)
            }
            TabType.TYPE_REPO_DETAILS_ISSUE -> {
                RepositoryDetailsIssueFragment.newInstance(mRepoBean)
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