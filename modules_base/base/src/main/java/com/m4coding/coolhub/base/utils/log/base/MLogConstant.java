package com.m4coding.coolhub.base.utils.log.base;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author mochangsheng
 * @version 1.0
 * @description MLog使用常量
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MLogConstant {

    public static final int VERBOSE = 0x1;
    public static final int DEBUG = 0x2;
    public static final int INFO = 0x3;
    public static final int WARN = 0x4;
    public static final int ERROR = 0x5;
    public static final int ASSERT = 0x6;

    public static final int OUTPUT_TYPE_FILE = 0x7;
    public static final int OUTPUT_TYPE_LOG = 0x8;
    public static final int OUTPUT_TYPE_JSON = 0x9;
    public static final int OUTPUT_TYPE_XML = 0x0a;

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String NULL_TIPS = "Log with null object";

    public static final String TYPE_VERBOSE_FORMAT = "type:VERBOSE# ";
    public static final String TYPE_DEBUG_FORMAT = "type:DEBUG# ";
    public static final String TYPE_INFO_FORMAT = "type:INFO# ";
    public static final String TYPE_WARN_FORMAT = "type:WARN# ";
    public static final String TYPE_ERROR_FORMAT = "type:ERROR# ";
    public static final String TYPE_ASSERT_FORMAT = "type:ASSERT# ";

    public static final String SUFFIX = ".java";

    //缩进数
    public static final int JSON_INDENT = 4;

    //打印出上下表框（由制表符组成）
    static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }

    public static boolean isEmpty(String line) {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }
}
