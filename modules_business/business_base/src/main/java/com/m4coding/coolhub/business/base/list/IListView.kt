package com.m4coding.coolhub.business.base.list

import android.support.annotation.IntDef
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m4coding.coolhub.base.mvp.IView
import kotlin.annotation.Retention

/**
 * @author mochangsheng
 * @description
 */
interface IListView : IView {

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(LOAD_TYPE_START_LOAD, LOAD_TYPE_START_PULL_DOWN_LOAD, LOAD_TYPE_START_MORE_LOAD, LOAD_TYPE_SUCCESS_LOAD, LOAD_TYPE_SUCCESS_PULL_DOWN_LOAD, LOAD_TYPE_SUCCESS_MORE_LOAD, LOAD_TYPE_ERROR_LOAD, LOAD_TYPE_ERROR_PULL_DOWN_LOAD, LOAD_TYPE_ERROR_MORE_LOAD)
    annotation class LoadType

    /**
     * 开始加载
     * @param type
     */
    fun startLoad(@LoadType type: Int)

    /**
     * 成功加载
     * @param type
     */
    fun successLoad(@LoadType type: Int)

    /**
     * 错误加载
     * @param type
     */
    fun errorLoad(@LoadType type: Int)

    companion object {
        const val LOAD_TYPE_START_LOAD = 1 //开始加载
        const val LOAD_TYPE_START_PULL_DOWN_LOAD = 2 //开始下拉刷新
        const val LOAD_TYPE_START_MORE_LOAD = 3 //开始上拉加载更多

        const val LOAD_TYPE_SUCCESS_LOAD = 10 //成功加载完成
        const val LOAD_TYPE_SUCCESS_PULL_DOWN_LOAD = 11 //成功下拉刷新完成
        const val LOAD_TYPE_SUCCESS_MORE_LOAD = 12 // 成功上拉加载更多完成

        const val LOAD_TYPE_ERROR_LOAD = 20 //错误加载
        const val LOAD_TYPE_ERROR_PULL_DOWN_LOAD = 21 //下拉刷新错误
        const val LOAD_TYPE_ERROR_MORE_LOAD = 22 //上拉加载错误
    }
}