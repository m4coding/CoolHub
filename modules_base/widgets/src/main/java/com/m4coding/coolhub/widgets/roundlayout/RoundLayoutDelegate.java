package com.m4coding.coolhub.widgets.roundlayout;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.m4coding.coolhub.widgets.R;


/**
 * @author m4coding
 * @description 圆角实现代理类
 */
public class RoundLayoutDelegate<TARGET extends View & IRoundDelegate> implements IRound  {

    private final TARGET mRatioMeasureDelegate;

    private GradientDrawable mDrawable;

    public static <TARGET extends View & IRoundDelegate> RoundLayoutDelegate obtain(TARGET target, AttributeSet attrs) {
        return obtain(target,attrs,0);
    }

    public static <TARGET extends View & IRoundDelegate> RoundLayoutDelegate obtain(TARGET target, AttributeSet attrs, int defStyleAttr) {
        return obtain(target,attrs,0,0);
    }

    @SuppressWarnings("unchecked")
    public static <TARGET extends View & IRoundDelegate> RoundLayoutDelegate obtain(TARGET target, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        return new RoundLayoutDelegate(target,attrs,defStyleAttr,defStyleRes);
    }

    private RoundLayoutDelegate(TARGET target, AttributeSet attrs, int defStyleAttr, int defStyleRes){
        this.mRatioMeasureDelegate = target;

        if (attrs != null) {
            TypedArray a = mRatioMeasureDelegate.getContext().obtainStyledAttributes(attrs, R.styleable.RoundAngle, defStyleAttr, defStyleRes);
            int solid = a.getColor(R.styleable.RoundAngle_bgSolidColor, Color.TRANSPARENT);
            int strokeColor = a.getColor(R.styleable.RoundAngle_bgStrokeColor, Color.TRANSPARENT);
            int radius = a.getDimensionPixelSize(R.styleable.RoundAngle_bgRadius, 0);
            int leftTopRadius = a.getDimensionPixelSize(R.styleable.RoundAngle_bgLeftTopRadius, 0);
            int leftBottomRadius = a.getDimensionPixelSize(R.styleable.RoundAngle_bgLeftBottomRadius, 0);
            int rightTopRadius = a.getDimensionPixelSize(R.styleable.RoundAngle_bgRightTopRadius, 0);
            int rightBottomRadius = a.getDimensionPixelSize(R.styleable.RoundAngle_bgRightBottomRadius, 0);
            int strokeWidth = a.getDimensionPixelSize(R.styleable.RoundAngle_bgStrokeWidth, 0);
            a.recycle();

            mDrawable = new GradientDrawable();
            mDrawable.setStroke(strokeWidth, strokeColor);
            mDrawable.setColor(solid);

            if (radius > 0) {
                mDrawable.setCornerRadius(radius);
            } else if (leftTopRadius > 0 || leftBottomRadius > 0 || rightTopRadius > 0 || rightBottomRadius > 0) {
                mDrawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
            }

            ViewCompat.setBackground(mRatioMeasureDelegate, mDrawable);
        }
    }

    @Override
    public void setSolidColor(int colorId) {
        mDrawable.setColor(colorId);
        ViewCompat.setBackground(mRatioMeasureDelegate, mDrawable);
    }

    @Override
    public void setRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius, int rightBottomRadius) {
        mDrawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        ViewCompat.setBackground(mRatioMeasureDelegate, mDrawable);
    }

    @Override
    public void setStrokeColorAndWidth(int strokeWidth, int colorId) {
        mDrawable.setStroke(strokeWidth, ContextCompat.getColor(mRatioMeasureDelegate.getContext(), colorId));
    }

}
