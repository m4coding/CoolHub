package com.m4coding.coolhub.base.utils.log;

import android.text.TextUtils;

import com.m4coding.coolhub.base.utils.log.base.MBaseLog;
import com.m4coding.coolhub.base.utils.log.base.MFileLog;
import com.m4coding.coolhub.base.utils.log.base.MJsonLog;
import com.m4coding.coolhub.base.utils.log.base.MLogConstant;
import com.m4coding.coolhub.base.utils.log.base.MXmlLog;

import java.io.File;

/**
 * @author mochangsheng
 * @version 1.0
 * @description Log打印工具
 *      支持正常的log打印，file打印，json打印，xml打印
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MLog {


    private static final String TAG_DEFAULT = "MLog";
    private static final int STACK_TRACE_INDEX_5 = 5;
    private static final String DEFAULT_FILE_PATH = "/tmp"; //默认的log2file目录是系统的tmp目录

    //全局TAG
    private static String sGlobalTag = null;
    //是否显示log
    private static boolean sIsShowLog = true;
    private static int sLogFilter = MLogConstant.VERBOSE; //过滤log， 过滤到这个级别
    //是否log到file
    private static boolean sIsLog2File = false;
    //默认的log2file路径
    private static String sLog2FilePath = DEFAULT_FILE_PATH;
    //log2file name
    private static String sLog2FileName = null;


    /**
     * 配置MLog的build类
     */
    public static class MLogConfig {

        private String  globalTAG    = null;
        private boolean isLog2File   = false;
        private int     logFilter    = MLogConstant.VERBOSE;
        private boolean isShowLog    = true;
        private String  log2FilePath = DEFAULT_FILE_PATH;
        private String  log2FileName = null;

        public MLogConfig setGlobalTag(String tag) {
            this.globalTAG = tag;
            return this;
        }

        public MLogConfig setLog2File(boolean isLog2File) {
            this.isLog2File = isLog2File;
            return this;
        }

        public MLogConfig setLog2FilePath(String log2FilePath) {
            this.log2FilePath = log2FilePath;
            return this;
        }

        public MLogConfig setLog2FileName(String log2FileName) {
            this.log2FileName = log2FileName;
            return this;
        }

        public MLogConfig setLogFilter(int logFilter) {
            this.logFilter = logFilter;
            return this;
        }

        public MLogConfig setShow(boolean isShowLog) {
            this.isShowLog = isShowLog;
            return this;
        }

        private void build() {
            MLog.sGlobalTag = globalTAG;
            MLog.sIsLog2File = isLog2File;
            MLog.sLog2FilePath = log2FilePath;
            MLog.sLogFilter = logFilter;
            MLog.sIsShowLog = isShowLog;
            MLog.sLog2FileName = log2FileName;
        }
    }

    /**
     * 初始化MLog配置，不初始化将使用默认的
     * @param config
     */
    public static void init(MLogConfig config) {
        config.build();
    }

    //VERBOSE
    public static void v(Object msg) {
        printLog(MLogConstant.VERBOSE, MLogConstant.OUTPUT_TYPE_LOG, null, msg);//注意使用printLog
    }

    public static void v(String tag, Object msg) {
        printLog(MLogConstant.VERBOSE, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //DEBUG
    public static void d(Object msg) {
        printLog(MLogConstant.DEBUG, MLogConstant.OUTPUT_TYPE_LOG, null, msg);
    }

    public static void d(String tag, Object msg) {
        printLog(MLogConstant.DEBUG, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //INFO
    public static void i(Object msg) {
        printLog(MLogConstant.INFO, MLogConstant.OUTPUT_TYPE_LOG, null, msg);
    }

    public static void i(String tag, Object msg) {
        printLog(MLogConstant.INFO, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //WARN
    public static void w(Object msg) {
        printLog(MLogConstant.WARN, MLogConstant.OUTPUT_TYPE_LOG, null, msg);
    }

    public static void w(String tag, Object msg) {
        printLog(MLogConstant.WARN, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //ERROR
    public static void e(Object msg) {
        printLog(MLogConstant.ERROR, MLogConstant.OUTPUT_TYPE_LOG, null, msg);
    }

    public static void e(String tag, Object msg) {
        printLog(MLogConstant.ERROR, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //ASSERT
    public static void a(Object msg) {
        printLog(MLogConstant.ASSERT, MLogConstant.OUTPUT_TYPE_LOG, null, msg);
    }

    public static void a(String tag, Object msg) {
        printLog(MLogConstant.ASSERT, MLogConstant.OUTPUT_TYPE_LOG, tag, msg);
    }

    //JSON
    public static void json(int level, String jsonFormat) {
        printLog(level, MLogConstant.OUTPUT_TYPE_JSON, null, jsonFormat);
    }

    public static void json(int level, String tag, String jsonFormat) {
        printLog(level, MLogConstant.OUTPUT_TYPE_JSON, tag, jsonFormat);
    }

    //XML
    public static void xml(int level, String xml) {
        printLog(level, MLogConstant.OUTPUT_TYPE_XML, null, xml);
    }

    public static void xml(int level, String tag, String xml) {
        printLog(level, MLogConstant.OUTPUT_TYPE_XML, tag, xml);
    }

    private static void printLog(int level, int outType, String tagStr, Object... objects) {

        if (!sIsShowLog) {
            return;
        }

        if (level < sLogFilter) {
            //当过滤级别小于sLogFilter，不打印，起到过滤打印的作用
            return;
        }

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objects);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (outType) {
            case MLogConstant.OUTPUT_TYPE_LOG:
                MBaseLog.printLog(level, tag, headString + msg);
                break;
            case MLogConstant.OUTPUT_TYPE_JSON:
                MJsonLog.printLog(level, tag, msg, headString);
                break;
            case MLogConstant.OUTPUT_TYPE_XML:
                MXmlLog.printLog(level, tag, msg, headString);
                break;
        }

        if (sIsLog2File) {
            printFile(level, tag, new File(sLog2FilePath), sLog2FileName, msg);
        }
    }

    private static void printFile(int level, String tagStr, File targetDirectory, String fileName, Object objectMsg) {

        String[] contents = wrapperContent(STACK_TRACE_INDEX_5, tagStr, objectMsg);
        String tag = contents[0];
        String msg = contents[1];

        MFileLog.printLog(level, tag, targetDirectory, fileName , msg);
    }

    private static String[] wrapperContent(int stackTraceIndex, String tagStr, Object... objects) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        StackTraceElement targetElement = stackTrace[stackTraceIndex];
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + MLogConstant.SUFFIX;
        }

        if (className.contains("$")) {
            className = className.split("\\$")[0] + MLogConstant.SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String tag = tagStr;

        if (TextUtils.isEmpty(tag)) {

            tag = className;

            if (TextUtils.isEmpty(sGlobalTag) && TextUtils.isEmpty(tag)) {
                tag = TAG_DEFAULT;
            } else if (!TextUtils.isEmpty(sGlobalTag)) {
                tag = sGlobalTag;
            }
        }

        String msg = (objects == null) ? MLogConstant.NULL_TIPS : getObjectsString(objects);
        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n");
                } else {
                    stringBuilder.append("Param").append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? "null" : object.toString();
        }
    }
}
