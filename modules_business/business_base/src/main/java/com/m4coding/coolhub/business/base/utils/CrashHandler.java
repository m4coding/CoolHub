package com.m4coding.coolhub.business.base.utils;


import com.m4coding.coolhub.base.manager.AppManager;
import com.m4coding.coolhub.base.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义系统的Crash捕捉类，用Toast替换系统的对话框
 * 将软件版本信息，设备信息，出错信息保存在sd卡中，你可以上传到服务器中
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler mInstance = new CrashHandler();

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return mInstance;
    }

    /**
     * 异常发生时，系统回调的函数，我们在这里处理一些操作
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String message = obtainExceptionInfo(ex);
        saveInfoToSdcard(message);

        AppManager.getInstance().killAll();
        System.exit(0);
    }

    private void saveInfoToSdcard(String message) {
        try {
            String path = BusinessFileUtils.getCrashLogPath();
            File file = new File(path);
            // 确保文件存在
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            BufferedWriter writer = null;
            StringBuilder sb = new StringBuilder();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());//设置日期格式
            sb.append(df.format(new Date()));
            sb.append("\n\n");
            sb.append(message);
            try {
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(sb.toString());
                writer.flush();
            } finally {
                FileUtils.close(writer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取系统未捕捉的错误信息
     *
     * @param throwable
     * @return
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter mStringWriter = new StringWriter();
        PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
        throwable.printStackTrace(mPrintWriter);
        mPrintWriter.close();
        return mStringWriter.toString();
    }

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
