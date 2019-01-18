package com.m4coding.coolhub.business.base.utils

import com.m4coding.business_base.R
import com.m4coding.coolhub.base.base.BaseApplication

/**
 * @author mochangsheng
 * @description 业务格式相关
 */
object BusinessFormatUtils {

    /**
     * 数字大小显示限定
     */
    fun formatNumber(number: Int): String {
        if (number > 10_0000) {
            return BaseApplication.getContext()
                    .getString(R.string.ten_thousand, (number % 10000).toString())
        }

        return number.toString()
    }
}