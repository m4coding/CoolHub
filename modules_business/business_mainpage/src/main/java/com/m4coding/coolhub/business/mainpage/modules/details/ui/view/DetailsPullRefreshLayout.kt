package com.m4coding.coolhub.business.mainpage.modules.details.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DetailsPullRefreshLayout : SmartRefreshLayout {

    private var mHeaderViewPager: HeaderViewPager? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        return if (mHeaderViewPager?.canPtr() == true) {
            super.dispatchTouchEvent(e)
        } else {
            mHeaderViewPager?.dispatchTouchEvent(e) ?: false
        }
    }

    fun setHeaderViewPager(view: HeaderViewPager?) {
        mHeaderViewPager = view
    }
}