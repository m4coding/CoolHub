package com.m4coding.coolhub.business.mainpage

import android.content.ComponentName
import android.content.Context
import android.view.KeyEvent
import android.view.View
import com.m4coding.coolhub.base.base.BaseActivity
import com.m4coding.coolhub.business.base.component.ComponentUtils
import com.m4coding.coolhub.business.mainpage.adapter.MainBottomAdapter
import com.m4coding.coolhub.business.mainpage.bean.BottomTitleBean
import kotlinx.android.synthetic.main.business_mainpage_activity_mainpage.*
import kotlinx.android.synthetic.main.business_mainpage_item_bottom.view.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import kotlin.collections.ArrayList
import com.m4coding.coolhub.base.utils.ToastUtils


/**
 * @author mochangsheng
 * @description  首页
 */
class MainPageActivity : BaseActivity() {

    private lateinit var mBottomList: ArrayList<BottomTitleBean>
    private var mFirstExitTime: Long = 0

    companion object {
        fun newInstance(context: Context) {
            val intent = ComponentUtils.getIntent(context)
            intent.component = ComponentName(context, MainPageActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        setContentView(R.layout.business_mainpage_activity_mainpage)
    }

    override fun initData() {

        mBottomList = ArrayList()
        mBottomList.add(BottomTitleBean(R.string.business_mainpage_main,
                R.drawable.business_mainpage_vc_ic_mainpage, R.drawable.business_mainpage_vc_ic_mainpage_not_select))
        mBottomList.add(BottomTitleBean(R.string.business_mainpage_hot,
                R.drawable.business_mainpage_vc_ic_hot, R.drawable.business_mainpage_vc_ic_hot_not_select))
        mBottomList.add(BottomTitleBean(R.string.business_mainpage_mine,
                R.drawable.business_mainpage_vc_ic_mine, R.drawable.business_mainpage_vc_ic_mine_not_select))

        setupBottomIndicator()
        setupViewPager()
    }

    private fun setupBottomIndicator() {
        val commonNavigator = CommonNavigator(this)
        //自动调整，平整调整
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
               return mBottomList.size
            }

            override fun getTitleView(context: Context, i: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)
                commonPagerTitleView.setContentView(R.layout.business_mainpage_item_bottom)
                val titleBean = mBottomList[i]
                titleBean.titleRes?.let { commonPagerTitleView.business_mainpage_bottom_text.setText(it) }
                titleBean.drawableRes?.let { commonPagerTitleView.business_mainpage_bottom_iv.setImageResource(it) }

                val clickListener = View.OnClickListener {
                    business_mainpage_viewpager.setCurrentItem(i, false)
                }
                commonPagerTitleView.business_mainpage_bottom_text.setOnClickListener(clickListener)
                commonPagerTitleView.business_mainpage_bottom_iv.setOnClickListener(clickListener)
                commonPagerTitleView.setOnClickListener(clickListener)

                commonPagerTitleView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener {
                    override fun onSelected(index: Int, totalCount: Int) {
                        commonPagerTitleView.isSelected = true
                        titleBean.drawableRes?.let { commonPagerTitleView.business_mainpage_bottom_iv.setImageResource(it) }
                    }

                    override fun onDeselected(index: Int, totalCount: Int) {
                        commonPagerTitleView.isSelected = false
                        titleBean.drawableResInNotSelect?.let { commonPagerTitleView.business_mainpage_bottom_iv.setImageResource(it) }
                    }

                    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {

                    }

                    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {

                    }
                }

                return commonPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return null
            }
        }

        business_mainpage_bottom_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(business_mainpage_bottom_indicator, business_mainpage_viewpager)
        business_mainpage_viewpager.isCanScroll = false
    }

    private fun setupViewPager() {
        //设置viewpager预先加载的页数
        business_mainpage_viewpager.offscreenPageLimit = 2
        business_mainpage_viewpager.adapter = MainBottomAdapter(supportFragmentManager, mBottomList)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - mFirstExitTime > 2000) {
                ToastUtils.showShort(R.string.business_mainpage_exit_app)
                mFirstExitTime = System.currentTimeMillis()
            } else {
                finish()
            }
            return true
        }

        return super.onKeyDown(keyCode, event)
    }
}