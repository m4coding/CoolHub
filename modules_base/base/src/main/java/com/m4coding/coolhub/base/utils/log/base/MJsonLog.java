package com.m4coding.coolhub.base.utils.log.base;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author mochangsheng
 * @version 1.0
 * @description json打印实现
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MJsonLog {

    /**
     *
     * @param tag
     * @param msg
     * @param headString
     * 打印形式：
     * ╔═══════════════════════════════════════════════════════════════════
     * ║ headString
     * ║ type
     * ║ {
     * ║     "tag": "其他",
     * ║     "menu": [
     * ║         "泰式柠檬肉片",
     * ║         "鸡柳汉堡",
     * ║         "蒸桂鱼卷 "
     * ║     ]
     * ║ }
     * ╚═══════════════════════════════════════════════════════════════════
     */
    public static void printLog(int type, String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(MLogConstant.JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(MLogConstant.JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        MLogConstant.printLine(tag, true);
        String tmpMsg = "";
        switch (type) {
            case MLogConstant.VERBOSE:
                tmpMsg = MLogConstant.TYPE_VERBOSE_FORMAT;
                break;
            case MLogConstant.DEBUG:
                tmpMsg = MLogConstant.TYPE_DEBUG_FORMAT;
                break;
            case MLogConstant.INFO:
                tmpMsg = MLogConstant.TYPE_INFO_FORMAT;
                break;
            case MLogConstant.WARN:
                tmpMsg = MLogConstant.TYPE_WARN_FORMAT;
                break;
            case MLogConstant.ERROR:
                tmpMsg = MLogConstant.TYPE_ERROR_FORMAT;
                break;
            case MLogConstant.ASSERT:
                tmpMsg = MLogConstant.TYPE_ASSERT_FORMAT;
                break;
        }

        if (!TextUtils.isEmpty(tmpMsg)) {
            message = headString + MLogConstant.LINE_SEPARATOR + tmpMsg
                    + MLogConstant.LINE_SEPARATOR + message;
        } else {
            message = headString + MLogConstant.LINE_SEPARATOR + message;
        }
        String[] lines = message.split(MLogConstant.LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        MLogConstant.printLine(tag, false);
    }
}
