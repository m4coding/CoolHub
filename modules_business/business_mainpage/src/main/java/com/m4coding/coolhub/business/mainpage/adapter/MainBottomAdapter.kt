package com.m4coding.coolhub.business.mainpage.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.bean.BottomTitleBean
import com.m4coding.coolhub.business.mainpage.modules.hot.ui.fragment.HotFragment
import com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment.MainFragment
import com.m4coding.coolhub.business.mainpage.modules.mine.ui.fragment.MineFragment
import kotlin.collections.ArrayList

/**
 * @author mochangsheng
 * @description
 */
class MainBottomAdapter(fm: FragmentManager, list: ArrayList<BottomTitleBean>) : FragmentPagerAdapter(fm) {

    private var mList: ArrayList<BottomTitleBean> = ArrayList()

    init {
        mList = list
    }

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null

        when (mList[position].titleRes) {
            R.string.business_mainpage_main -> {
                fragment = MainFragment()
            }
            R.string.business_mainpage_hot -> {
                fragment = HotFragment.newInstance()
            }
            R.string.business_mainpage_mine -> {
                fragment = MineFragment()
            }
        }

        return fragment
    }

    /**
     * Return the number of views available.
     */
    override fun getCount(): Int {
        return mList.size
    }

}