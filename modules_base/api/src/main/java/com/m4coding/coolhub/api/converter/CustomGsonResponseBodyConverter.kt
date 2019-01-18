package com.m4coding.coolhub.api.converter

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.m4coding.coolhub.api.exception.ApiException
import com.m4coding.coolhub.api.exception.ServerException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import okhttp3.internal.Util.UTF_8

/**
 * @author mochangsheng
 * @description  json处理，并统一处理业务异常
 *
 * @例如
     {
       "code":200
       "message":"错误原因"
       "data": {}
      }
 */
internal class CustomGsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val rsString = value.string()
        val contentType  = value.contentType()
        try {
            //此处，如果解析的json为数组会报异常
            val response = JSONObject(rsString)

            // 结果状态不对的，统一抛出异常，进入Subscriber的onError回调函数
            val hasCode = response.has("code")
            if (hasCode && response.optInt("code") != 200) {
                value.close()
                throw ServerException(response.optInt("code"), response.optString("message"))
            }

            var info:String? = null
            if (hasCode && response.has("data")) {
                //如果符合Bean，则只取data即可
                info = response.optString("data")
                if (TextUtils.isEmpty(info) || TextUtils.equals(info.toLowerCase(), "null")) {
                    info = "{}"
                }
            } else {
                info = rsString
            }

            return adapter.read(getJsonReader(info, contentType))
        } catch (e: JSONException) {
            //有解析异常则直接返回原来的
            return adapter.read(getJsonReader(rsString, contentType))
        } finally {
            value.close()
        }
    }

    private fun getJsonReader(info: String?, contentType: MediaType?): JsonReader {
        val charset = if (contentType != null)  contentType.charset(UTF_8) else UTF_8
        val inputStream = ByteArrayInputStream(info?.toByteArray())
        val reader = InputStreamReader(inputStream, charset)
        return gson.newJsonReader(reader)
    }
}