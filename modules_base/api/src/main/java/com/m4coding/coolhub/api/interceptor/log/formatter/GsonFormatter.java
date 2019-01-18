package com.m4coding.coolhub.api.interceptor.log.formatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

class GsonFormatter extends JSONFormatter {
    //GSON解析=等号出现乱码 （如=解析成\u003d） 使用disableHtmlEscaping方法解决
    private final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final JsonParser PARSER = new JsonParser();

    @Override
    String format(String source) {
        return GSON.toJson(PARSER.parse(source));
    }

    static JSONFormatter buildIfSupported() {
        try {
            Class.forName("com.google.gson.Gson");
            return new GsonFormatter();
        } catch (ClassNotFoundException ignore) {
            return null;
        }
    }

}