package com.m4coding.coolhub.business.search.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.UserBean
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business_search.R

class SearchContentUserAdapter(list: List<UserBean>?) : BaseListAdapter<UserBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_search_item_user
    }


    override fun convert(helper: BaseViewHolder?, item: UserBean?) {
        ImageLoader.begin().setImageOnDefault(R.color.text_gray)
                .displayImage(item?.avatarUrl, helper?.getView(R.id.business_search_user_iv_avatar))

        helper?.setText(R.id.business_search_user_tv_user_name, item?.login)
    }

}