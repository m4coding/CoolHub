package com.m4coding.coolhub.widgets.ratiolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.m4coding.coolhub.widgets.ratiolayout.delegate.RatioDatumMode;
import com.m4coding.coolhub.widgets.ratiolayout.delegate.RatioLayoutDelegate;
import com.m4coding.coolhub.widgets.ratiolayout.delegate.RatioMeasureDelegate;


public class RatioLinearLayout extends LinearLayout implements RatioMeasureDelegate {

    private RatioLayoutDelegate mRatioLayoutDelegate;

    public RatioLinearLayout(Context context) {
        this(context, null, 0);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public RatioLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mRatioLayoutDelegate = RatioLayoutDelegate.obtain(this, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(mRatioLayoutDelegate != null){
            mRatioLayoutDelegate.onMeasure(widthMeasureSpec,heightMeasureSpec);
            widthMeasureSpec = mRatioLayoutDelegate.getWidthMeasureSpec();
            heightMeasureSpec = mRatioLayoutDelegate.getHeightMeasureSpec();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setDelegateMeasuredDimension(int measuredWidth, int measuredHeight) {
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void setRatio(RatioDatumMode mode, float datumWidth, float datumHeight) {
        if(mRatioLayoutDelegate != null){
            mRatioLayoutDelegate.setRatio(mode,datumWidth,datumHeight);
        }
    }
}
