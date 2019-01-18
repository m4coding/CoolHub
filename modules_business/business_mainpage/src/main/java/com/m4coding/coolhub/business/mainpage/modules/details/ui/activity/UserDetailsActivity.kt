package com.m4coding.coolhub.business.mainpage.modules.details.ui.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gyf.barlibrary.ImmersionBar
import com.m4coding.coolhub.api.datasource.UserDataSource
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.base.BaseActivity
import com.m4coding.coolhub.base.utils.BarUtils
import com.m4coding.coolhub.base.utils.ImageUtils
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConfig
import com.m4coding.coolhub.base.utils.imageloader.listener.SimpleImageLoadingListener
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.UserDetailsViewPagerAdapter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.UserDetailsTitleFragment
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.UserDetailsEvent
import com.m4coding.coolhub.business.mainpage.modules.details.ui.view.DetailsPullRefreshLayout
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.*
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import org.greenrobot.eventbus.EventBus

/**
 * @author mochangsheng
 * @description  用户详情主页
 */
class UserDetailsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mList: ArrayList<TabBean>
    private var mViewPager: ViewPager? = null
    private var mMagicIndicator: MagicIndicator? = null
    private var mHeaderViewPager: HeaderViewPager? = null
    private var mStateView: MultiStateView? = null
    private var mAdapter: UserDetailsViewPagerAdapter? = null
    private var mDetailsPullRefreshLayout: DetailsPullRefreshLayout? = null
    private var mHeaderBgImageView: ImageView? = null
    private var mHeaderBgLayout: ViewGroup? = null

    private var mTitleFragment: UserDetailsTitleFragment? = null

    companion object {

        private const val KEY_USER_NAME = "user_name"

        fun newInstance(context: Context, username: String) {
            val intent = Intent()
            intent.component = ComponentName(context, UserDetailsActivity::class.java)
            intent.putExtra(KEY_USER_NAME, username)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this).init()
    }

    override fun onDestroy() {
        super.onDestroy()

        ImmersionBar.with(this).destroy()//必须调用该方法，防止内存泄漏
    }

    override fun initView() {
        setContentView(R.layout.business_mainpage_activity_user_details)
    }

    override fun initData() {
        mDetailsPullRefreshLayout = findViewById(R.id.business_mainpage_user_details_root_sm_refresh_l)
        mStateView = findViewById(R.id.business_mainpage_user_details_state_view)
        mHeaderViewPager = findViewById(R.id.business_mainpage_user_details_header_pager)
        mViewPager = findViewById(R.id.business_mainpage_user_details_viewpager)
        mMagicIndicator = findViewById(R.id.business_mainpage_user_details_top_indicator)
        mHeaderBgImageView = findViewById(R.id.business_mainpage_user_details_header_iv_bg)
        mHeaderBgLayout = findViewById(R.id.business_mainpage_user_details_header_cl_bg)

        mDetailsPullRefreshLayout?.setHeaderViewPager(mHeaderViewPager)
        mDetailsPullRefreshLayout?.setOnRefreshListener {
            update(true)
        }

        mStateView?.setStateListener(object : MultiStateView.StateListener {
            override fun onStateChanged(viewState: Int) {
            }

            @SuppressLint("SwitchIntDef")
            override fun onStateInflated(viewState: Int, view: View) {
                when(viewState) {
                    VIEW_STATE_ERROR -> {
                        view.findViewById<View>(R.id.business_base_cl_error)
                                ?.setOnClickListener(this@UserDetailsActivity)
                    }
                }
            }

        })

        mList = ArrayList()
        mList.add(TabBean(TabType.TYPE_USER_DETAILS_INFO))
        mList.add(TabBean(TabType.TYPE_DYNAMIC))

        setupTitle()
        setupHeader()
        setupIndicator()
        setupViewPager()
    }

    private fun setupTitle() {
        mTitleFragment = UserDetailsTitleFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.business_mainpage_user_details_fl_title_container, mTitleFragment)/*.hide(mTitleFragment)*/.commit()
        mHeaderViewPager?.setTopOffset(BarUtils.getActionBarHeight(this) + BarUtils.getStatusBarHeight())
        mHeaderViewPager?.setOnScrollListener { currentY, maxY ->
            mTitleFragment?.setScrollChanges(currentY, maxY)
        }
    }

    private fun setupHeader() {
        mDetailsPullRefreshLayout?.setOnMultiPurposeListener(object : OnMultiPurposeListener {
            override fun onFooterPulling(footer: RefreshFooter?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                mHeaderBgLayout?.scaleX = 1.0f + percent
                mHeaderBgLayout?.scaleY = 1.0f + percent
            }

            override fun onHeaderStartAnimator(header: RefreshHeader?, headerHeight: Int, extendHeight: Int) {
            }

            override fun onFooterReleased(footer: RefreshFooter?, footerHeight: Int, extendHeight: Int) {
            }

            override fun onStateChanged(refreshLayout: RefreshLayout?, oldState: RefreshState?, newState: RefreshState?) {
            }

            override fun onFooterFinish(footer: RefreshFooter?, success: Boolean) {
            }

            override fun onFooterStartAnimator(footer: RefreshFooter?, footerHeight: Int, extendHeight: Int) {
            }

            override fun onFooterReleasing(footer: RefreshFooter?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
            }

            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                mHeaderBgLayout?.scaleX = 1.0f + percent
                mHeaderBgLayout?.scaleY = 1.0f + percent
            }

            override fun onHeaderReleased(header: RefreshHeader?, headerHeight: Int, extendHeight: Int) {
            }

            override fun onLoadMore(refreshLayout: RefreshLayout?) {
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
            }

            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
            }

        })
    }

    private fun setupIndicator() {
        val commonNavigator = CommonNavigator(this)
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
        if (mAdapter?.getFragmentByPosition(position) is HeaderScrollHelper.ScrollableContainer) {
            mHeaderViewPager?.setCurrentScrollableContainer(mAdapter?.getFragmentByPosition(position) as HeaderScrollHelper.ScrollableContainer)
        }
    }

    @SuppressLint("CheckResult")
    private fun setupViewPager() {
       update(false)
    }

    @SuppressLint("CheckResult")
    private fun update(isPullRefresh: Boolean) {
        val username = intent.getStringExtra(KEY_USER_NAME)
        UserDataSource.getUserInfo(username)
                .doOnSubscribe {
                    if (null == mAdapter) {
                        mStateView?.viewState = VIEW_STATE_LOADING
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this@UserDetailsActivity, ActivityEvent.DESTROY))
                .subscribe({
                    if (null == mAdapter) {
                        mAdapter = UserDetailsViewPagerAdapter(it, mList, supportFragmentManager)
                        mAdapter?.setOnHandleListener(object :
                                CustomFragmentStatePagerAdapter.SimpleAdapterHandleLister() {
                            override fun onInstantiateItem(container: ViewGroup, position: Int) {
                                mViewPager?.currentItem?.let {
                                    setCurrentScrollableContainer(it)
                                }
                            }
                        })
                        mViewPager?.adapter = mAdapter
                        mStateView?.viewState = VIEW_STATE_CONTENT
                    }

                    //只解码时，设置自己的config，避免其他的干扰
                    val config: ImageLoaderConfig = ImageLoaderConfig.Builder().build()
                    config.isOnlyDecodeImage = true
                    config.imageLoadingListener = object : SimpleImageLoadingListener() {
                        override fun onLoadingComplete(imageUri: String?, view: View?, bitmap: Bitmap?) {
                            mHeaderBgImageView?.setImageBitmap(ImageUtils.fastBlur(bitmap, 1.0f, 25f))
                        }
                    }
                    ImageLoader.beginNoDefaultConfig()
                            .displayImage(it.avatarUrl, mHeaderBgImageView, config)

                    EventBus.getDefault().post(UserDetailsEvent(UserDetailsEvent.TYPE_UPDATE, it))
                    mDetailsPullRefreshLayout?.finishRefresh()
                }, {
                    it.printStackTrace()
                    if (null == mAdapter) {
                        mStateView?.viewState = VIEW_STATE_ERROR
                    } else {
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    }
                    mDetailsPullRefreshLayout?.finishRefresh()
                })
    }


    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.business_base_cl_error -> {
                update(false)
            }
        }
    }
}