package com.m4coding.coolhub.business.search.contract

import com.m4coding.coolhub.base.mvp.IModel
import com.m4coding.coolhub.business.base.list.IListView

class SearchContract {
    interface View : IListView
    interface Model : IModel
}