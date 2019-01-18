package com.m4coding.coolhub.base.utils.log.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author mochangsheng
 * @version 1.0
 * @description file打印实现
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MFileLog {

    private static final String FILE_PREFIX = "MLog_";
    private static final String FILE_FORMAT = ".log";

    public static boolean printLog(int type, String tag, File targetDirectory, @Nullable String fileName, String msg) {

        fileName = (fileName == null) ? getDefaultFileName() : fileName;
        if (save(type, targetDirectory, fileName, msg)) {
            return true;
        } else {
            Log.e(tag, "save log to file fails !");
            return false;
        }
    }

    private static boolean save(int type, File dic, @NonNull String fileName, String msg) {

        File file = new File(dic, fileName);
        isToDelete(file);

        String tmpMsg = "";
        String time = getNowMDHMSTime();

        switch (type) {
            case MLogConstant.VERBOSE:
                tmpMsg = String.format("%s: type:VERBOSE# %s", time, msg);
                break;
            case MLogConstant.DEBUG:
                tmpMsg = String.format("%s: type:DEBUG# %s", time, msg);
                break;
            case MLogConstant.INFO:
                tmpMsg = String.format("%s: type:INFO# %s", time, msg);
                break;
            case MLogConstant.WARN:
                tmpMsg = String.format("%s: type:WARN# %s", time, msg);
                break;
            case MLogConstant.ERROR:
                tmpMsg = String.format("%s: type:ERROR# %s", time, msg);
                break;
            case MLogConstant.ASSERT:
                tmpMsg = String.format("%s: type:ASSERT# %s", time, msg);
                break;
        }


        try {

            try {
                FileWriter filerWriter = new FileWriter(file, true);
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write(tmpMsg);
                bufWriter.newLine();
                bufWriter.close();
                filerWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String getNowMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    private static void isToDelete(File file) {
        if (file.length() > 1024 * 1024) {//大于1m时，删除
            file.delete();
        }
    }

    private static String getDefaultFileName() {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }
}
