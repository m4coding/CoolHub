package com.m4coding.coolhub.base.utils.log.base;

import android.util.Log;

/**
 * @author mochangsheng
 * @version 1.0
 * @description logcat打印实现
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MBaseLog {

    private static final int MAX_LENGTH = 4000;

    public static void printLog(int type, String tag, String msg) {

        int index = 0;
        int length = msg.length();
        int countOfOver = length / MAX_LENGTH;

        if (countOfOver > 0) { //logcat最长只能4000个字符打印，当过长时分开打印
            for (int i = 0; i < countOfOver; i++) {
                String sub = msg.substring(index, index + MAX_LENGTH);
                printLogOnType(type, tag, sub);
                index += MAX_LENGTH;
            }
            printLogOnType(type, tag, msg.substring(index, length));
        } else {
            printLogOnType(type, tag, msg);
        }
    }

    private static void printLogOnType(int type, String tag, String msg) {
        switch (type) {
            case MLogConstant.VERBOSE:
                Log.v(tag, msg);
                break;
            case MLogConstant.DEBUG:
                Log.d(tag, msg);
                break;
            case MLogConstant.INFO:
                Log.i(tag, msg);
                break;
            case MLogConstant.WARN:
                Log.w(tag, msg);
                break;
            case MLogConstant.ERROR:
                Log.e(tag, msg);
                break;
            case MLogConstant.ASSERT:
                Log.wtf(tag, msg);
                break;
        }
    }
}
