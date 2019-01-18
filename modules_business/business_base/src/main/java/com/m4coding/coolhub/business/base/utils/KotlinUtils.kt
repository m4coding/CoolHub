package com.m4coding.coolhub.business.base.utils

import android.support.annotation.StringRes
import com.m4coding.coolhub.base.base.BaseApplication

object KotlinUtils {

    /**
     * 所有不为空时执行
     */
    fun <T: Any, R: Any> whenAllNotNull(vararg options: T?, block: (List<T>)->R) {
        if (options.all { it != null }) {
            block(options.filterNotNull())
        }
    }


    /**
     * 有一个不为空就执行
     */
    fun <T: Any, R: Any> whenAnyNotNull(vararg options: T?, block: (List<T>)->R) {
        if (options.any { it != null }) {
            block(options.filterNotNull())
        }
    }

    fun getString(@StringRes stringId: Int): String {
        return BaseApplication.getContext().getString(stringId)
    }
}