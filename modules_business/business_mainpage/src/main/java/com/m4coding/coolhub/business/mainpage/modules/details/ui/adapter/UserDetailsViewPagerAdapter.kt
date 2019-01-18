package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.UserDetailsDynamicFragment
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.UserDetailsInfoFragment
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import java.util.ArrayList

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */

class UserDetailsViewPagerAdapter(user: UserBean, list: ArrayList<TabBean>, fragmentManager: FragmentManager) : CustomFragmentStatePagerAdapter(fragmentManager){


    private var mList: ArrayList<TabBean> = list

    private var mUser: UserBean = user

    override fun getItem(position: Int): Fragment? {
        return when(mList[position].tabType) {
            TabType.TYPE_DYNAMIC -> {
                UserDetailsDynamicFragment.newInstance(mUser.login)
            }
            TabType.TYPE_USER_DETAILS_INFO -> {
                UserDetailsInfoFragment.newInstance(mUser)
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