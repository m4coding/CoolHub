package com.m4coding.coolhub.api.datasource.bean

import com.google.gson.annotations.SerializedName

/**
 * @author mochangsheng
 * @description
 */
class LabelBean {
    private var id: Long = 0
    private var name: String? = null
    private var color: String? = null
    @SerializedName("default")
    private var isDefault: Boolean = false

    private var select: Boolean = false
}