package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author mochangsheng
 * @description
 */
class IssueBean() : Parcelable {
    enum class IssueState {
        open, closed
    }

    enum class IssueAuthorAssociation {
        OWNER, CONTRIBUTOR, NONE
    }

    var id: String? = null
    var number: Int = 0
    var title: String? = null
    var state: IssueState? = null
    var locked: Boolean = false
    @SerializedName("comments")
    var commentNum: Int = 0

    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("closed_at")
    var closedAt: Date? = null
    var body: String? = null
    @SerializedName("body_html")
    var bodyHtml: String? = null

    var user: UserBean? = null
    @SerializedName("author_association")
    var authorAssociation: IssueAuthorAssociation? = null
    @SerializedName("repository_url")
    var repoUrl: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null

    var labels: ArrayList<LabelBean>? = null
    var assignee: UserBean? = null
    var assignees: ArrayList<UserBean>? = null
    var milestone: MilestoneBean? = null
    @SerializedName("closed_by")
    var closedBy: UserBean? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        number = parcel.readInt()
        title = parcel.readString()
        locked = parcel.readByte() != 0.toByte()
        commentNum = parcel.readInt()
        body = parcel.readString()
        bodyHtml = parcel.readString()
        user = parcel.readParcelable(UserBean::class.java.classLoader)
        repoUrl = parcel.readString()
        htmlUrl = parcel.readString()
        assignee = parcel.readParcelable(UserBean::class.java.classLoader)
        closedBy = parcel.readParcelable(UserBean::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(number)
        parcel.writeString(title)
        parcel.writeByte(if (locked) 1 else 0)
        parcel.writeInt(commentNum)
        parcel.writeString(body)
        parcel.writeString(bodyHtml)
        parcel.writeParcelable(user, flags)
        parcel.writeString(repoUrl)
        parcel.writeString(htmlUrl)
        parcel.writeParcelable(assignee, flags)
        parcel.writeParcelable(closedBy, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IssueBean> {
        override fun createFromParcel(parcel: Parcel): IssueBean {
            return IssueBean(parcel)
        }

        override fun newArray(size: Int): Array<IssueBean?> {
            return arrayOfNulls(size)
        }
    }
}