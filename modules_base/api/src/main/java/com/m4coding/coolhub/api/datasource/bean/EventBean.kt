package com.m4coding.coolhub.api.datasource.bean

import com.google.gson.annotations.SerializedName
import com.m4coding.coolhub.base.utils.StringUtils
import java.util.*

/**
 * @author mochangsheng
 * @description  事件Bean
 *
 * @wiki https://developer.github.com/v3/activity/events/
 */
class EventBean {

    /**
        {
            "type": "Event",
            "public": true,
            "payload": {
            },
            "repo": {
                "id": 3,
                "name": "octocat/Hello-World",
                "url": "https://api.github.com/repos/octocat/Hello-World"
            },
            "actor": {
                "id": 1,
                "login": "octocat",
                "gravatar_id": "",
                "avatar_url": "https://github.com/images/error/octocat_happy.gif",
                "url": "https://api.github.com/users/octocat"
            },
            "org": {
                "id": 1,
                "login": "github",
                "gravatar_id": "",
                "url": "https://api.github.com/orgs/github",
                "avatar_url": "https://github.com/images/error/octocat_happy.gif"
            },
            "created_at": "2011-09-06T17:26:27Z",
            "id": "12345"
        }
     */


    //枚举类型   事件类型
    enum class EventType {

        CommitCommentEvent,
        CreateEvent,
        /**
         * Represents a deleted branch or tag.
         */
        DeleteEvent,
        ForkEvent,
        /**
         * Triggered when a Wiki page is created or updated.
         */
        GollumEvent,


        /**
         * Triggered when a GitHub App has been installed or uninstalled.
         */
        InstallationEvent,
        /**
         * Triggered when a repository is added or removed from an installation.
         */
        InstallationRepositoriesEvent,
        IssueCommentEvent,
        IssuesEvent,


        /**
         * Triggered when a user purchases, cancels, or changes their GitHub Marketplace plan.
         */
        MarketplacePurchaseEvent,
        /**
         * Triggered when a user is added or removed as a collaborator to a repository, or has their permissions changed.
         */
        MemberEvent,
        /**
         * Triggered when an organization blocks or unblocks a user.
         */
        OrgBlockEvent,
        /**
         * Triggered when a project card is created, updated, moved, converted to an issue, or deleted.
         */
        ProjectCardEvent,
        /**
         * Triggered when a project column is created, updated, moved, or deleted.
         */
        ProjectColumnEvent,


        /**
         * Triggered when a project is created, updated, closed, reopened, or deleted.
         */
        ProjectEvent,
        /**
         * made repository public
         */
        PublicEvent,
        PullRequestEvent,
        /**
         * Triggered when a pull request review is submitted into a non-pending state, the body is edited, or the review is dismissed.
         */
        PullRequestReviewEvent,
        PullRequestReviewCommentEvent,


        PushEvent,
        ReleaseEvent,
        WatchEvent,

        //Events of this type are not visible in timelines. These events are only used to trigger hooks.
        DeploymentEvent,
        DeploymentStatusEvent,
        MembershipEvent,
        MilestoneEvent,
        OrganizationEvent,
        PageBuildEvent,
        RepositoryEvent,
        StatusEvent,
        TeamEvent,
        TeamAddEvent,
        LabelEvent,

        //Events of this type are no longer delivered, but it's possible that they exist in timelines
        // of some users. You cannot createForRepo webhooks that listen to these events.
        DownloadEvent,
        FollowEvent,
        ForkApplyEvent,
        GistEvent
    }

    //事件类型 （gson会自动转换String和枚举对应值 如 GistEvent <-> "GistEvent"）
    var type: EventType? = null
    var public: Boolean = false
    var payload: Payload? = null
    var repo: Repo? = null
    var actor: Actor? = null
    var org: Org? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    var id: String? = null


    companion object {
        class Repo {
            var id: Int? = 0
            var name: String? = null
            var url: String? = null

            override fun toString(): String {
                return "Repo(id=$id, name=$name, url=$url)"
            }
        }

        class Actor {
            var id: Int? = 0
            var login: String? = null

            @SerializedName("gravatar_id")
            var gravatarId: String? = null

            @SerializedName("avatar_url")
            var avatarUrl: String? = null

            var url: String? = null

            override fun toString(): String {
                return "Actor(id=$id, login=$login, gravatarId=$gravatarId, avatarUrl=$avatarUrl, url=$url)"
            }
        }

        class Org {
            var id: Int? = 0
            var login: String? = null

            @SerializedName("gravatar_id")
            var gravatarId: String? = null

            @SerializedName("avatar_url")
            var avatarUrl: String? = null

            var url: String? = null

            override fun toString(): String {
                return "Org(id=$id, login=$login, gravatarId=$gravatarId, avatarUrl=$avatarUrl, url=$url)"
            }
        }

        //事件类型不一致，对应的字段也会不一致
        class Payload {
            enum class RefType {
                repository, branch, tag
            }

            enum class IssueEventActionType {
                assigned, unassigned, labeled, unlabeled, opened,
                edited, milestoned, demilestoned, closed, reopened
            }

            enum class MemberEventActionType {
                added, deleted, edited
            }

            enum class OrgBlockEventActionType {
                blocked, unblocked
            }

            enum class PullRequestReviewCommentEventActionType {
                created, edited, deleted
            }

            enum class PullRequestReviewEventActionType {
                submitted, edited, dismissed
            }

            //PushEvent
            @SerializedName("push_id")
            var pushId: String? = null
            var size: Int = 0
            @SerializedName("distinct_size")
            var distinctSize: Int = 0
            var ref: String? = null //PushEvent&CreateEvent
            var head: String? = null
            var before: String? = null
            var commits: ArrayList<PushEventCommitBean>? = null

            //WatchEvent&PullRequestEvent
            var action: String? = null

            //CreateEvent
            @SerializedName("ref_type")
            var refType: RefType? = null
            @SerializedName("master_branch")
            var masterBranch: String? = null
            var description: String? = null
            @SerializedName("pusher_type")
            var pusherType: String? = null

            //ReleaseEvent
            var release: ReleaseBean? = null
            //IssueCommentEvent
            var issue: IssueBean? = null
            var comment: IssueEventBean? = null

            //MemberEvent
            var member: UserBean? = null

            var organization: UserBean? = null
            @SerializedName("blocked_user")
            var blockedUser: UserBean? = null

            //ForkEvent
            var forkee: ForkBean? = null

            // PublicEvent None



            fun getBranch(): String? {
                return ref?.let {
                    if (StringUtils.isBlank(it)) ""
                    else it.substring(it.lastIndexOf("/") + 1)
                }
            }
        }

    }

    override fun toString(): String {
        return "EventBean(type=$type, public=$public, " +
                "payload=$payload, repo=$repo, actor=$actor, org=$org, createdAt=$createdAt, id=$id)"
    }
}