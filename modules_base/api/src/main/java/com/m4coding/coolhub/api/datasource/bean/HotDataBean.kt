package com.m4coding.coolhub.api.datasource.bean

/**
 * @author mochangsheng
 * @description
 */
class HotDataBean {
    var fullName: String? = null
    var ownerName: String? = null
    var repositoryName: String? = null
    var describe: String? = null //项目描述
    var language: String? = null
    var allStarNum: Int = 0  //总的星标数
    var forkNum: Int = 0 //fork数
    var timeType: String? = null  //时间类型
    var starNumInTime: Int = 0 //在限定时间内的星标数  （如一天内、一周内、一个月内）
}