package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.RepoBean
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class RepositoryListAdapter(list: List<RepoBean>?) : BaseListAdapter<RepoBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_repository
    }

    override fun convert(helper: BaseViewHolder?, item: RepoBean?) {

        ImageLoader.begin().setImageOnDefault(R.color.text_gray)
                .displayImage(item?.owner?.avatarUrl, helper?.getView(R.id.business_mainpage_repository_iv_avatar))

        helper?.setText(R.id.business_mainpage_repository_tv_repo_name, item?.fullName)

        if (TextUtils.isEmpty(item?.description)) {
            helper?.setVisible(R.id.business_mainpage_repository_tv_describe, false)
        } else {
            helper?.setVisible(R.id.business_mainpage_repository_tv_describe, true)
            helper?.setText(R.id.business_mainpage_repository_tv_describe, item?.description)
        }

        helper?.setText(R.id.business_mainpage_repository_tv_star, item?.stargazersCount.toString())

        if (TextUtils.isEmpty(item?.language)) {
            helper?.setVisible(R.id.business_mainpage_repository_tv_language, false)
        } else {
            helper?.setVisible(R.id.business_mainpage_repository_tv_language, true)
            helper?.setText(R.id.business_mainpage_repository_tv_language, item?.language)
        }

        helper?.setText(R.id.business_mainpage_repository_tv_fork, item?.forksCount.toString())
    }

}