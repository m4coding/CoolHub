package com.m4coding.coolhub.business.search.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.search.model.bean.SearchSelectRepoTypeBean
import com.m4coding.coolhub.business.search.model.bean.SearchSelectUserTypeBean
import com.m4coding.coolhub.business_search.R

class SearchRepoTypeAdapter(list: List<SearchSelectRepoTypeBean>) : BaseListAdapter<SearchSelectRepoTypeBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_search_item_select_type
    }

    override fun convert(helper: BaseViewHolder?, item: SearchSelectRepoTypeBean?) {
        helper?.setText(R.id.business_search_select_type_tv_content, item?.showName)
    }
}