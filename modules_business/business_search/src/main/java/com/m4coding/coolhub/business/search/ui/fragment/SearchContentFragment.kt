package com.m4coding.coolhub.business.search.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.business.search.model.bean.TabType
import com.m4coding.coolhub.business.search.model.bean.TabBean
import com.m4coding.coolhub.business.search.ui.adapter.SearchContentTabAdapter
import com.m4coding.coolhub.business_search.R
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * 搜索内容Fragment
 */
class SearchContentFragment : BaseFragment() {

    private var mList: ArrayList<TabBean> = ArrayList()
    private var mViewPager: ViewPager? = null
    private var mMagicIndicator: MagicIndicator? = null
    private var mAdapter: SearchContentTabAdapter? = null

    companion object {
        const val TAG = "SearchContentFragment"
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_search_fragment_search_content, container, false)
    }


    override fun initData() {
        mViewPager = mRootView.findViewById(R.id.business_search_content_viewpager)
        mMagicIndicator = mRootView.findViewById(R.id.business_search_content_top_indicator)

        mList.add(TabBean(TabType.TYPE_SEARCH_REPO))
        mList.add(TabBean(TabType.TYPE_SEARCH_USER))

        setupIndicator()
        setupViewPager()
    }

    private fun setupIndicator() {
        val commonNavigator = CommonNavigator(context)
        //自动调整，平整调整
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mList.size
            }

            override fun getTitleView(context: Context, i: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = ContextCompat.getColor(context, R.color.common_999999)
                colorTransitionPagerTitleView.selectedColor = ContextCompat.getColor(context, R.color.accent)
                colorTransitionPagerTitleView.textSize = 15f
                colorTransitionPagerTitleView.text = getString(mList[i].tabType.nameRes)
                colorTransitionPagerTitleView.setOnClickListener {
                    mViewPager?.setCurrentItem(i, false)
                }

                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY//固定长度的，不随内容变化
                indicator.lineWidth = context.resources.getDimension(R.dimen.px_60)
                indicator.lineHeight = context.resources.getDimension(R.dimen.px_7)
                indicator.setColors(ContextCompat.getColor(context, R.color.accent))
                return indicator
            }
        }

        mMagicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mMagicIndicator, mViewPager)
    }

    @SuppressLint("CheckResult")
    private fun setupViewPager() {
        mAdapter = SearchContentTabAdapter(mList, childFragmentManager)
        mViewPager?.adapter = mAdapter
    }
}