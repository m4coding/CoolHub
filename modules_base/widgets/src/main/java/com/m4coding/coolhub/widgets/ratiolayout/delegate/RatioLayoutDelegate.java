package com.m4coding.coolhub.widgets.ratiolayout.delegate;

import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.m4coding.coolhub.widgets.R;


public final class RatioLayoutDelegate<TARGET extends View & RatioMeasureDelegate> {

    public static <TARGET extends View & RatioMeasureDelegate> RatioLayoutDelegate obtain(TARGET target, AttributeSet attrs) {
        return obtain(target,attrs,0);
    }

    public static <TARGET extends View & RatioMeasureDelegate> RatioLayoutDelegate obtain(TARGET target, AttributeSet attrs, int defStyleAttr) {
        return obtain(target,attrs,0,0);
    }

    @SuppressWarnings("unchecked")
    public static <TARGET extends View & RatioMeasureDelegate> RatioLayoutDelegate obtain(TARGET target, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        return new RatioLayoutDelegate(target,attrs,defStyleAttr,defStyleRes);
    }

    private final TARGET mRatioMeasureDelegate;

    private RatioDatumMode mRatioDatumMode;

    private float mDatumWidth = 0.0f;

    private float mDatumHeight = 0.0f;

    private int mWidthMeasureSpec, mHeightMeasureSpec;

    private RatioLayoutDelegate(TARGET target, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        this.mRatioMeasureDelegate = target;
        TypedArray typedArray = mRatioMeasureDelegate.getContext().obtainStyledAttributes(attrs, R.styleable.ViewSizeCalculate, defStyleAttr, defStyleRes);
        if(typedArray != null){
            int datum = typedArray.getInt(R.styleable.ViewSizeCalculate_datumRatio, 0);
            if(datum == 1){
                mRatioDatumMode = RatioDatumMode.DATUM_WIDTH;
            }else if(datum == 2){
                mRatioDatumMode = RatioDatumMode.DATUM_HEIGHT;
            }
            mDatumWidth = typedArray.getFloat(R.styleable.ViewSizeCalculate_widthRatio, mDatumWidth);
            mDatumHeight = typedArray.getFloat(R.styleable.ViewSizeCalculate_heightRatio, mDatumHeight);
            typedArray.recycle();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.mWidthMeasureSpec = widthMeasureSpec;
        this.mHeightMeasureSpec = heightMeasureSpec;
        if(mRatioDatumMode != null && mDatumWidth != 0 && mDatumHeight != 0){
            mRatioMeasureDelegate.setDelegateMeasuredDimension(View.getDefaultSize(0, mWidthMeasureSpec),
                    View.getDefaultSize(0, mHeightMeasureSpec));
            int measuredWidth = mRatioMeasureDelegate.getMeasuredWidth();
            int measuredHeight = mRatioMeasureDelegate.getMeasuredHeight();
            if(mRatioDatumMode == RatioDatumMode.DATUM_WIDTH){
                measuredHeight = (int) (measuredWidth / mDatumWidth * mDatumHeight);
            }else {
                measuredWidth = (int) (measuredHeight / mDatumHeight * mDatumWidth);
            }
            mWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY);
            mHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY);
        }
    }

    public int getWidthMeasureSpec() {
        return mWidthMeasureSpec;
    }

    public int getHeightMeasureSpec() {
        return mHeightMeasureSpec;
    }

    public void setRatio(RatioDatumMode mode,float datumWidth,float datumHeight){
        if(mode == null){
            throw new IllegalArgumentException("RatioDatumMode == null");
        }
        this.mRatioDatumMode = mode;
        this.mDatumWidth = datumWidth;
        this.mDatumHeight = datumHeight;
        this.mRatioMeasureDelegate.requestLayout();
    }
}
