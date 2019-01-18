package com.m4coding.coolhub.business.mainpage.modules.details.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.business.mainpage.modules.details.model.bean.DataListBean
import com.chad.library.adapter.base.util.MultiTypeDelegate
import com.m4coding.coolhub.api.datasource.bean.*
import com.m4coding.coolhub.base.utils.StringUtils
import com.m4coding.coolhub.business.base.utils.BusinessTimeUtils


/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class DataListAdapter(list: List<DataListBean>?) : BaseListAdapter<DataListBean, BaseViewHolder>(list) {

    init {

        multiTypeDelegate = object : MultiTypeDelegate<DataListBean>() {
            override fun getItemType(entity: DataListBean): Int {
                //根据你的实体类来判断布局类型
                return entity.type
            }
        }


        multiTypeDelegate.registerItemType(DataListBean.TYPE_FOLLOWERS, R.layout.business_mainpage_item_follow)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_FOLLOWING, R.layout.business_mainpage_item_follow)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_FORKERS, R.layout.business_mainpage_item_follow)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_STARTERS, R.layout.business_mainpage_item_follow)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_WATCHERS, R.layout.business_mainpage_item_follow)

        multiTypeDelegate.registerItemType(DataListBean.TYPE_ISSUE_CLOSE, R.layout.business_mainpage_item_issue)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_ISSUE_OPEN, R.layout.business_mainpage_item_issue)
        multiTypeDelegate.registerItemType(DataListBean.TYPE_ISSUE_TIME_LINE, R.layout.business_mainpage_item_issue_details)

    }

    override fun convert(helper: BaseViewHolder?, item: DataListBean?) {
        var url: String? = null
        var name: String? = null

        when(item?.type) {
            DataListBean.TYPE_FOLLOWERS -> {
                if (item.value is FollowBean) {
                    url = (item.value as FollowBean).avatarUrl
                    name = (item.value as FollowBean).login
                }
            }

            DataListBean.TYPE_FOLLOWING -> {
                if (item.value is FollowBean) {
                    url = (item.value as FollowBean).avatarUrl
                    name = (item.value as FollowBean).login
                }
            }

            DataListBean.TYPE_FORKERS -> {
                if (item.value is RepoBean) {
                    url = (item.value as RepoBean).owner?.avatarUrl
                    name = (item.value as RepoBean).owner?.login
                }
            }

            DataListBean.TYPE_STARTERS -> {
                if (item.value is UserBean) {
                    url = (item.value as UserBean).avatarUrl
                    name = (item.value as UserBean).login
                }
            }

            DataListBean.TYPE_WATCHERS -> {
                if (item.value is UserBean) {
                    url = (item.value as UserBean).avatarUrl
                    name = (item.value as UserBean).login
                }
            }

            DataListBean.TYPE_ISSUE_OPEN -> {
                if (item.value is IssueBean) {
                    initIssue(helper, item.value as IssueBean, DataListBean.TYPE_ISSUE_OPEN)
                }
                return
            }

            DataListBean.TYPE_ISSUE_CLOSE -> {
                if (item.value is IssueBean) {
                    initIssue(helper, item.value as IssueBean, DataListBean.TYPE_ISSUE_CLOSE)
                }
                return
            }

            DataListBean.TYPE_ISSUE_TIME_LINE -> {
                val temp = item.value
                if (temp is IssueEventBean || temp is IssueBean) {
                    initIssueDetails(helper, temp, DataListBean.TYPE_ISSUE_TIME_LINE)
                }
                return
            }
        }

        ImageLoader.begin().setImageOnDefault(R.color.text_gray)
                .displayImage(url, helper?.getView(R.id.business_mainpage_follow_iv_avatar))

        helper?.setText(R.id.business_mainpage_follow_tv_user_name, name)
    }

    /**
     * 初始化问题相关的Item
     */
    private fun initIssue(helper: BaseViewHolder?, issueBean: IssueBean, type: Int) {
        ImageLoader.begin().setImageOnDefault(R.color.text_gray).displayImage(issueBean.user?.avatarUrl,
                helper?.getView(R.id.business_mainpage_issue_iv_avatar))

        helper?.setText(R.id.business_mainpage_issue_tv_name, issueBean.user?.login)

        helper?.setText(R.id.business_mainpage_issue_tv_content, issueBean.title)
        helper?.setText(R.id.business_mainpage_issue_tv_time, BusinessTimeUtils.getTimeStr(mContext, issueBean.createdAt))
        helper?.setText(R.id.business_mainpage_issue_tv_comment_number, issueBean.commentNum.toString())
        helper?.setText(R.id.business_mainpage_issue_tv_number, "#" + issueBean.number)
    }


    /**
     * 初始化issue详情Item
     */
    private fun initIssueDetails(helper: BaseViewHolder?, any: Any, type: Int) {

        if (any is IssueBean) {
            ImageLoader.begin().setImageOnDefault(R.color.text_gray).displayImage(any.user?.avatarUrl,
                    helper?.getView(R.id.business_mainpage_issue_iv_avatar))

            helper?.setText(R.id.business_mainpage_issue_tv_name, any.user?.login)


            helper?.setText(R.id.business_mainpage_issue_tv_ask, R.string.business_mainpage_issue_details_ask)

            helper?.setVisible(R.id.business_mainpage_issue_tv_title, true)
            helper?.setText(R.id.business_mainpage_issue_tv_title, any.title)


            if (!StringUtils.isBlank(any.bodyHtml)) {
                helper?.setText(R.id.business_mainpage_issue_tv_content, Html.fromHtml(any.bodyHtml))
            } else if (!StringUtils.isBlank(any.body)) {
                helper?.setText(R.id.business_mainpage_issue_tv_content, any.body)
            } else {
                helper?.setText(R.id.business_mainpage_issue_tv_content, R.string.business_mainpage_no_description)
            }

            helper?.setText(R.id.business_mainpage_issue_tv_time, BusinessTimeUtils.getTimeStr(mContext, any.createdAt))

        } else if (any is IssueEventBean) {

            ImageLoader.begin().setImageOnDefault(R.color.text_gray).displayImage(any.actor?.avatarUrl,
                    helper?.getView(R.id.business_mainpage_issue_iv_avatar))

            helper?.setText(R.id.business_mainpage_issue_tv_name, any.actor?.login)
            helper?.setText(R.id.business_mainpage_issue_tv_ask, R.string.business_mainpage_issue_details_reply)

            helper?.setVisible(R.id.business_mainpage_issue_tv_title, false)

            if (!StringUtils.isBlank(any.bodyHtml)) {
                helper?.setText(R.id.business_mainpage_issue_tv_content, Html.fromHtml(any.bodyHtml))
            } else if (!StringUtils.isBlank(any.body)) {
                helper?.setText(R.id.business_mainpage_issue_tv_content, any.body)
            } else {
                helper?.setText(R.id.business_mainpage_issue_tv_content, R.string.business_mainpage_no_description)
            }

            helper?.setText(R.id.business_mainpage_issue_tv_time, BusinessTimeUtils.getTimeStr(mContext, any.createdAt))
        }
    }

}