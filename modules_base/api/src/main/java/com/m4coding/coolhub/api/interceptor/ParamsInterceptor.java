package com.m4coding.coolhub.api.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * 参数添加拦截器
 */
public class ParamsInterceptor implements Interceptor {

    //Url 拼接参数Map
    private Map<String, String> mQueryParamsMap = new HashMap<>();
    //Post  Body拼接参数Map
    private Map<String, String> mParamsMap = new HashMap<>();
    //头部拼接参数Map （Key-value）
    private Map<String, String> mHeaderParamsMap = new HashMap<>();
    //头部拼接参数Map （直接一行）
    private List<String> mHeaderLinesList = new ArrayList<>();

    private ParamsInterceptor() {

    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        // process header params inject
        Headers.Builder headerBuilder = request.headers().newBuilder();
        if (mHeaderParamsMap.size() > 0) {
            Iterator iterator = mHeaderParamsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                headerBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }

        if (mHeaderLinesList.size() > 0) {
            for (String line: mHeaderLinesList) {
                headerBuilder.add(line);
            }
            requestBuilder.headers(headerBuilder.build());
        }
        // process header params end


        // process queryParams inject whatever it's GET or POST
        if (mQueryParamsMap.size() > 0) {
            request = injectParamsIntoUrl(request.url().newBuilder(), requestBuilder, mQueryParamsMap);
        }

        // process post body inject  （为Post的body添加参数）
        if (mParamsMap != null && mParamsMap.size() > 0 && request.method().equals("POST")) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder newFormBodyBuilder = new FormBody.Builder();
                mParamsMap.size();
                Iterator iterator = mParamsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    newFormBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }

                FormBody oldFormBody = (FormBody) request.body();
                int paramSize = oldFormBody.size();
                if (paramSize > 0) {
                    for (int i=0;i<paramSize;i++) {
                        newFormBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                    }
                }

                requestBuilder.post(newFormBodyBuilder.build());
                request = requestBuilder.build();
            } else if (request.body() instanceof MultipartBody) {
                MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                Iterator iterator = mParamsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    multipartBuilder.addFormDataPart((String) entry.getKey(), (String) entry.getValue());
                }

                List<MultipartBody.Part> oldParts = ((MultipartBody)request.body()).parts();
                if (oldParts != null && oldParts.size() > 0) {
                    for (MultipartBody.Part part : oldParts) {
                        multipartBuilder.addPart(part);
                    }
                }

                requestBuilder.post(multipartBuilder.build());
                request = requestBuilder.build();
            }

        }
        return chain.proceed(request);
    }

    // func to inject params into url （url拼接参数）
    private Request injectParamsIntoUrl(HttpUrl.Builder httpUrlBuilder, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder.build();
        }

        return null;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }


    /**
     * Builder构造
     */
    public static class Builder {

        ParamsInterceptor interceptor;

        public Builder() {
            interceptor = new ParamsInterceptor();
        }

        public Builder addParam(String key, String value) {
            interceptor.mParamsMap.put(key, value);
            return this;
        }

        public Builder addParamsMap(Map<String, String> paramsMap) {
            interceptor.mParamsMap.putAll(paramsMap);
            return this;
        }

        /**
         * 在 header 中插入键值对参数。
         * @param key
         * @param value
         * @return
         */
        public Builder addHeaderParam(String key, String value) {
            interceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        /**
         * 在 header 中插入键值对 Map 集合，批量插入
         * @param headerParamsMap
         * @return
         */
        public Builder addHeaderParamsMap(Map<String, String> headerParamsMap) {
            interceptor.mHeaderParamsMap.putAll(headerParamsMap);
            return this;
        }

        /**
         * 在 header 中插入 headerLine 字符串，字符串需要符合 -1 != headerLine.indexOf(“:”) 的规则，即可以解析成键值对。
         * @param headerLine
         * @return
         */
        public Builder addHeaderLine(String headerLine) {
            int index = headerLine.indexOf(":");
            if (index == -1) {
                throw new IllegalArgumentException("Unexpected header: " + headerLine);
            }
            interceptor.mHeaderLinesList.add(headerLine);
            return this;
        }

        /**
         * headerLineList: List 为参数，批量插入 headerLine
         * @param headerLinesList
         * @return
         */
        public Builder addHeaderLinesList(List<String> headerLinesList) {
            for (String headerLine: headerLinesList) {
                int index = headerLine.indexOf(":");
                if (index == -1) {
                    throw new IllegalArgumentException("Unexpected header: " + headerLine);
                }
                interceptor.mHeaderLinesList.add(headerLine);
            }
            return this;
        }


        /**
         * 插入键值对参数到url中
         * @param key
         * @param value
         * @return
         */
        public Builder addQueryParam(String key, String value) {
            interceptor.mQueryParamsMap.put(key, value);
            return this;
        }

        /**
         * 插入键值对参数map到url中，批量插入
         * @param queryParamsMap
         * @return
         */
        public Builder addQueryParamsMap(Map<String, String> queryParamsMap) {
            interceptor.mQueryParamsMap.putAll(queryParamsMap);
            return this;
        }

        public ParamsInterceptor build() {
            return interceptor;
        }

    }
}
