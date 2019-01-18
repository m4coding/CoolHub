package com.m4coding.coolhub.business.mainpage.modules.details.ui.view

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.m4coding.business_base.R
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.header.ClassicsHeader

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DetailsPullRefreshHeader : MaterialHeader {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setColorSchemeColors(ContextCompat.getColor(context, R.color.accent))
    }

    //重写，避免requestDefaultHeaderTranslationContent(false)导致contentView不能跟随header下移
    override fun onInitialized(kernel: RefreshKernel, height: Int, extendHeight: Int) {

    }
}