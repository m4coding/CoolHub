package com.m4coding.coolhub.business.base.list

import android.support.annotation.LayoutRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @author mochangsheng
 * @description
 */
abstract class BaseListAdapter<T, K : BaseViewHolder> : BaseQuickAdapter<T, K> {

    constructor(@LayoutRes layoutResId: Int, data: List<T>?) : super(layoutResId, data)

    constructor(data: List<T>?) : super(data)

    constructor(@LayoutRes layoutResId: Int) :super(layoutResId)
}