package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class SearchResultBean<M>() {

    @SerializedName("total_count")
    var totalCount: Int = 0

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false

    var items: ArrayList<M>? = null
}