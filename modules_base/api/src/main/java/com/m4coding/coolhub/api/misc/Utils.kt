package com.m4coding.coolhub.api.misc

import okhttp3.Credentials


/**
 * @author mochangsheng
 * @description api库的相关工具类
 */
object Utils {
    /**
     * 构造base授权中的header信息
     *
     * HTTP Basic Authorization : https://www.cnblogs.com/linxiyue/p/4079768.html
     */
    fun basicAuthorizationHeader(userName: String, password: String): String {
        return Credentials.basic(userName, password)
    }
}