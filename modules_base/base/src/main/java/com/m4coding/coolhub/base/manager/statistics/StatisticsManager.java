package com.m4coding.coolhub.base.manager.statistics;

import android.content.Context;

import java.util.Map;

/**
 * @author m4coding
 * @description 统计相关管理类
 */

public class StatisticsManager implements IStatistics{

    private static StatisticsManager sManager;
    private IStatistics mStatistics;

    private StatisticsManager() {
        //mStatistics = new UmengStatistics();
    }

    public static StatisticsManager getInstance() {
        if (sManager == null) {
            synchronized (StatisticsManager.class) {
                if (sManager == null) {
                    sManager = new StatisticsManager();
                }
            }
        }

        return sManager;
    }

    public void init(boolean isDebug) {
        mStatistics.init(isDebug);
    }

    @Override
    public void onPause(Context context) {
        mStatistics.onPause(context);
    }

    @Override
    public void onResume(Context context) {
        mStatistics.onResume(context);
    }

    @Override
    public void onPageStart(String pageName) {
        mStatistics.onPageStart(pageName);
    }

    @Override
    public void onPageEnd(String pageName) {
        mStatistics.onPageEnd(pageName);
    }

    @Override
    public void onEvent(Context context, String eventID, Map<String, String> map) {
        mStatistics.onEvent(context, eventID, map);
    }
}
