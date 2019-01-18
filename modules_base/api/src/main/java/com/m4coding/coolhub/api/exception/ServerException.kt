package com.m4coding.coolhub.api.exception

/**
 * 后端业务异常
 */
class ServerException(val code: Int, val msg: String) : RuntimeException()
