package com.m4coding.coolhub.widgets.roundlayout;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.m4coding.coolhub.widgets.ratiolayout.RatioLinearLayout;

/**
 * @author m4coding
 * @description 圆角和可以设置比例的LinearLayout
 */
public class RoundLinearLayout extends RatioLinearLayout implements IRoundDelegate, IRound {

    private RoundLayoutDelegate mRoundLayoutDelegate;

    public RoundLinearLayout(Context context) {
        super(context);
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mRoundLayoutDelegate = RoundLayoutDelegate.obtain(this, attrs);
    }

    public RoundLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRoundLayoutDelegate = RoundLayoutDelegate.obtain(this, attrs, defStyleAttr);
    }


    @Override
    public void setSolidColor(int colorId) {
        mRoundLayoutDelegate.setSolidColor(colorId);
    }

    @Override
    public void setRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius, int rightBottomRadius) {
        mRoundLayoutDelegate.setRadius(leftTopRadius, leftBottomRadius, rightTopRadius, rightBottomRadius);
    }

    @Override
    public void setStrokeColorAndWidth(int strokeWidth, int colorId) {
        mRoundLayoutDelegate.setStrokeColorAndWidth(strokeWidth, colorId);
    }
}
