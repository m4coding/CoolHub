package com.m4coding.coolhub.business.base.list

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.business.base.utils.RxLifecycleUtils
import com.m4coding.coolhub.business.base.widgets.RxDialog
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


/**
 * @author mochangsheng
 * @description 基本的列表浏览界面Presenter
 */
abstract class BaseListContentPresenter<B, M : IModel, V : IListView>(rootView: V) : BasePresenter<M, V>(rootView) {

    protected var mAdapter: BaseListAdapter<B,*>? = null
    protected var mList: ArrayList<B>? = ArrayList()
    protected var mPage: Int = 1 //page 由api决定，第一页是0，还是1，要设置正确
    protected var mIsLoadMoreEnd: Boolean = false
    protected var mDataIndex: Int = -1 //数据索引
    protected var mDisposable: Disposable? = null

    init {
        initAdapter()
    }

    fun getList(): List<B>? {
        return mList
    }

    override fun onDestroy() {
        super.onDestroy()

        mAdapter = null
        mList = null
        mDisposable = null
    }

    /**
     * 获取数据
     * @param page 页数  （有些接口是按页数获取数据的，需要用到这个）
     * @param dataIndex 数据索引 (当前数据个数)  （有些接口是按索引获取数据的，需要用到这个）
     * @param map  指定的请求参数  （可用参数）
     */
    protected abstract fun getData(page: Int, dataIndex: Int, map: Map<String, Any>): Observable<List<B>>

    /**
     * 适配器初始化
     */
    protected abstract fun initAdapter()

    fun getAdapter(): BaseListAdapter<B,*>? {
        return mAdapter
    }

    /**
     * isPullToRefresh为true下拉刷新，为false首次加载
     * map为传递的参数
     */
    fun refreshData(isPullToRefresh: Boolean, map: Map<String, Any>) {
        mPage = 1
        mDataIndex = 0

        val event = when (mRootView) {
            is AppCompatActivity -> ActivityEvent.DESTROY
            is Fragment -> FragmentEvent.DESTROY
            is RxDialog<*> -> ActivityEvent.DESTROY
            else -> null
        }

        mDisposable = getData(mPage, mDataIndex,  map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    mIsLoadMoreEnd = false
                    if (!isPullToRefresh) {
                        mRootView.startLoad(IListView.LOAD_TYPE_START_LOAD)
                    } else {
                        mRootView.startLoad(IListView.LOAD_TYPE_START_PULL_DOWN_LOAD)
                        mAdapter?.setEnableLoadMore(false)
                    }
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, event))
                .subscribe({
                    mList?.clear()
                    mList?.addAll(it)
                    mDataIndex = mList?.size ?: -1
                    mAdapter?.setNewData(mList)
                    mAdapter?.disableLoadMoreIfNotFullPage()
                }, {
                    it.printStackTrace()
                    if (isPullToRefresh) {
                        if (mList == null || mList?.size == 0) {
                            mRootView.errorLoad(IListView.LOAD_TYPE_ERROR_LOAD)
                        } else {
                            mRootView.errorLoad(IListView.LOAD_TYPE_ERROR_PULL_DOWN_LOAD)
                        }
                    } else {
                        mRootView.errorLoad(IListView.LOAD_TYPE_ERROR_LOAD)
                    }
                    mDataIndex = mList?.size ?: -1
                }, {
                    if (isPullToRefresh) {
                        mRootView.successLoad(IListView.LOAD_TYPE_SUCCESS_PULL_DOWN_LOAD)
                    } else {
                        mRootView.successLoad(IListView.LOAD_TYPE_SUCCESS_LOAD)
                    }
                    mDataIndex = mList?.size ?: -1
                })
    }

    /**
     * 加载更多
     */
    @SuppressLint("CheckResult")
    fun loadMore(map: Map<String, Any>) {

        if (mIsLoadMoreEnd) {
            return
        }

        mPage += 1

        val event = when (mRootView) {
            is AppCompatActivity -> ActivityEvent.DESTROY
            is Fragment -> FragmentEvent.DESTROY
            is RxDialog<*> -> ActivityEvent.DESTROY
            else -> null
        }

        getData(mPage, mDataIndex,  map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{
                    mRootView.startLoad(IListView.LOAD_TYPE_START_MORE_LOAD)
                }
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView, event))
                .subscribe({
                    if (it != null && !it.isEmpty()) {
                        mAdapter?.addData(it)
                        mDataIndex = mList?.size ?: -1
                        mAdapter?.loadMoreComplete()
                    } else {
                        mIsLoadMoreEnd = true
                        mAdapter?.loadMoreEnd()
                    }
                }, {
                    it.printStackTrace()
                    mAdapter?.loadMoreFail()
                    mRootView.errorLoad(IListView.LOAD_TYPE_ERROR_MORE_LOAD)
                    mDataIndex = mList?.size ?: -1

                }, {
                    mRootView.successLoad(IListView.LOAD_TYPE_SUCCESS_MORE_LOAD)
                    mDataIndex = mList?.size ?: -1
                })
    }

}

