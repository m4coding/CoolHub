package com.m4coding.coolhub.widgets;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */

public class CustomViewPager extends ViewPager {

    private boolean mIsCanScroll = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.mIsCanScroll = isCanScroll;
    }

    public boolean isCanScroll() {
        return mIsCanScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mIsCanScroll && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.mIsCanScroll && super.onInterceptTouchEvent(event);
    }

}
