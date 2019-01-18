package com.m4coding.coolhub.business.base.component

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * @author mochangsheng
 * @description
 */
object ComponentUtils {

    /**
     * 获取定制的Intent，适用于组件化间跳转
     */
    fun getIntent(context: Context): Intent {
        val intent = Intent()
        if (context !is Activity) {
            //调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        return intent
    }
}