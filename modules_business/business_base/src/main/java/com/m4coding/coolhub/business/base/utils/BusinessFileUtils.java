package com.m4coding.coolhub.business.base.utils;

import android.os.Environment;

import java.io.File;

public class BusinessFileUtils {
    private static final String BASE_PATH = "/CoolHub/";
    private static final String CRASH_LOG = "log/crash_log.txt";


    public static String getBasePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + BASE_PATH;
    }

    /**
     * 获取外部存储，app根目录的file对象
     */
    public static File getBaseFile() {
        File file = new File(getBasePath());
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }

    public static String getCrashLogPath() {
        return getBasePath() + CRASH_LOG;
    }
}
