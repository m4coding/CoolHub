package com.m4coding.coolhub.business.base.widgets

import android.content.Context
import android.util.AttributeSet
import com.m4coding.coolhub.widgets.headervp.HeaderViewPager
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @author mochangsheng
 * @description
 */

class BusinessRefreshLayout : SmartRefreshLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
}