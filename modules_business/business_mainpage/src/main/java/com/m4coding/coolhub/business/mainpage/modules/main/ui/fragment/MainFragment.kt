package com.m4coding.coolhub.business.mainpage.modules.main.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.m4coding.coolhub.api.datasource.LoginDataSource
import com.m4coding.coolhub.base.base.BasePresenterFragment
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.main.contract.MainContract
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import com.m4coding.coolhub.business.mainpage.modules.main.presenter.MainPresenter
import com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter.MainAdapter
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager
import com.trello.rxlifecycle2.android.FragmentEvent
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView

/**
 * @author mochangsheng
 * @description
 */
class MainFragment : BasePresenterFragment<MainPresenter>(), MainContract.View {

    private lateinit var mList: ArrayList<TabBean>
    private var mViewPager: ViewPager? = null
    private var mMagicIndicator: MagicIndicator? = null
    private var mHeaderViewPager: HeaderViewPager? = null
    private var mMainAdapter: MainAdapter? = null

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_main, container, false)
    }

    override fun initData() {
        mPresenter = MainPresenter(this@MainFragment)
    }

    override fun initLazyData() {
        mHeaderViewPager = mRootView.findViewById(R.id.business_mainpage_main_header_pager)
        mViewPager = mRootView.findViewById(R.id.business_mainpage_main_viewpager)
        mMagicIndicator = mRootView.findViewById(R.id.business_mainpage_main_top_indicator)

        mList = ArrayList()
        mList.add(TabBean(TabType.TYPE_RECOMMEND))
        mList.add(TabBean(TabType.TYPE_DYNAMIC_FOCUS))

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
                //indicator.yOffset = context.resources.getDimension(R.dimen.px_6)
                indicator.setColors(ContextCompat.getColor(context, R.color.accent))
                //indicator.roundRadius = context.resources.getDimension(R.dimen.px_20)
                return indicator
            }
        }

        mMagicIndicator?.navigator = commonNavigator
        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                mMagicIndicator?.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                mMagicIndicator?.onPageSelected(position)

                setCurrentScrollableContainer(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                mMagicIndicator?.onPageScrollStateChanged(state)
            }
        })
    }

    //设置当前的Fragment容器
    private fun setCurrentScrollableContainer(position: Int) {
        if (mMainAdapter?.getFragmentByPosition(position) is HeaderScrollHelper.ScrollableContainer) {
            mHeaderViewPager?.setCurrentScrollableContainer(mMainAdapter?.getFragmentByPosition(position) as HeaderScrollHelper.ScrollableContainer)
        }
    }

    @SuppressLint("CheckResult")
    private fun setupViewPager() {
        LoginDataSource.getLoginUserInfo()
                .compose(RxLifecycleUtils.bindToLifecycle(this, FragmentEvent.DESTROY_VIEW))
                .subscribe({
                    mMainAdapter = MainAdapter(it, mList, childFragmentManager)
                    mMainAdapter?.setOnHandleListener(object :
                            CustomFragmentStatePagerAdapter.SimpleAdapterHandleLister() {
                        override fun onInstantiateItem(container: ViewGroup, position: Int) {
                            mViewPager?.currentItem?.let {
                                setCurrentScrollableContainer(it)
                            }
                        }
                    })
                    mViewPager?.adapter = mMainAdapter
                }, {
                    it.printStackTrace()
                })
    }


    /**
     * 显示信息
     */
    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }

}