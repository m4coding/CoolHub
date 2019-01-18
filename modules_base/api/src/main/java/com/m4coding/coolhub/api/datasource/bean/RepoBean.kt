package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author mochangsheng
 * @description 仓库bean
 */
class RepoBean : Parcelable{

    /**
        {
            "id": 1296269,
            "node_id": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
            "name": "Hello-World",
            "full_name": "octocat/Hello-World",
            "owner": {
            "login": "octocat",
            "id": 1,
            "node_id": "MDQ6VXNlcjE=",
            "avatar_url": "https://github.com/images/error/octocat_happy.gif",
            "gravatar_id": "",
            "url": "https://api.github.com/users/octocat",
            "html_url": "https://github.com/octocat",
            "followers_url": "https://api.github.com/users/octocat/followers",
            "following_url": "https://api.github.com/users/octocat/following{/other_user}",
            "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
            "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
            "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
            "organizations_url": "https://api.github.com/users/octocat/orgs",
            "repos_url": "https://api.github.com/users/octocat/repos",
            "events_url": "https://api.github.com/users/octocat/events{/privacy}",
            "received_events_url": "https://api.github.com/users/octocat/received_events",
            "type": "User",
            "site_admin": false
            },
            "private": false,
            "html_url": "https://github.com/octocat/Hello-World",
            "description": "This your first repo!",
            "fork": false,
            "url": "https://api.github.com/repos/octocat/Hello-World",
            "archive_url": "http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
            "assignees_url": "http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
            "blobs_url": "http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
            "branches_url": "http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
            "collaborators_url": "http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
            "comments_url": "http://api.github.com/repos/octocat/Hello-World/comments{/number}",
            "commits_url": "http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
            "compare_url": "http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
            "contents_url": "http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
            "contributors_url": "http://api.github.com/repos/octocat/Hello-World/contributors",
            "deployments_url": "http://api.github.com/repos/octocat/Hello-World/deployments",
            "downloads_url": "http://api.github.com/repos/octocat/Hello-World/downloads",
            "events_url": "http://api.github.com/repos/octocat/Hello-World/events",
            "forks_url": "http://api.github.com/repos/octocat/Hello-World/forks",
            "git_commits_url": "http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
            "git_refs_url": "http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
            "git_tags_url": "http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
            "git_url": "git:github.com/octocat/Hello-World.git",
            "issue_comment_url": "http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
            "issue_events_url": "http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
            "issues_url": "http://api.github.com/repos/octocat/Hello-World/issues{/number}",
            "keys_url": "http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
            "labels_url": "http://api.github.com/repos/octocat/Hello-World/labels{/name}",
            "languages_url": "http://api.github.com/repos/octocat/Hello-World/languages",
            "merges_url": "http://api.github.com/repos/octocat/Hello-World/merges",
            "milestones_url": "http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
            "notifications_url": "http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}",
            "pulls_url": "http://api.github.com/repos/octocat/Hello-World/pulls{/number}",
            "releases_url": "http://api.github.com/repos/octocat/Hello-World/releases{/id}",
            "ssh_url": "git@github.com:octocat/Hello-World.git",
            "stargazers_url": "http://api.github.com/repos/octocat/Hello-World/stargazers",
            "statuses_url": "http://api.github.com/repos/octocat/Hello-World/statuses/{sha}",
            "subscribers_url": "http://api.github.com/repos/octocat/Hello-World/subscribers",
            "subscription_url": "http://api.github.com/repos/octocat/Hello-World/subscription",
            "tags_url": "http://api.github.com/repos/octocat/Hello-World/tags",
            "teams_url": "http://api.github.com/repos/octocat/Hello-World/teams",
            "trees_url": "http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}",
            "clone_url": "https://github.com/octocat/Hello-World.git",
            "mirror_url": "git:git.example.com/octocat/Hello-World",
            "hooks_url": "http://api.github.com/repos/octocat/Hello-World/hooks",
            "svn_url": "https://svn.github.com/octocat/Hello-World",
            "homepage": "https://github.com",
            "language": null,
            "forks_count": 9,
            "stargazers_count": 80,
            "watchers_count": 80,
            "size": 108,
            "default_branch": "master",
            "open_issues_count": 0,
            "topics": [
            "octocat",
            "atom",
            "electron",
            "API"
            ],
            "has_issues": true,
            "has_projects": true,
            "has_wiki": true,
            "has_pages": false,
            "has_downloads": true,
            "archived": false,
            "pushed_at": "2011-01-26T19:06:43Z",
            "created_at": "2011-01-26T19:01:12Z",
            "updated_at": "2011-01-26T19:14:43Z",
            "permissions": {
            "admin": false,
            "push": false,
            "pull": true
            },
            "subscribers_count": 42,
            "network_count": 0,
            "license": {
            "key": "mit",
            "name": "MIT License",
            "spdx_id": "MIT",
            "url": "https://api.github.com/licenses/mit",
            "node_id": "MDc6TGljZW5zZW1pdA=="
            }
        }
     */

    var id: Int = 0
    var name: String? = null

    @SerializedName("full_name")
    var fullName: String? = null

    @SerializedName("private")
    var repPrivate: Boolean = false

    @SerializedName("html_url")
    var htmlUrl: String? = null

    var description: String? = null
    var language: String? = null
    var owner: UserBean? = null

    @SerializedName("default_branch")
    var defaultBranch: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("pushed_at")
    var pushedAt: String? = null

    @SerializedName("git_url")
    var gitUrl: String? = null

    @SerializedName("ssh_url")
    var sshUrl: String? = null

    @SerializedName("clone_url")
    var cloneUrl: String? = null

    @SerializedName("svn_url")
    var svnUrl: String? = null

    var size: Long = 0

    @SerializedName("stargazers_count")
    var stargazersCount: Int = 0

    @SerializedName("watchers_count")
    var watchersCount: Int = 0

    @SerializedName("forks_count")
    var forksCount: Int = 0

    @SerializedName("open_issues_count")
    var openIssuesCount: Int = 0

    @SerializedName("subscribers_count")
    var subscribersCount: Int = 0

    var fork: Boolean = false
    var parent: RepoBean? = null
    var permissions: RepoPermissionsBean? = null

    @SerializedName("has_issues")
    var hasIssues: Boolean = false

    @SerializedName("has_projects")
    var hasProjects: Boolean = false

    @SerializedName("has_downloads")
    var hasDownloads: Boolean = false

    @SerializedName("has_wiki")
    var hasWiki: Boolean = false

    @SerializedName("has_pages")
    var hasPages: Boolean = false

    var sinceStargazersCount: Int = 0

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(name)
        dest.writeString(fullName)
        dest.writeInt(if(repPrivate) 1 else 0)
        dest.writeString(htmlUrl)
        dest.writeString(description)
        dest.writeString(language)
        dest.writeParcelable(owner, flags)
        dest.writeString(defaultBranch)
        dest.writeString(createdAt)
        dest.writeString(updatedAt)
        dest.writeString(pushedAt)
        dest.writeString(gitUrl)
        dest.writeString(sshUrl)
        dest.writeString(cloneUrl)
        dest.writeString(svnUrl)
        dest.writeLong(size)
        dest.writeInt(stargazersCount)
        dest.writeInt(watchersCount)
        dest.writeInt(forksCount)
        dest.writeInt(openIssuesCount)
        dest.writeInt(subscribersCount)
        dest.writeInt(if(fork) 1 else 0)
        dest.writeParcelable(parent, flags)
        dest.writeParcelable(permissions, flags)
        dest.writeInt(if(hasIssues) 1 else 0)
        dest.writeInt(if(hasProjects) 1 else 0)
        dest.writeInt(if(hasDownloads) 1 else 0)
        dest.writeInt(if(hasWiki) 1 else 0)
        dest.writeInt(if(hasPages) 1 else 0)
        dest.writeInt(sinceStargazersCount)
    }

    constructor(source: Parcel)  {
        id = source.readInt()
        name = source.readString()
        fullName = source.readString()
        repPrivate = source.readInt() == 1
        htmlUrl = source.readString()
        description = source.readString()
        language = source.readString()
        owner = source.readParcelable(UserBean::class.java.classLoader)
        defaultBranch = source.readString()
        createdAt = source.readString()
        updatedAt = source.readString()
        pushedAt = source.readString()
        gitUrl = source.readString()
        sshUrl = source.readString()
        cloneUrl = source.readString()
        svnUrl = source.readString()
        size = source.readLong()
        stargazersCount = source.readInt()
        watchersCount = source.readInt()
        forksCount = source.readInt()
        openIssuesCount = source.readInt()
        subscribersCount = source.readInt()
        fork = source.readInt() == 1
        parent = source.readParcelable(RepoBean::class.java.classLoader)
        permissions = source.readParcelable(RepoPermissionsBean::class.java.classLoader)
        hasIssues = source.readInt() == 1
        hasProjects = source.readInt() == 1
        hasDownloads = source.readInt() == 1
        hasWiki = source.readInt() == 1
        hasPages = source.readInt() == 1
        sinceStargazersCount = source.readInt()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RepoBean> = object : Parcelable.Creator<RepoBean> {
            override fun createFromParcel(source: Parcel): RepoBean {
                return RepoBean(source)
            }
            override fun newArray(size: Int): Array<RepoBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}