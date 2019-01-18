package com.m4coding.coolhub.business.search.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.search.model.bean.SearchHistoryBean
import com.m4coding.coolhub.business_search.R

/**
 * 搜索历史提示适配器
 */
class SearchHitAdapter(list: List<SearchHistoryBean>) : BaseListAdapter<SearchHistoryBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_search_item_hint
    }

    override fun convert(helper: BaseViewHolder?, item: SearchHistoryBean?) {
        helper?.setText(R.id.business_search_hint_tv_content, item?.content)
    }

}