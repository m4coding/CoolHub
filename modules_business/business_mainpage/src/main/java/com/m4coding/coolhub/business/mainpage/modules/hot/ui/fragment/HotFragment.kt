package com.m4coding.coolhub.business.mainpage.modules.hot.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m4coding.coolhub.api.datasource.bean.HotDataBean
import com.m4coding.coolhub.base.utils.SPUtils
import com.m4coding.coolhub.business.base.component.ParamsMapKey
import com.m4coding.coolhub.business.base.list.BaseListContentFragment
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity
import com.m4coding.coolhub.business.mainpage.modules.hot.contract.HotContract
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotLanguageBean
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotTimeBean
import com.m4coding.coolhub.business.mainpage.modules.hot.presenter.HotPresenter
import com.m4coding.coolhub.business.mainpage.modules.hot.ui.dialog.LanguageFragment
import com.m4coding.coolhub.business.mainpage.modules.hot.ui.dialog.TimeFragment
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.MultiStateView.VIEW_STATE_LOADING
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager


/**
 * @author mochangsheng
 * @description   热点Fragment
 */
class HotFragment : BaseListContentFragment<HotPresenter>(), HotContract.View , View.OnClickListener{

    private lateinit var mLanguageView: TextView
    private lateinit var mTimeView: TextView
    private lateinit var mTimeArrowView: ImageView
    private lateinit var mLanguageArrowView: ImageView
    private lateinit var mHeaderViewPager: HeaderViewPager

    private var mTimePopup: TimeFragment? = null
    private var mLanguagePopup: LanguageFragment? = null

    companion object {
        fun newInstance(): HotFragment {
            return HotFragment()
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?): View? {
        return inflater?.inflate(R.layout.business_mainpage_fragment_hot, container, false)
    }

    override fun initData() {
        mHeaderViewPager = mRootView.findViewById(R.id.business_mainpage_hot_header_pager)
        mLanguageView = mRootView.findViewById(R.id.business_mainpage_hot_top_tv_language)
        mTimeView = mRootView.findViewById(R.id.business_mainpage_hot_top_tv_day)

        mLanguageArrowView = mRootView.findViewById(R.id.business_mainpage_hot_iv_language_arrow)
        mTimeArrowView = mRootView.findViewById(R.id.business_mainpage_hot_iv_time_arrow)

        mMultiStateView = mRootView.findViewById(R.id.business_mainpage_hot_state_view)
        mRecyclerView = mRootView.findViewById(R.id.business_mainpage_hot_recyclerview)
        mRefreshView = mRootView.findViewById(R.id.business_mainpage_hot_sm_refresh_l)

        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_LOADING

        mHashMap = HashMap()
    }

    override fun initListPresenter() {
        initSelectView()
        mHeaderViewPager.setCurrentScrollableContainer(this@HotFragment)
        mPresenter = HotPresenter(this@HotFragment)
    }

    override fun initLazyData() {
        super.initLazyData()

        mPresenter.getAdapter()?.setOnLoadMoreListener(null, mRecyclerView)

        mPresenter.getAdapter()?.setOnItemClickListener { adapter, view, position ->
            val hotDataBean = adapter.data[position] as HotDataBean
            context?.let {
                RepositoryDetailsActivity.newInstance(it,
                        hotDataBean.ownerName ?: "", hotDataBean.repositoryName ?: "")
            }
        }
    }

    private fun initSelectView() {

        mTimeView.setOnClickListener(this)
        mLanguageView.setOnClickListener(this)

        val language = SPUtils.getInstance().getString(ParamsMapKey.LANGUAGE, HotLanguageBean.ALL)
        val time = SPUtils.getInstance().getString(ParamsMapKey.SINCE, HotTimeBean.WEEK)

        mHashMap?.put(ParamsMapKey.LANGUAGE, language)
        mHashMap?.put(ParamsMapKey.SINCE, time)

        mLanguageView.text = HotLanguageBean.getNameByLanguage(language)
        mTimeView.text = HotTimeBean.getNameByTime(time)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.business_mainpage_hot_top_tv_day -> {
                if (mRefreshView?.isRefreshing == true || mMultiStateView?.viewState == VIEW_STATE_LOADING) {
                    showMessage(getString(R.string.now_refresh))
                    return
                }
                if (mTimePopup == null) {
                    mTimePopup = TimeFragment()
                    mTimePopup?.setArrowView(mTimeArrowView)
                    mTimePopup?.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener {
                        adapter, view, position ->
                        val timeBean: HotTimeBean = adapter.getItem(position) as HotTimeBean
                        mHashMap?.put(ParamsMapKey.SINCE, timeBean.time ?: "")
                        SPUtils.getInstance().put(ParamsMapKey.SINCE, timeBean.time)
                        mTimeView.text = timeBean.name

                        mRefreshView?.autoRefresh()
                        mTimePopup?.dismiss()
                    })
                    mTimePopup?.show(mTimeView)
                } else {
                    if (mTimePopup?.isShowing == true) {
                        mTimePopup?.dismiss()
                    } else {
                        mTimePopup?.show(mTimeView)
                    }
                }
            }
            R.id.business_mainpage_hot_top_tv_language -> {
                if (mRefreshView?.isRefreshing == true || mMultiStateView?.viewState == VIEW_STATE_LOADING) {
                    showMessage(getString(R.string.now_refresh))
                    return
                }
                if (mLanguagePopup == null) {
                    mLanguagePopup = LanguageFragment()
                    mLanguagePopup?.setArrowView(mLanguageArrowView)
                    mLanguagePopup?.setOnItemClickListener(BaseQuickAdapter.OnItemClickListener {
                        adapter, view, position ->
                        val languageBean: HotLanguageBean = adapter.getItem(position) as HotLanguageBean
                        mHashMap?.put(ParamsMapKey.LANGUAGE, languageBean.language ?: HotLanguageBean.ALL)
                        SPUtils.getInstance().put(ParamsMapKey.LANGUAGE, languageBean.language)
                        mLanguageView.text = languageBean.name

                        mRefreshView?.autoRefresh()
                        mLanguagePopup?.dismiss()
                    })
                    mLanguagePopup?.show(mLanguageView)
                } else {
                    if (mLanguagePopup?.isShowing == true) {
                        mLanguagePopup?.dismiss()
                    } else {
                        mLanguagePopup?.show(mLanguageView)
                    }
                }
            }
        }
    }

}