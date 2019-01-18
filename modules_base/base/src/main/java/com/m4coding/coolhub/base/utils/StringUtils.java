package com.m4coding.coolhub.base.utils;


import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class StringUtils {

    /**
     * json 格式化
     * @param bodyString
     * @return
     */
    public static String jsonFormat(String bodyString) {
        String message;
        try {
            if (bodyString.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(bodyString);
                message = jsonObject.toString(4);
            } else if (bodyString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(bodyString);
                message = jsonArray.toString(4);
            } else {
                message = bodyString;
            }
        } catch (JSONException e) {
            message = bodyString;
        }
        return message;
    }

    public static String upCaseFirstChar(String str){
        if(isBlank(str)) return null;
        return str.substring(0, 1).toUpperCase().concat(str.substring(1));
    }

    public static boolean isBlank(@Nullable String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isBlankList(@Nullable List list) {
        return list == null || list.size() == 0;
    }
}
