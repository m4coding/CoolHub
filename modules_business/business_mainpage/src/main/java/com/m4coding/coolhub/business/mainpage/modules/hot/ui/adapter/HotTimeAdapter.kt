package com.m4coding.coolhub.business.mainpage.modules.hot.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotTimeBean

/**
 * @author mochangsheng
 * @description
 */
class HotTimeAdapter(list: List<HotTimeBean>) : BaseListAdapter<HotTimeBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_hot_spinner
    }

    override fun convert(helper: BaseViewHolder?, item: HotTimeBean?) {
        helper?.setText(R.id.business_mainpage_hot_spinner_tv_content, item?.name)
    }
}