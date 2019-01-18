package com.m4coding.coolhub.api.datasource.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */
class FileBean() : Parcelable {


    /**
        {
        "type": "file",
        "encoding": "base64",
        "size": 5362,
        "name": "README.md",
        "path": "README.md",
        "content": "encoded content ...",
        "sha": "3d21ec53a331a6f037a91c368710b99387d012c1",
        "url": "https://api.github.com/repos/octokit/octokit.rb/contents/README.md",
        "git_url": "https://api.github.com/repos/octokit/octokit.rb/git/blobs/3d21ec53a331a6f037a91c368710b99387d012c1",
        "html_url": "https://github.com/octokit/octokit.rb/blob/master/README.md",
        "download_url": "https://raw.githubusercontent.com/octokit/octokit.rb/master/README.md",
        "_links": {
            "git": "https://api.github.com/repos/octokit/octokit.rb/git/blobs/3d21ec53a331a6f037a91c368710b99387d012c1",
            "self": "https://api.github.com/repos/octokit/octokit.rb/contents/README.md",
            "html": "https://github.com/octokit/octokit.rb/blob/master/README.md"
        }
        }
     */

    var type : String? = null
    var encoding : String? = null
    var size : Long = 0
    var name : String? = null
    var path : String? = null
    var content: String? = null
    var sha: String? = null
    var url: String? = null

    @SerializedName("git_url")
    var gitUrl: String? = null

    @SerializedName("html_url")
    var htmlUrl: String? = null

    @SerializedName("download_url")
    var downloadUrl: String? = null

    @SerializedName("_links")
    var links: Links? = null

    constructor(parcel: Parcel) : this() {
        type = parcel.readString()
        encoding = parcel.readString()
        size = parcel.readLong()
        name = parcel.readString()
        path = parcel.readString()
        content = parcel.readString()
        sha = parcel.readString()
        url = parcel.readString()
        gitUrl = parcel.readString()
        htmlUrl = parcel.readString()
        downloadUrl = parcel.readString()
        links  = parcel.readParcelable(Links::class.java.classLoader)
    }

    class Links() : Parcelable {
        var git: String? = null
        var self: String? = null
        var html: String? = null

        constructor(parcel: Parcel) : this() {
            git = parcel.readString()
            self = parcel.readString()
            html = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(git)
            parcel.writeString(self)
            parcel.writeString(html)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Links> {
            override fun createFromParcel(parcel: Parcel): Links {
                return Links(parcel)
            }

            override fun newArray(size: Int): Array<Links?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(encoding)
        parcel.writeLong(size)
        parcel.writeString(name)
        parcel.writeString(path)
        parcel.writeString(content)
        parcel.writeString(sha)
        parcel.writeString(url)
        parcel.writeString(gitUrl)
        parcel.writeString(htmlUrl)
        parcel.writeString(downloadUrl)
        parcel.writeParcelable(links, flags)
    }

    override fun describeContents(): Int {
        return 0
    }


    companion object {
        //type定义
        const val TYPE_FILE = "file"
        const val TYPE_DIR = "dir"

        //parcelable需要
        @JvmField
        val CREATOR: Parcelable.Creator<FileBean> = object : Parcelable.Creator<FileBean> {
            override fun createFromParcel(parcel: Parcel): FileBean {
                return FileBean(parcel)
            }

            override fun newArray(size: Int): Array<FileBean?> {
                return arrayOfNulls(size)
            }
        }
    }

}