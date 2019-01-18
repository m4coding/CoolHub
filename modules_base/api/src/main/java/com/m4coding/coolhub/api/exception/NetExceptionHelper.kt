package com.m4coding.coolhub.api.exception

import com.google.gson.JsonParseException

import org.json.JSONException
import retrofit2.HttpException
import java.lang.StringBuilder

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException


/**
 * 异常包装，处理成通用的ApiException  （包括网络异常和服务器本身反馈回来的业务异常）
 */
object NetExceptionHelper {

    //对应HTTP的状态码
    private const val UNAUTHORIZED = 401 //未经授权
    private const val FORBIDDEN = 403 //禁止
    private const val NOT_FOUND = 404 //未发现
    private const val REQUEST_TIMEOUT = 408 //请求超时
    private const val INTERNAL_SERVER_ERROR = 500 //服务器内部错误
    private const val BAD_GATEWAY = 502 //作为网关或者代理工作的服务器尝试执行请求时，从上游服务器接收到无效的响应。
    private const val SERVICE_UNAVAILABLE = 503 //服务不可用
    private const val GATEWAY_TIMEOUT = 504 //作为网关或者代理工作的服务器尝试执行请求时，未能及时从上游服务器 （超时）

    private fun getHttpErrorString(code: Int, displayMessage: String): String {
        return StringBuilder().append("code: ").append(code).append(" ").append(displayMessage).toString()
    }

    fun wrapException(e: Throwable): ApiException {
        val ex: ApiException
        if (e is HttpException) {             //HTTP错误
            ex = ApiException(e, ErrorCode.HTTP_ERROR)
            ex.displayMessage = getHttpErrorString(e.code(), e.message())
            /*when (e.code()) {
                FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex.displayMessage = "网络错误"  //均视为网络错误
                else -> ex.displayMessage = "网络错误"
            }*/
            return ex
        } else if (e is ServerException) {   //服务器返回的错误
            ex = ApiException(e, e.code)
            ex.displayMessage = e.msg
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex = ApiException(e, ErrorCode.PARSE_ERROR)
            ex.displayMessage = "解析错误"            //均视为解析错误
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(e, ErrorCode.NETWORD_ERROR)
            ex.displayMessage = "连接失败"  //均视为网络错误
            return ex
        }  else if (e is SocketTimeoutException) {
            ex = ApiException(e, ErrorCode.NETWORD_ERROR)
            ex.displayMessage = "连接超时"  //均视为网络错误
            return ex
        } else {
            ex = ApiException(e, ErrorCode.UNKNOWN)
            ex.displayMessage = "未知错误"          //未知错误
            return ex
        }
    }
}
