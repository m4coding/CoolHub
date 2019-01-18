package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author mochangsheng
 * @description 用户信息Bean
 */
class UserBean : Parcelable {
    /**
     *{
    "login": "m4coding",
    "id": 13324985,
    "node_id": "MDQ6VXNlcjEzMzI0OTg1",
    "avatar_url": "https://avatars3.githubusercontent.com/u/13324985?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/m4coding",
    "html_url": "https://github.com/m4coding",
    "followers_url": "https://api.github.com/users/m4coding/followers",
    "following_url": "https://api.github.com/users/m4coding/following{/other_user}",
    "gists_url": "https://api.github.com/users/m4coding/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/m4coding/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/m4coding/subscriptions",
    "organizations_url": "https://api.github.com/users/m4coding/orgs",
    "repos_url": "https://api.github.com/users/m4coding/repos",
    "events_url": "https://api.github.com/users/m4coding/events{/privacy}",
    "received_events_url": "https://api.github.com/users/m4coding/received_events",
    "type": "User",
    "site_admin": false,
    "name": null,
    "company": null,
    "blog": "",
    "location": "guangzhou",
    "email": "m4coding@qq.com",
    "hireable": null,
    "bio": "Do a good",
    "public_repos": 8,
    "public_gists": 0,
    "followers": 4,
    "following": 18,
    "created_at": "2015-07-14T02:11:30Z",
    "updated_at": "2018-09-15T03:45:47Z"
    }
     *
     */

    var login: String? = null

    var id: String? = null

    @SerializedName("node_id")
    var nodId: String? = null

    @SerializedName("avatar_url")
    var avatarUrl: String? = null

    @SerializedName("gravatar_id")
    var gravatarId: String? = null

    var url: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null

    @SerializedName("followers_url")
    var followersUrl: String? = null

    @SerializedName("following_url")
    var followingUrl: String? = null

    @SerializedName("gists_url")
    var gistsUrl: String? = null

    @SerializedName("starred_url")
    var starredUrl: String? = null

    @SerializedName("subscriptions_url")
    var subscriptionsUrl: String? = null

    @SerializedName("organizations_url")
    var organizationsUrl: String? = null

    @SerializedName("repos_url")
    var reposUrl: String? = null

    @SerializedName("events_url")
    var eventsUrl: String? = null

    @SerializedName("received_events_url")
    var receivedEventsUrl: String? = null

    var type: String? = null

    @SerializedName("site_admin")
    var siteAdmin: Boolean? = null

    var name: String? = null

    var company: String? = null

    var blog: String? = null

    var location: String? = null

    var email: String? = null

    var hireable: String? = null

    var bio: String? = null

    @SerializedName("public_repos")
    var publicRepos: Int? = 0

    @SerializedName("public_gists")
    var publicGists: Int? = 0

    var followers: Int? = 0

    var following: Int? = 0

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    override fun toString(): String {
        return "UserBean(login=$login,\n" +
                "id=$id,\n" +
                "nodId=$nodId,\n" +
                "avatarUrl=$avatarUrl,\n" +
                "gravatarId=$gravatarId,\n" +
                "url=$url,\n" +
                "htmlUrl=$htmlUrl,\n" +
                "followersUrl=$followersUrl,\n" +
                "followingUrl=$followingUrl,\n" +
                "gistsUrl=$gistsUrl,\n" +
                "starredUrl=$starredUrl,\n" +
                "subscriptionsUrl=$subscriptionsUrl,\n" +
                "organizationsUrl=$organizationsUrl,\n" +
                "reposUrl=$reposUrl,\n" +
                "eventsUrl=$eventsUrl,\n" +
                "receivedEventsUrl=$receivedEventsUrl,\n" +
                "type=$type,\n" +
                "siteAdmin=$siteAdmin,\n" +
                "name=$name,\n" +
                "company=$company,\n" +
                "blog=$blog,\n" +
                "location=$location,\n" +
                "email=$email,\n" +
                "hireable=$hireable,\n" +
                "bio=$bio,\n" +
                "publicRepos=$publicRepos,\n" +
                "publicGists=$publicGists,\n" +
                "followers=$followers,\n" +
                "following=$following,\n" +
                "createdAt=$createdAt,\n" +
                "updatedAt=$updatedAt)"
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(login)
        dest.writeString(id)
        dest.writeString(nodId)
        dest.writeString(avatarUrl)
        dest.writeString(gravatarId)
        dest.writeString(url)
        dest.writeString(htmlUrl)
        dest.writeString(followersUrl)
        dest.writeString(followingUrl)
        dest.writeString(gistsUrl)
        dest.writeString(starredUrl)
        dest.writeString(subscriptionsUrl)
        dest.writeString(organizationsUrl)
        dest.writeString(reposUrl)
        dest.writeString(eventsUrl)
        dest.writeString(receivedEventsUrl)
        dest.writeString(type)
        dest.writeInt(if(siteAdmin == true) 1 else 0)
        dest.writeString(name)
        dest.writeString(company)
        dest.writeString(blog)
        dest.writeString(location)
        dest.writeString(email)
        dest.writeString(hireable)
        dest.writeString(bio)
        dest.writeInt(publicRepos ?: 0)
        dest.writeInt(publicGists ?: 0)
        dest.writeInt(followers ?: 0)
        dest.writeValue(following)
        dest.writeString(createdAt)
        dest.writeString(updatedAt)
    }

    constructor(source: Parcel)  {
        login = source.readString()
        id = source.readString()
        nodId = source.readString()
        avatarUrl = source.readString()
        gravatarId = source.readString()
        url = source.readString()
        htmlUrl = source.readString()
        followersUrl = source.readString()
        followingUrl = source.readString()
        gistsUrl = source.readString()
        starredUrl = source.readString()
        subscriptionsUrl = source.readString()
        organizationsUrl = source.readString()
        reposUrl = source.readString()
        eventsUrl = source.readString()
        receivedEventsUrl = source.readString()
        type = source.readString()
        siteAdmin = source.readInt() == 1
        name = source.readString()
        company = source.readString()
        blog = source.readString()
        location = source.readString()
        email = source.readString()
        hireable = source.readString()
        bio = source.readString()
        publicRepos = source.readInt()
        publicGists = source.readInt()
        followers = source.readInt()
        following = source.readInt()
        createdAt = source.readString()
        updatedAt = source.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<UserBean> = object : Parcelable.Creator<UserBean> {
            override fun createFromParcel(source: Parcel): UserBean {
                return UserBean(source)
            }
            override fun newArray(size: Int): Array<UserBean?> {
                return arrayOfNulls(size)
            }
        }
    }


}