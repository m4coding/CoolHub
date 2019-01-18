package com.m4coding.coolhub.widgets.roundlayout;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author mochangsheng
 * @description 该类的主要功能描述
 */

public class RoundEditText extends android.support.v7.widget.AppCompatEditText implements IRoundDelegate, IRound {

    private RoundLayoutDelegate mRoundLayoutDelegate;

    public RoundEditText(Context context) {
        super(context);
    }

    public RoundEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRoundLayoutDelegate = RoundLayoutDelegate.obtain(this, attrs);
    }

    public RoundEditText(Context context, AttributeSet attrs, int defStyleAttr) {
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
