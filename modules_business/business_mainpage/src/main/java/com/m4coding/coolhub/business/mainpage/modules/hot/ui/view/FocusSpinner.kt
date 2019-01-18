package com.m4coding.coolhub.business.mainpage.modules.hot.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.Spinner

/**
 * @author mochangsheng
 * @description
 */
class FocusSpinner(context: Context,  attrs: AttributeSet) : Spinner(context, attrs) {

    private var mOnWindowFocusChangedListener: OnWindowFocusChangedListener? = null

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)

        mOnWindowFocusChangedListener?.onWindowFocusChanged(hasWindowFocus)
    }

    fun setOnWindowFocusChangedListener(onWindowFocusChangedListener: OnWindowFocusChangedListener) {
        mOnWindowFocusChangedListener = onWindowFocusChangedListener
    }

    interface OnWindowFocusChangedListener {
        fun onWindowFocusChanged(hasWindowFocus: Boolean)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        mOnWindowFocusChangedListener = null
    }

}