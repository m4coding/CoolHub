package com.m4coding.coolhub.business.base.widgets

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.BaseAdapter
import com.flyco.dialog.entity.DialogMenuItem
import com.flyco.dialog.widget.ActionSheetDialog

class CustomSheetDialog : ActionSheetDialog {
    constructor(context: Context, adapter: BaseAdapter, animateView: View) : super(context, adapter, animateView)

    constructor(context: Context, baseItems: ArrayList<DialogMenuItem>, animateView: View) : super(context, baseItems, animateView)

    constructor(context: Context?, items: Array<out String>?, animateView: View?) : super(context, items, animateView)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mLlControlHeight.gravity = Gravity.BOTTOM
    }

}