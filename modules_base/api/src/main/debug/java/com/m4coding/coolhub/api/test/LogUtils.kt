package com.m4coding.coolhub.api.test

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author mochangsheng
 * @description
 */
object LogUtils {

    //规定每段显示的长度
    private const val LOG_MAXLENGTH: Int = 2000

    fun obtainExceptionInfo(throwable: Throwable): String {
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        throwable.printStackTrace(printWriter)
        printWriter.close()
        val message = stringWriter.toString()
        stringWriter.close()
        return message
    }

    fun e(tag: String, msg: String) {
        val strLength: Int = msg.length
        var start: Int = 0
        var end: Int = LOG_MAXLENGTH
        for (i in 1..100){
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.e(tag + i, msg.substring(start, end))
                start = end
                end += LOG_MAXLENGTH
            } else {
                Log.e(tag, msg.substring(start, strLength))
                break
            }
        }
    }
}