package com.m4coding.coolhub.business.mainpage.modules.hot.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.HotDataBean
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotTimeBean

/**
 * @author mochangsheng
 * @description  热点适配器
 */
class HotAdapter(list: List<HotDataBean>?) : BaseListAdapter<HotDataBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_hot
    }

    override fun convert(helper: BaseViewHolder?, item: HotDataBean?) {
        helper?.setText(R.id.business_mainpage_hot_tv_repo_name, item?.fullName)
        helper?.setText(R.id.business_mainpage_hot_tv_describe, item?.describe)
        if (TextUtils.isEmpty(item?.language)) {
            helper?.setVisible(R.id.business_mainpage_hot_tv_language, false)
        } else {
            helper?.setVisible(R.id.business_mainpage_hot_tv_language, true)
            helper?.setText(R.id.business_mainpage_hot_tv_language, item?.language)
        }
        helper?.setText(R.id.business_mainpage_hot_tv_star, item?.allStarNum.toString())
        if (item?.starNumInTime == 0) {
            helper?.setVisible(R.id.business_mainpage_hot_tv_star_in_time, false)
        } else {
            helper?.setVisible(R.id.business_mainpage_hot_tv_star_in_time, true)
            when(item?.timeType) {
                HotTimeBean.TODAY -> {
                    helper?.setText(R.id.business_mainpage_hot_tv_star_in_time,
                            mContext.getString(R.string.many_stars_in_day, item.starNumInTime))
                }
                HotTimeBean.WEEK -> {
                    helper?.setText(R.id.business_mainpage_hot_tv_star_in_time,
                            mContext.getString(R.string.many_stars_in_this_week, item.starNumInTime))
                }
                HotTimeBean.MONTH -> {
                    helper?.setText(R.id.business_mainpage_hot_tv_star_in_time,
                            mContext.getString(R.string.many_stars_in_this_month, item.starNumInTime))
                }
            }
        }
    }

}