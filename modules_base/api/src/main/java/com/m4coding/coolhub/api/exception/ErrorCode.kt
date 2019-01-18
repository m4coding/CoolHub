package com.m4coding.coolhub.api.exception

/**.
 * 约定好的异常
 */
object ErrorCode {
    /**
     * 未知错误
     */
    const val UNKNOWN = 1000
    /**
     * 解析错误
     */
    const val PARSE_ERROR = 1001
    /**
     * 网络错误
     */
    const val NETWORD_ERROR = 1002
    /**
     * 协议出错  (HTTP异常)
     */
    const val HTTP_ERROR = 1003
}
