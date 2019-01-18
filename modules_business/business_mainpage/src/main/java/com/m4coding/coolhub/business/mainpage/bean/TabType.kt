package com.m4coding.coolhub.business.mainpage.bean

import com.m4coding.coolhub.business.mainpage.R

/**
 * @author mochangsheng
 * @description
 */
enum class TabType(var nameRes: Int) {
    //动态tab
    TYPE_DYNAMIC(R.string.business_mainpage_dynamic),
    //关注tab
    TYPE_DYNAMIC_FOCUS(R.string.business_mainpage_focus),
    //推荐tab
    TYPE_RECOMMEND(R.string.business_mainpage_recommend),

    //用户详细信息tab
    TYPE_USER_DETAILS_INFO(R.string.business_mainpage_user_details_tab_info),

    //仓库详细信息tab
    TYPE_REPO_DETAILS_INFO(R.string.business_mainpage_repository_details_tab_info),
    //仓库文件列表信息tab
    TYPE_REPO_DETAILS_FILE(R.string.business_mainpage_repository_details_tab_files),


    //仓库问题tab
    TYPE_REPO_DETAILS_ISSUE(R.string.business_mainpage_repository_details_tab_issue),
    //仓库问题tab-打开的
    TYPE_REPO_DETAILS_ISSUE_OPEN(R.string.business_mainpage_repository_details_tab_issue_open),
    //仓库问题tab-关闭的
    TYPE_REPO_DETAILS_ISSUE_CLOSE(R.string.business_mainpage_repository_details_tab_issue_close)
}