package com.m4coding.coolhub.api.datasource.bean

/**
 * @author mochangsheng
 * @description
 */
class PushEventCommitBean {
    var sha: String? = null
    //email&name
    var author: UserBean? = null
    var message: String? = null
    var distinct: Boolean = false
    var url: String? = null
}