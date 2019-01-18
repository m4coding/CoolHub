package com.m4coding.coolhub.business.search.model.bean

import com.m4coding.coolhub.business_search.R


/**
 * @author mochangsheng
 * @description
 */
enum class TabType(var nameRes: Int) {

    //搜索仓库tab
    TYPE_SEARCH_REPO(R.string.business_search_repository),
    //搜索用户tab
    TYPE_SEARCH_USER(R.string.business_search_user)
}