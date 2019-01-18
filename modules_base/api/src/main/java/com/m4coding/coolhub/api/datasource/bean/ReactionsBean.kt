package com.m4coding.coolhub.api.datasource.bean

import com.google.gson.annotations.SerializedName

/**
 * @author mochangsheng
 * @description
 */
class ReactionsBean {
    @SerializedName("total_count")
    var totalCount: Int = 0
    @SerializedName("+1")
    var plusOne: Int = 0
    @SerializedName("-1")
    var minusOne: Int = 0
    var laugh: Int = 0
    var hooray: Int = 0
    var confused: Int = 0
    var heart: Int = 0
}