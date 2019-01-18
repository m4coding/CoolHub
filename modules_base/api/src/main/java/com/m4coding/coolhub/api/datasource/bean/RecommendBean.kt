package com.m4coding.coolhub.api.datasource.bean


/**
 * @author mochangsheng
 * @description  推荐Bean
 */
class RecommendBean {
    var repositoryId: String? = null
    var userId: String? = null
    var repositoryName: String? = null
    var avatar: String? = null
    var describe: String? = null
    var tagsList: List<String>? = null
    var language: String? = null
    var starNum: Int = 0
}
