package com.m4coding.coolhub.base.manager.statistics;

import android.content.Context;

import java.util.Map;

/**
 * @author m4coding
 * @description  统计接口统一
 */

public interface IStatistics {

    void init(boolean isDebug);

    /**
     * 统计时长
     * @param context
     */
    void onPause(Context context);
    void onResume(Context context);

    /**
     *  统计页面("MainScreen"为页面名称，可自定义)
     * @param pageName
     */
    void onPageStart(String pageName);
    void onPageEnd(String pageName);


    /**
     * 统计事件
     * @param context 当前宿主进程的ApplicationContext上下文。
     * @param eventID 为当前统计的事件ID
     * @param map  为当前事件的属性和取值（Key-Value键值对）
     */
    void onEvent(Context context, String eventID, Map<String, String> map);

}
