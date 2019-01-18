package com.m4coding.coolhub.business.base.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.m4coding.business_base.R
import com.m4coding.coolhub.base.base.BasePresenterFragment
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.base.utils.ToastUtils
import com.m4coding.coolhub.widgets.MultiStateView
import com.m4coding.coolhub.widgets.headervp.HeaderScrollHelper
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @author mochangsheng
 * @description 列表型浏览Fragment
 */
abstract class BaseListContentFragment<P : BaseListContentPresenter<*, *, *>> : BasePresenterFragment<P>(), IListView, HeaderScrollHelper.ScrollableContainer {

    protected var mHashMap: HashMap<String, String>? = null
    protected var mMultiStateView: MultiStateView? = null
    protected var mRecyclerView: RecyclerView? = null
    protected var mRefreshView: SmartRefreshLayout? = null
    protected var mIsUseInViewPager: Boolean = true //懒加载对ViewPager才有用

    /**
     * 初始化列表型Presenter
     */
    abstract fun initListPresenter()


    override fun initData() {
        if (!mIsUseInViewPager) {
            initOwnData()
        }
    }

    override fun initLazyData() {
        if (mIsUseInViewPager) {
            initOwnData()
        }
    }

    protected open fun initOwnData() {
        initListPresenter()

        mRecyclerView?.layoutManager = LinearLayoutManager(context)
        mPresenter.getAdapter()?.setPreLoadNumber(15)

        mPresenter.getAdapter()?.bindToRecyclerView(mRecyclerView)
        mPresenter.getAdapter()?.setEmptyView(R.layout.layout_load_empty)

        mPresenter.getAdapter()?.setOnLoadMoreListener({
            mHashMap?.let { it ->
                mPresenter.loadMore(it)
            }
        }, mRecyclerView)

        mRecyclerView?.adapter = mPresenter.getAdapter()

        mMultiStateView?.getView(MultiStateView.VIEW_STATE_ERROR)
                ?.findViewById<View>(R.id.business_base_cl_error)
                ?.setOnClickListener{
                    mHashMap?.let { it1 -> mPresenter.refreshData(false, it1) }
                }


        mRefreshView?.setOnRefreshListener {
            mHashMap?.let { it1 ->
                mPresenter.refreshData(true, it1)
            }
        }

        firstUpdate()
    }

    protected open fun firstUpdate() {
        mHashMap?.let {
            mPresenter.refreshData(false, it)
            /*mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
            //首次加载改为下拉刷新模式
            mRefreshView?.autoRefresh()*/
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mHashMap = null
    }

    /**
     * 开始加载
     * @param type
     */
    @SuppressLint("SwitchIntDef")
    override fun startLoad(type: Int) {
        when(type) {
            IListView.LOAD_TYPE_START_LOAD -> {
                mMultiStateView?.viewState = MultiStateView.VIEW_STATE_LOADING
            }
            IListView.LOAD_TYPE_START_PULL_DOWN_LOAD -> {
                mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        }
    }

    /**
     * 成功加载
     * @param type
     */
    override fun successLoad(type: Int) {
        mMultiStateView?.viewState = MultiStateView.VIEW_STATE_CONTENT
        if (mRefreshView?.isRefreshing == true) {
            mRefreshView?.finishRefresh()
        }
    }

    /**
     * 错误加载
     * @param type
     */
    @SuppressLint("SwitchIntDef")
    override fun errorLoad(type: Int) {
        when (type) {
            IListView.LOAD_TYPE_ERROR_LOAD -> {
                mMultiStateView?.viewState = MultiStateView.VIEW_STATE_ERROR
            }
            IListView.LOAD_TYPE_ERROR_PULL_DOWN_LOAD -> {
                mRefreshView?.finishRefresh()
                showMessage(getString(R.string.business_base_network_not_good))
            }
        }
    }

    /**
     * 显示信息
     */
    override fun showMessage(message: String?) {
        ToastUtils.showShort(message)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setCurrentScrollableContainer()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

        setCurrentScrollableContainer()
    }

    private fun setCurrentScrollableContainer() {
        if (mIsVisible) {
            val fragment = parentFragment
            if (fragment is HeaderScrollHelper.ScrollableContainerForParent) {
                fragment.setCurrentScrollableContainer(mRecyclerView)
            }
        }
    }

    override fun getScrollableView(): View? {
        return mRecyclerView
    }

}