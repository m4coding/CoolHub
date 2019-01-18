package com.m4coding.coolhub.base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;


/**
 * @author mochangsheng
 * @description 基本Activity
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public static final String KEY_IS_NOT_ADD_ACTIVITY_LIST = "key_is_not_add_activity_list";//是否添加AppManager管理列表中
    protected BaseApplication mApplication;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initData();
    }

    protected void initialize() {
        mApplication = (BaseApplication) getApplication();
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册到事件主线
        }
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        mApplication = null;
    }

    /**
     * 是否使用eventBus,默认为不使用(false)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }

    protected abstract void initView();

    protected abstract void initData();

}
