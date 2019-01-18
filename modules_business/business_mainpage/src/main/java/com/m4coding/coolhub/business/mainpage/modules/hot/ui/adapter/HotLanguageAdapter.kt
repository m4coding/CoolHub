package com.m4coding.coolhub.business.mainpage.modules.hot.ui.adapter

import com.m4coding.coolhub.business.mainpage.R
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotLanguageBean


/**
 * @author mochangsheng
 * @description
 */
class HotLanguageAdapter(list: List<HotLanguageBean>) : BaseListAdapter<HotLanguageBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_hot_spinner
    }

    override fun convert(helper: BaseViewHolder?, item: HotLanguageBean?) {
        helper?.setText(R.id.business_mainpage_hot_spinner_tv_content, item?.name)
    }
}