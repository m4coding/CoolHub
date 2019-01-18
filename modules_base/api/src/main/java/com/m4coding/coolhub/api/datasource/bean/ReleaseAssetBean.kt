package com.m4coding.coolhub.api.datasource.bean

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author mochangsheng
 * @description
 */
class ReleaseAssetBean {
    private var id: String? = null
    private var name: String? = null
    private var label: String? = null
    private var uploader: UserBean? = null

    @SerializedName("content_type")
    private var contentType: String? = null

    private var state: String? = null
    private var size: Long = 0
    private var downloadCout: Int = 0

    @SerializedName("created_at")
    private var createdAt: Date? = null

    @SerializedName("updated_at")
    private var updatedAt: Date? = null

    @SerializedName("browser_download_url")
    private var downloadUrl: String? = null
}