package com.m4coding.coolhub.base.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * @author m4coding
 * @description 基本Fragment
 */

public abstract class BaseFragment extends RxFragment {

    protected BaseActivity mActivity;
    protected View mRootView;

    protected boolean mIsVisible = false;//当前Fragment是否可见
    protected boolean mIsInitView = false;//是否与View建立起映射关系
    protected boolean mIsFirstLoad = true;//是否是第一次加载数据

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView(inflater,container);
        }
        mIsInitView = true;
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        initData();
        lazyLoadData();
    }

    protected void initialize() {
        mActivity = (BaseActivity) getActivity();
        if (useEventBus()) {
            EventBus.getDefault().register(this);//注册到事件主线
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mIsInitView = false;
        mIsFirstLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        mActivity = null;
        mRootView = null;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoadData();
        } else {
            mIsVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (!mIsFirstLoad || !mIsVisible || !mIsInitView) {
            return;
        }

        initLazyData();
        mIsFirstLoad = false;
    }

    /**
     * 是否使用eventBus,默认为不使用(false)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }


    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initData();

    /**
     * (ViewPager形式的才会回调 （setUserVisibleHint影响到的）)
     * 加载要显示的数据(懒加载，即Fragment可见后才进行initLazyData (第一次，只执行一次))
     */
    protected  void initLazyData() {

    }

}
