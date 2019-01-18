package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author mochangsheng
 * @description 仓库权限bean
 */
class RepoPermissionsBean : Parcelable{
    var admin: Boolean = false
    var push: Boolean = false
    var pull: Boolean = false

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(if(admin) 1 else 0)
        dest.writeInt(if(push) 1 else 0)
        dest.writeInt(if(pull) 1 else 0)
    }

    constructor(source: Parcel)  {
        admin = source.readInt() == 1
        push = source.readInt() == 1
        pull = source.readInt() == 1
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RepoPermissionsBean> = object : Parcelable.Creator<RepoPermissionsBean> {
            override fun createFromParcel(source: Parcel): RepoPermissionsBean {
                return RepoPermissionsBean(source)
            }
            override fun newArray(size: Int): Array<RepoPermissionsBean?> {
                return arrayOfNulls(size)
            }
        }
    }
}