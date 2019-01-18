package com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.base.BaseFragment
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.RepositoryIssueAdapter
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

class RepositoryDetailsIssueFragment : BaseFragment(), View.OnClickListener,
        HeaderScrollHelper.ScrollableContainer, HeaderScrollHelper.ScrollableContainerForParent {

    private var mViewPager: ViewPager? = null
    private var mMagicIndicator: MagicIndicator? = null
    private lateinit var mList: ArrayList<TabBean>
    private var mRepoBean: RepoBean? = null
    private var mAdapter: RepositoryIssueAdapter? = null

    private lateinit var mScrollContainer: View

    companion object {

        private const val KEY_REPO = "key_repo"

        fun newInstance(repoBean: RepoBean): RepositoryDetailsIssueFragment {
            val fragment = RepositoryDetailsIssueFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_REPO, repoBean)
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_repository_details_issue, container, false)
    }

    override fun initData() {

    }

    override fun initLazyData() {

        mRepoBean = arguments?.getParcelable(KEY_REPO)

        mViewPager = mRootView.findViewById(R.id.business_mainpage_repo_issue_viewpager)
        mMagicIndicator = mRootView.findViewById(R.id.business_mainpage_repo_issue_top_indicator)

        mList = ArrayList()
        mList.add(TabBean(TabType.TYPE_REPO_DETAILS_ISSUE_OPEN))
        mList.add(TabBean(TabType.TYPE_REPO_DETAILS_ISSUE_CLOSE))

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
               /* val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY//固定长度的，不随内容变化
                indicator.lineWidth = context.resources.getDimension(R.dimen.px_60)
                indicator.lineHeight = context.resources.getDimension(R.dimen.px_7)
                indicator.setColors(ContextCompat.getColor(context, R.color.accent))
                return indicator*/
                return null
            }
        }

        mMagicIndicator?.navigator = commonNavigator
        ViewPagerHelper.bind(mMagicIndicator, mViewPager)
    }

    /**
     *  设置当前的Fragment容器Scroll
     */
    override fun setCurrentScrollableContainer(view: View?) {
        view?.let {
            mScrollContainer = it
        }
    }

    private fun setupViewPager() {
        mRepoBean?.let {
            mAdapter = RepositoryIssueAdapter(it, mList, childFragmentManager)
            mViewPager?.adapter = mAdapter
        }
    }


    override fun onClick(v: View?) {

    }

    override fun getScrollableView(): View {
        return mScrollContainer
    }

}