package com.m4coding.coolhub.business.search.ui.fragment

import android.view.LayoutInflater
import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.base.widgets.BaseDownSelectDialog
import com.m4coding.coolhub.business.search.model.bean.SearchSelectLanguageBean
import com.m4coding.coolhub.business.search.ui.adapter.SearchRepoLanguageAdapter
import com.m4coding.coolhub.business_search.R

class SearchRepoLanguageFragment : BaseDownSelectDialog() {


    override fun initAdapter(): BaseListAdapter<*, *> {
        return SearchRepoLanguageAdapter(SearchSelectLanguageBean.createList())
    }

    init {
        contentView = LayoutInflater.from(BaseApplication.getContext()).
                inflate(R.layout.business_search_fragment_select_type, null)
        mRecyclerView = contentView.findViewById(R.id.business_search_select_type_rv)
        initRv()
    }

}