package com.m4coding.coolhub.business.search.presenter

import com.m4coding.coolhub.base.mvp.BasePresenter
import com.m4coding.coolhub.business.search.contract.SearchContract

class SearchPresenter(rootView: SearchContract.View) :
        BasePresenter<SearchContract.Model, SearchContract.View>(rootView) {

}