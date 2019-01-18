package com.m4coding.coolhub.business.mainpage.modules.details.ui.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

class MdScrollWebView : WebView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE.shr(2), MeasureSpec.AT_MOST)
        super.onMeasure(widthMeasureSpec, mExpandSpec)
    }
}