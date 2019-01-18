package com.m4coding.coolhub.api.exception


/**
 * 网络通用异常 （包括网络异常和后端业务异常）
 */
class ApiException(throwable: Throwable, val code: Int) : Exception(throwable) {
    var displayMessage: String? = null
}
