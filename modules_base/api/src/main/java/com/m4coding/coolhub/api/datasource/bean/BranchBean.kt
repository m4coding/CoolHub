package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable

class BranchBean() : Parcelable {

   var name: String = ""

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BranchBean> {
        override fun createFromParcel(parcel: Parcel): BranchBean {
            return BranchBean(parcel)
        }

        override fun newArray(size: Int): Array<BranchBean?> {
            return arrayOfNulls(size)
        }
    }
}