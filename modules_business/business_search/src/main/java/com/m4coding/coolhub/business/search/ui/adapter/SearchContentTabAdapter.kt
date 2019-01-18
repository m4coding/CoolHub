package com.m4coding.coolhub.business.search.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.search.model.bean.TabType
import com.m4coding.coolhub.business.search.model.bean.TabBean
import com.m4coding.coolhub.business.search.ui.fragment.SearchContentRepoFragment
import com.m4coding.coolhub.business.search.ui.fragment.SearchContentUserFragment
import java.util.*

class SearchContentTabAdapter(list: ArrayList<TabBean>, fragmentManager: FragmentManager) : CustomFragmentStatePagerAdapter(fragmentManager){


    private var mList: ArrayList<TabBean> = list


    override fun getItem(position: Int): Fragment? {
        return when(mList[position].tabType) {
            TabType.TYPE_SEARCH_REPO -> {
                SearchContentRepoFragment.newInstance()
            }
            TabType.TYPE_SEARCH_USER -> {
                SearchContentUserFragment.newInstance()
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