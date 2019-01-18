package com.m4coding.coolhub.api.datasource.bean

import com.m4coding.coolhub.api.BuildConfig
import java.util.*

/**
 * @author mochangsheng
 * @description 认证请求参数bean
 */
class AuthRequestBean {
    var note: String? = null //名称
    var note_url: String? = null //应用的url
    var client_id: String? = null //客户端id，github中注册的
    var client_secret: String? = null //客户端secret，github中注册的
    var fingerprint: String? = null
    var scopes: List<String>? = null  //授权的范围列表

    companion object {
        fun create(): AuthRequestBean {
            val authRequestBean = AuthRequestBean()
            authRequestBean.client_id = BuildConfig.CLIENT_ID
            authRequestBean.client_secret = BuildConfig.CLIENT_SECRET
            authRequestBean.note = BuildConfig.APPLICATION_ID
            //权限设置，要设置好，否则会导致有些api不能使用，返回404 （例如starred api）
            authRequestBean.scopes = Arrays.asList("user", "repo", "gist", "notifications")
            return authRequestBean
        }
    }
}
