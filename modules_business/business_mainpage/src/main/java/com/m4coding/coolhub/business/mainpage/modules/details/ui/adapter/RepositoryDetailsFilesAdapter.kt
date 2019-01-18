package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.FileBean
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R

class RepositoryDetailsFilesAdapter(list: List<FileBean>?) : BaseListAdapter<FileBean, BaseViewHolder>(list) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_repo_files
    }

    override fun convert(helper: BaseViewHolder?, item: FileBean?) {
        when(item?.type) {
            FileBean.TYPE_FILE -> {
                helper?.setImageResource(R.id.business_mainpage_repo_files_iv_icon, R.drawable.business_mainpage_vc_ic_file)
                helper?.setText(R.id.business_mainpage_repo_files_tv_name, item?.name)
            }
            FileBean.TYPE_DIR -> {
                helper?.setImageResource(R.id.business_mainpage_repo_files_iv_icon, R.drawable.business_mainpage_vc_ic_folder)
                helper?.setText(R.id.business_mainpage_repo_files_tv_name, item?.name)
            }
        }
    }

}