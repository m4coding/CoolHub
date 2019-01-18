package com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter

import android.text.TextUtils
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.google.android.flexbox.FlexboxLayout
import com.m4coding.coolhub.api.datasource.bean.RecommendBean
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R

/**
 * @author mochangsheng
 * @description
 */
class RecommendAdapter(list: List<RecommendBean>?) : BaseListAdapter<RecommendBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_recommend
    }

    override fun convert(helper: BaseViewHolder, item: RecommendBean) {
        if (!TextUtils.isEmpty(item.language)) {
            helper.setText(R.id.business_mainpage_recommend_tv_language, item.language)
            helper.setVisible(R.id.business_mainpage_recommend_tv_language, true)
        } else {
            helper.setVisible(R.id.business_mainpage_recommend_tv_language, false)
        }

        helper.setText(R.id.business_mainpage_recommend_tv_star, item.starNum.toString())

        helper.setText(R.id.business_mainpage_recommend_tv_describe, item.describe)

        helper.setText(R.id.business_mainpage_recommend_tv_repo_name, item.repositoryName)

        helper.addOnClickListener(R.id.business_mainpage_recommend_iv_avatar)

        ImageLoader.begin().setImageOnDefault(R.color.secondary_text).displayImage(item.avatar,
                helper.getView(R.id.business_mainpage_recommend_iv_avatar))

        val flexboxLayout: FlexboxLayout = helper.getView(R.id.business_mainpage_recommend_fb_layout)
        if (flexboxLayout.childCount > 0) {
            flexboxLayout.removeAllViews()
        }
        val list = item.tagsList
        if (list != null) {
            for (tag in list) {
                if (mLayoutInflater != null) {
                    val textView: TextView = mLayoutInflater.inflate(R.layout.business_mainpage_layout_tag, flexboxLayout, false) as TextView
                    textView.text = tag
                    flexboxLayout.addView(textView)
                }
            }
        }

    }
}