package com.m4coding.coolhub.business.search.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.base.utils.BusinessFormatUtils
import com.m4coding.coolhub.business_search.R

class SearchContentRepoAdapter(list: List<RepoBean>?) : BaseListAdapter<RepoBean, BaseViewHolder>(list) {


    init {
        mLayoutResId = R.layout.business_search_item_repo
    }

    override fun convert(helper: BaseViewHolder, item: RepoBean) {

        helper.setText(R.id.business_search_repo_tv_author, item.owner?.login)

        if (TextUtils.isEmpty(item.language)) {
            helper.setVisible(R.id.business_search_repo_tv_language, false)
        } else {
            helper.setVisible(R.id.business_search_repo_tv_language, true)
            helper.setText(R.id.business_search_repo_tv_language, item.language)
        }

        helper.setText(R.id.business_search_repo_tv_star,
                BusinessFormatUtils.formatNumber(item.stargazersCount))

        helper.setText(R.id.business_search_repo_tv_fork,
                BusinessFormatUtils.formatNumber(item.forksCount))


        if (TextUtils.isEmpty(item.description)) {
            helper.setVisible(R.id.business_search_repo_tv_describe, false)
        } else {
            helper.setVisible(R.id.business_search_repo_tv_describe, true)
            helper.setText(R.id.business_search_repo_tv_describe, item.description)
        }

        helper.setText(R.id.business_search_repo_tv_repo_name, item.fullName)

        helper.addOnClickListener(R.id.business_search_repo_iv_avatar)

        ImageLoader.begin().setImageOnDefault(R.color.secondary_text).displayImage(item.owner?.avatarUrl,
                helper.getView(R.id.business_search_repo_iv_avatar))
    }

}