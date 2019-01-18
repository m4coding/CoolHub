package com.m4coding.coolhub.business.base.widgets

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import com.m4coding.business_base.R
import com.scwang.smartrefresh.header.MaterialHeader

/**
 * @author mochangsheng
 * @description
 */
class BusinessRefreshHeader : MaterialHeader {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
    }
}