package com.m4coding.coolhub.business.mainpage.modules.hot.model.bean

import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.business.mainpage.R


/**
 * @author mochangsheng
 * @description
 */
class HotTimeBean(name: String, time: String) {


    companion object {
        /**
         * 时间参数
         */
        const val TODAY = "daily"
        const val WEEK = "weekly"
        const val MONTH = "monthly"

        fun createList(): List<HotTimeBean> {
            val list = ArrayList<HotTimeBean>()
            list.add(HotTimeBean(BaseApplication.getContext()
                    .getString(R.string.business_mainpage_hot_day), TODAY))

            list.add(HotTimeBean(BaseApplication.getContext()
                    .getString(R.string.business_mainpage_hot_week), WEEK))

            list.add(HotTimeBean(BaseApplication.getContext()
                    .getString(R.string.business_mainpage_hot_month), MONTH))

            return list
        }

        fun getNameByTime(time: String): String? {
            when(time) {
                TODAY -> {
                    return BaseApplication.getContext()
                            .getString(R.string.business_mainpage_hot_day)
                }
                WEEK -> {
                    return BaseApplication.getContext()
                            .getString(R.string.business_mainpage_hot_week)
                }
                MONTH -> {
                    return BaseApplication.getContext()
                            .getString(R.string.business_mainpage_hot_month)
                }
                else -> {
                    return null
                }
            }
        }
    }

    var time: String? = time
    var name: String? = name
}