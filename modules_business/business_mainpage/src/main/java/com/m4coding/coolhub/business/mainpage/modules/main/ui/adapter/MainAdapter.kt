package com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.m4coding.coolhub.api.datasource.dao.bean.AuthUser
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment.DynamicFragment
import com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment.RecommendFragment
import java.util.*

/**
 * @author mochangsheng
 * @description
 */
class MainAdapter(user: AuthUser, list: ArrayList<TabBean>, fragmentManager: FragmentManager) : CustomFragmentStatePagerAdapter(fragmentManager){


    private var mList: ArrayList<TabBean> = list

    private var mUser: AuthUser = user

    override fun getItem(position: Int): Fragment? {
        return when(mList[position].tabType) {
            TabType.TYPE_DYNAMIC_FOCUS -> {
                DynamicFragment.newInstance(mUser.userName)
            }
            TabType.TYPE_RECOMMEND -> {
                RecommendFragment()
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

    //重写：避免bug https://blog.csdn.net/fkq_2016/article/details/78074999?utm_source=debugrun&utm_medium=referral
    override fun saveState(): Parcelable? {
        return null
    }
}