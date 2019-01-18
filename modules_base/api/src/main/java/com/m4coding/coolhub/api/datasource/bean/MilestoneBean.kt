package com.m4coding.coolhub.api.datasource.bean

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author mochangsheng
 * @description
 */
class MilestoneBean {
    enum class State {
        OPEN, CLOSED
    }

    var id: Long = 0
    var number: Int = 0
    var title: String? = null
    var description: String? = null
    var creator: UserBean? = null

    @SerializedName("open_issues")
    var openIssues: Int = 0
    @SerializedName("closed_issues")
    var closedIssues: Int = 0
    var state: MilestoneBean.State? = null

    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    @SerializedName("due_on")
    var dueOn: Date? = null
    @SerializedName("closed_at")
    var closedAt: Date? = null
}