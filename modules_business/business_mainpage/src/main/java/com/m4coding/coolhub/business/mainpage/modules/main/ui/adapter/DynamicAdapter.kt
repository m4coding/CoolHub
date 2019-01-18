package com.m4coding.coolhub.business.mainpage.modules.main.ui.adapter

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.chad.library.adapter.base.BaseViewHolder
import com.m4coding.coolhub.api.datasource.bean.EventBean
import com.m4coding.coolhub.base.utils.imageloader.ImageLoader
import com.m4coding.coolhub.business.base.list.BaseListAdapter
import com.m4coding.coolhub.business.mainpage.R
import com.m4coding.coolhub.api.datasource.bean.EventBean.Companion.Payload.IssueEventActionType
import com.m4coding.coolhub.api.datasource.bean.EventBean.Companion.Payload.MemberEventActionType
import com.m4coding.coolhub.api.datasource.bean.EventBean.Companion.Payload.OrgBlockEventActionType
import com.m4coding.coolhub.api.datasource.bean.EventBean.Companion.Payload.PullRequestReviewEventActionType
import com.m4coding.coolhub.api.datasource.bean.EventBean.Companion.Payload.PullRequestReviewCommentEventActionType
import com.m4coding.coolhub.base.utils.StringUtils
import com.m4coding.coolhub.business.base.utils.BusinessTimeUtils

/**
 * @author mochangsheng
 * @description
 */
class DynamicAdapter(data: List<EventBean>?) : BaseListAdapter<EventBean, BaseViewHolder>(data) {

    init {
        mLayoutResId = R.layout.business_mainpage_item_dynamic
    }


    override fun convert(helper: BaseViewHolder?, item: EventBean?) {

        helper?.addOnClickListener(R.id.business_mainpage_dynamic_iv_avatar)

        ImageLoader.begin().displayImage(item?.actor?.avatarUrl, helper?.getView(R.id.business_mainpage_dynamic_iv_avatar))
        helper?.setText(R.id.business_mainpage_dynamic_tv_name, item?.actor?.login)

        helper?.setVisible(R.id.business_mainpage_dynamic_tv_desc, false)

        var actionStr = StringUtils.upCaseFirstChar(getActionStr(helper, item))
        actionStr = actionStr ?: ""
        val span = SpannableStringBuilder(actionStr)
        span.setSpan(StyleSpan(Typeface.BOLD), 0, actionStr.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        helper?.setText(R.id.business_mainpage_dynamic_tv_operate, span)
        helper?.setText(R.id.business_mainpage_dynamic_tv_time, BusinessTimeUtils.getTimeStr(mContext, item?.createdAt))
    }

    private fun getActionStr(helper: BaseViewHolder?, item: EventBean?): String? {

        var actionStr: String? = null
        val action: String? = item?.payload?.action

        when(item?.type) {
            EventBean.EventType.CommitCommentEvent -> {
                actionStr = mContext.getString(R.string.created_comment_on_commit, item.repo?.name)
            }
            EventBean.EventType.CreateEvent -> {
                when(item.payload?.refType) {
                    EventBean.Companion.Payload.RefType.repository -> {
                        actionStr = mContext.getString(R.string.created_repo, item.repo?.name)
                    }
                    EventBean.Companion.Payload.RefType.branch -> {
                        actionStr = mContext.getString(R.string.created_branch_at, item.payload?.ref, item.repo?.name)

                    }
                    EventBean.Companion.Payload.RefType.tag-> {
                        actionStr = mContext.getString(R.string.created_tag_at, item.payload?.ref, item.repo?.name)
                    }
                }
            }
            EventBean.EventType.DeleteEvent -> {
                when(item.payload?.refType) {
                    EventBean.Companion.Payload.RefType.branch -> {
                        actionStr = mContext.getString(R.string.delete_branch_at, item.payload?.ref, item.repo?.name)
                    }
                    EventBean.Companion.Payload.RefType.tag-> {
                        actionStr = mContext.getString(R.string.delete_tag_at, item.payload?.ref, item.repo?.name)
                    }
                }
            }
            EventBean.EventType.ForkEvent -> {
                val newRepo = item.payload?.forkee?.fullName
                val oldRepo = item.repo?.name
                actionStr = mContext.getString(R.string.forked_from, newRepo, oldRepo)
            }
            EventBean.EventType.GollumEvent -> {
                actionStr = "$action a wiki page "
            }
            EventBean.EventType.InstallationEvent -> {
                actionStr = "$action an GitHub App "
            }
            EventBean.EventType.IssueCommentEvent -> {
                actionStr = mContext.getString(R.string.created_comment_on_issue,
                        item.payload?.issue?.number, item.repo?.name)
            }
            EventBean.EventType.IssuesEvent -> {
                if (action != null) {
                    val issueEventStr = getIssueEventStr(action)
                    actionStr = String.format(issueEventStr,
                            item.payload?.issue?.number, item.repo?.name)
                }
            }
            EventBean.EventType.MarketplacePurchaseEvent -> {
                actionStr = "$action marketplace plan "
            }
            EventBean.EventType.MemberEvent -> {
                if (action != null) {
                    val memberEventStr = getMemberEventStr(action)
                    actionStr = String.format(memberEventStr,
                            item.payload?.member?.login, item.repo?.name)
                }

            }
            EventBean.EventType.OrgBlockEvent -> {
                val orgBlockEventStr: String = if (OrgBlockEventActionType.blocked.name == action) {
                    mContext.getString(R.string.org_blocked_user)
                } else {
                    mContext.getString(R.string.org_unblocked_user)
                }
                actionStr = String.format(orgBlockEventStr,
                        item.payload?.organization?.login,
                        item.payload?.blockedUser?.login)
            }
            EventBean.EventType.ProjectCardEvent -> {
                actionStr = "$action a project "
            }
            EventBean.EventType.ProjectColumnEvent -> {
                actionStr = "$action a project "
            }
            EventBean.EventType.ProjectEvent -> {
                actionStr = "$action a project "
            }
            EventBean.EventType.PublicEvent -> {
                actionStr = mContext.getString(R.string.made_repo_public, item.repo?.name)
            }
            EventBean.EventType.PullRequestEvent -> {
                actionStr = action + " pull request " + item.repo?.name
            }
            EventBean.EventType.PullRequestReviewEvent -> {
                if (action != null) {
                    val pullRequestReviewStr = getPullRequestReviewEventStr(action)
                    actionStr = String.format(pullRequestReviewStr, item.repo?.name)
                }
            }
            EventBean.EventType.PullRequestReviewCommentEvent -> {
                if (action != null) {
                    val pullRequestCommentStr = getPullRequestReviewCommentEventStr(action)
                    actionStr = String.format(pullRequestCommentStr, item.repo?.name)
                }
            }
            EventBean.EventType.PushEvent ->{
                val branch = item.payload?.getBranch()
                actionStr = String.format(mContext.getString(R.string.push_to), branch, item.repo?.name)

                var descSpan = SpannableStringBuilder("")
                val count = item.payload?.commits?.size ?: 0
                val maxLines = 4
                val max = if (count > maxLines) maxLines - 1 else count
                for (i in 0 until max) {
                    val commit = item.payload?.commits?.get(i)
                    if (i != 0) {
                        descSpan.append("\n")
                    }

                    val lastLength = descSpan.length
                    val sha = commit?.sha?.substring(0, 7)
                    descSpan.append(sha)
                    descSpan.setSpan(ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.accent)),
                            lastLength, lastLength + (sha?.length?:0), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    descSpan.append(" ")
                    descSpan.append(commit?.message)

                  /*  descSpan.setSpan(EllipsizeLineSpan(if (i == count - 1) 0 else 0),
                            lastLength, descSpan.length, 0)*/
                }
                if (count > maxLines) {
                    descSpan.append("\n").append("...")
                }
                helper?.setVisible(R.id.business_mainpage_dynamic_tv_desc, true)
                helper?.setText(R.id.business_mainpage_dynamic_tv_desc, descSpan)
            }
            EventBean.EventType.ReleaseEvent -> {
                actionStr = mContext.getString(R.string.published_release_at, item.payload?.release?.tagName, item.repo?.name)
            }
            EventBean.EventType.WatchEvent -> {
                actionStr = mContext.getString(R.string.starred_repo, item.repo?.name)
            }
        }
        return actionStr
    }

    private fun getIssueEventStr(action: String): String {
        val actionType = IssueEventActionType.valueOf(action)
        return when (actionType) {
            IssueEventActionType.assigned -> mContext.getString(R.string.assigned_issue_at)
            IssueEventActionType.unassigned -> mContext.getString(R.string.unassigned_issue_at)
            IssueEventActionType.labeled -> mContext.getString(R.string.labeled_issue_at)
            IssueEventActionType.unlabeled -> mContext.getString(R.string.unlabeled_issue_at)
            IssueEventActionType.opened -> mContext.getString(R.string.opened_issue_at)

            IssueEventActionType.edited -> mContext.getString(R.string.edited_issue_at)
            IssueEventActionType.milestoned -> mContext.getString(R.string.milestoned_issue_at)
            IssueEventActionType.demilestoned -> mContext.getString(R.string.demilestoned_issue_at)
            IssueEventActionType.closed -> mContext.getString(R.string.closed_issue_at)
            IssueEventActionType.reopened -> mContext.getString(R.string.reopened_issue_at)

            else -> mContext.getString(R.string.opened_issue_at)
        }
    }

    private fun getMemberEventStr(action: String): String {
        val actionType = EventBean.Companion.Payload.MemberEventActionType.valueOf(action)
        return when (actionType) {
            MemberEventActionType.added -> mContext.getString(R.string.added_member_to)
            MemberEventActionType.deleted -> mContext.getString(R.string.deleted_member_at)
            MemberEventActionType.edited -> mContext.getString(R.string.edited_member_at)
            else -> mContext.getString(R.string.added_member_to)
        }
    }

    private fun getPullRequestReviewEventStr(action: String): String {
        val actionType = PullRequestReviewEventActionType.valueOf(action)
        return when (actionType) {
            PullRequestReviewEventActionType.submitted -> mContext.getString(R.string.submitted_pull_request_review_at)
            PullRequestReviewEventActionType.edited -> mContext.getString(R.string.edited_pull_request_review_at)
            PullRequestReviewEventActionType.dismissed -> mContext.getString(R.string.dismissed_pull_request_review_at)
            else -> mContext.getString(R.string.submitted_pull_request_review_at)
        }
    }

    private fun getPullRequestReviewCommentEventStr(action: String): String {
        val actionType = PullRequestReviewCommentEventActionType.valueOf(action)
        return when (actionType) {
            PullRequestReviewCommentEventActionType.created -> mContext.getString(R.string.created_pull_request_comment_at)
            PullRequestReviewCommentEventActionType.edited -> mContext.getString(R.string.edited_pull_request_comment_at)
            PullRequestReviewCommentEventActionType.deleted -> mContext.getString(R.string.deleted_pull_request_comment_at)
            else -> mContext.getString(R.string.created_pull_request_comment_at)
        }
    }

}