package com.m4coding.coolhub.business.mainpage.modules.hot.ui.dialog

import android.view.LayoutInflater
import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.base.widgets.BaseDownSelectDialog
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.hot.model.bean.HotLanguageBean
import com.m4coding.coolhub.business.mainpage.modules.hot.ui.adapter.HotLanguageAdapter

/**
 * @author mochangsheng
 * @description
 */
class LanguageFragment : BaseDownSelectDialog() {
    override fun initAdapter(): BaseListAdapter<*, *> {
        return HotLanguageAdapter(HotLanguageBean.createList())
    }

    init {
        contentView = LayoutInflater.from(BaseApplication.getContext()).
                inflate(R.layout.business_mainpage_fragment_hot_time, null)
        mRecyclerView = contentView.findViewById(R.id.business_mainpage_hot_time_rv)
        initRv()
    }
}