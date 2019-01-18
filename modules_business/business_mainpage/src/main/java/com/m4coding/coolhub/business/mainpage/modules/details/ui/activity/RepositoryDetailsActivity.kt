package com.m4coding.coolhub.business.mainpage.modules.details.ui.activity

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.DataSetObserver
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gyf.barlibrary.ImmersionBar
import com.m4coding.coolhub.api.datasource.RepoDataSource
import com.m4coding.coolhub.api.datasource.bean.BranchBean
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.api.exception.NetExceptionHelper
import com.m4coding.coolhub.base.base.BaseActivity
import com.m4coding.coolhub.base.base.IFragmentKeyListener
import com.m4coding.coolhub.base.utils.BarUtils
import com.m4coding.coolhub.base.utils.ImageUtils
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.base.utils.imageloader.ImageLoaderConfig
import com.m4coding.coolhub.base.utils.imageloader.listener.SimpleImageLoadingListener
import com.m4coding.coolhub.base.utils.log.MLog
import com.m4coding.coolhub.business.base.adapter.CustomFragmentStatePagerAdapter
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.bean.TabType
import com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter.RepositoryDetailsViewPagerAdapter
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.RepositoryDetailsBottomTabFragment
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.RepositoryDetailsHeaderFragment
import com.m4coding.coolhub.business.mainpage.modules.details.ui.fragment.RepositoryDetailsTitleFragment
import com.m4coding.coolhub.business.mainpage.modules.details.ui.misc.RepoDetailsEvent
import com.m4coding.coolhub.business.mainpage.modules.main.model.bean.TabBean
import com.m4coding.coolhub.widgets.CustomViewPager
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager
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
 * @description 仓库详情页
 */
class RepositoryDetailsActivity  : BaseActivity(), View.OnClickListener {

    private lateinit var mList: ArrayList<TabBean>
    private var mViewPager: CustomViewPager? = null
    private var mMagicIndicator: MagicIndicator? = null
    private var mHeaderViewPager: HeaderViewPager? = null
    private var mStateView: MultiStateView? = null
    private var mAdapter: RepositoryDetailsViewPagerAdapter? = null
    private var mHeaderBgImageView: ImageView? = null
    private var mHeaderBgLayout: ViewGroup? = null

    private var mTitleFragment: RepositoryDetailsTitleFragment? = null
    private var mHeaderFragment: RepositoryDetailsHeaderFragment? = null
    private var mBottomTabFragment: RepositoryDetailsBottomTabFragment? = null

    private var mCurrentBranch: BranchBean? = null

    companion object {

        private const val KEY_USER_NAME = "user_name"
        private const val KEY_REPO_NAME = "repo_name"

        fun newInstance(context: Context, username: String, repoName: String) {
            val intent = Intent()
            intent.component = ComponentName(context, RepositoryDetailsActivity::class.java)
            intent.putExtra(KEY_USER_NAME, username)
            intent.putExtra(KEY_REPO_NAME, repoName)
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
        setContentView(R.layout.business_mainpage_activity_repository_details)
    }

    override fun initData() {
        mStateView = findViewById(R.id.business_mainpage_repository_details_state_view)
        mHeaderViewPager = findViewById(R.id.business_mainpage_repository_details_header_pager)
        mViewPager = findViewById(R.id.business_mainpage_repository_details_viewpager)
        mMagicIndicator = findViewById(R.id.business_mainpage_repository_details_top_indicator)
        mHeaderBgImageView = findViewById(R.id.business_mainpage_repository_details_header_iv_bg)
        mHeaderBgLayout = findViewById(R.id.business_mainpage_repository_details_header_cl_bg)

        mStateView?.setStateListener(object : MultiStateView.StateListener {
            override fun onStateChanged(viewState: Int) {
            }

            @SuppressLint("SwitchIntDef")
            override fun onStateInflated(viewState: Int, view: View) {
                when(viewState) {
                    MultiStateView.VIEW_STATE_ERROR -> {
                        view.findViewById<View>(R.id.business_base_cl_error)
                                ?.setOnClickListener(this@RepositoryDetailsActivity)
                    }
                }
            }

        })

        mList = ArrayList()
        mList.add(TabBean(TabType.TYPE_REPO_DETAILS_INFO))
        mList.add(TabBean(TabType.TYPE_REPO_DETAILS_FILE))
        mList.add(TabBean(TabType.TYPE_REPO_DETAILS_ISSUE))

        setupTitle()
        setupIndicator()
        setupViewPager()
    }

    private fun setupTitle() {
        if (null == mTitleFragment) {
            mTitleFragment = RepositoryDetailsTitleFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.business_mainpage_repository_details_fl_title_container, mTitleFragment).commit()
            mHeaderViewPager?.setTopOffset(BarUtils.getActionBarHeight(this) + BarUtils.getStatusBarHeight())
            mHeaderViewPager?.setOnScrollListener { currentY, maxY ->
                MLog.d("headerscroll currentY==$currentY, maxY==$maxY")
                mTitleFragment?.setScrollChanges(currentY, maxY)
            }
        }
    }

    private fun setupHeader(repoBean: RepoBean) {
        if (null == mHeaderFragment) {
            mHeaderFragment = RepositoryDetailsHeaderFragment.newInstance(repoBean)
            supportFragmentManager.beginTransaction()
                    .replace(R.id.business_mainpage_repository_details_fl_header_container, mHeaderFragment).commit()
        }
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
        //预加载2个
        mViewPager?.offscreenPageLimit = 2
        update()
    }

    fun getCurrentBranch(): BranchBean? {
        return mCurrentBranch
    }

    fun setCurrentBranch(currentBranch: BranchBean) {
        mCurrentBranch = currentBranch
        //发送切换分支事件
        EventBus.getDefault().post(RepoDetailsEvent(RepoDetailsEvent.TYPE_UPDATE_BRANCH, mCurrentBranch))
    }

    @SuppressLint("CheckResult")
    private fun update() {
        val username = intent.getStringExtra(KEY_USER_NAME)
        val repoName = intent.getStringExtra(KEY_REPO_NAME)
        RepoDataSource.getRepoInfo(username, repoName)
                .doOnSubscribe {
                    if (null == mAdapter) {
                        mStateView?.viewState = MultiStateView.VIEW_STATE_LOADING
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(this@RepositoryDetailsActivity, ActivityEvent.DESTROY))
                .subscribe({
                    if (null == mAdapter) {
                        //刚开始时，设置默认分支为当前分支
                        mCurrentBranch = BranchBean()
                        mCurrentBranch?.name = it?.defaultBranch ?: ""
                        mAdapter = RepositoryDetailsViewPagerAdapter(it, mList, supportFragmentManager)
                        mAdapter?.setOnHandleListener(object :
                                CustomFragmentStatePagerAdapter.SimpleAdapterHandleLister() {
                            override fun onInstantiateItem(container: ViewGroup, position: Int) {
                                mViewPager?.currentItem?.let {
                                    setCurrentScrollableContainer(it)
                                }
                            }
                        })
                        mViewPager?.adapter = mAdapter
                        mStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
                    }

                    mTitleFragment?.updateData(it)
                    setupHeader(it)

                    if (null == mBottomTabFragment) {
                        val mBottomTabFragment = RepositoryDetailsBottomTabFragment.newInstance(it)
                        supportFragmentManager.beginTransaction()
                                .replace(R.id.business_mainpage_repository_details_fl_bottom_tab_container, mBottomTabFragment).commit()
                    } else {
                        mBottomTabFragment?.updateState()
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
                            .displayImage(it.owner?.avatarUrl, mHeaderBgImageView, config)

                }, {
                    it.printStackTrace()
                    if (null == mAdapter) {
                        mStateView?.viewState = MultiStateView.VIEW_STATE_ERROR
                    } else {
                        ToastUtils.showShort(NetExceptionHelper.wrapException(it).displayMessage)
                    }
                })
    }

    /**
     * 设置ViewPager是否可以滚动
     */
    fun setViewPagerScroll(canScroll: Boolean) {
        mViewPager?.isCanScroll = canScroll
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.business_base_cl_error -> {
                update()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val fragment = mAdapter?.getFragmentByPosition(mViewPager?.currentItem ?: -1)
        return if (fragment != null
                && fragment is IFragmentKeyListener
                && (fragment as IFragmentKeyListener).onKeyDown(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}