package com.m4coding.coolhub.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * BorderTextView
 *
 * 自定义TextView 实现填充色、圆角、文字变色、边框、边框颜色、背景图等功能
 *
 *  textSolidColor                                 背景填充色
    textStrokeColor                                边框颜色
    textRadius                                     textView弧度
    textLeftTopRadius                              textView左上角弧度
    textLeftBottomRadius                           textView左下角弧度
    textRightTopRadius                             textView右上角弧度
    textRightBottomRadius                          textView右下角弧度
    textStrokeWidth                                边框宽度
    textDrawable                                   图片
    textNormalTextColor                            正常状态文字颜色
    textSelectedTextColor                          按下状态文字颜色
 */
public class BorderTextView extends AppCompatTextView {

    private GradientDrawable drawable;

    public BorderTextView(Context context) {
        super(context);

    }

    public BorderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttributeSet(context, attrs);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributeSet(context, attrs);
    }


    private void setAttributeSet(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.borderTextView);
        int solid = a.getColor(R.styleable.borderTextView_textSolidColor, Color.TRANSPARENT);
        int strokeColor = a.getColor(R.styleable.borderTextView_textStrokeColor, Color.TRANSPARENT);
        int radius = a.getDimensionPixelSize(R.styleable.borderTextView_textRadius, 0);
        int leftTopRadius = a.getDimensionPixelSize(R.styleable.borderTextView_textLeftTopRadius, 0);
        int leftBottomRadius = a.getDimensionPixelSize(R.styleable.borderTextView_textLeftBottomRadius, 0);
        int rightTopRadius = a.getDimensionPixelSize(R.styleable.borderTextView_textRightTopRadius, 0);
        int rightBottomRadius = a.getDimensionPixelSize(R.styleable.borderTextView_textRightBottomRadius, 0);
        int strokeWidth = a.getDimensionPixelSize(R.styleable.borderTextView_textStrokeWidth, 0);
        Drawable textDrawable = a.getDrawable(R.styleable.borderTextView_textDrawable);
        int normalTextColor = a.getColor(R.styleable.borderTextView_textNormalTextColor, Color.TRANSPARENT);
        int selectedTextColor = a.getColor(R.styleable.borderTextView_textSelectedTextColor, Color.TRANSPARENT);


        a.recycle();

        drawable = new GradientDrawable();
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setColor(solid);

        if (radius > 0) {
            drawable.setCornerRadius(radius);
        } else if (leftTopRadius > 0 || leftBottomRadius > 0 || rightTopRadius > 0 || rightBottomRadius > 0) {
            drawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        }

        setBackgroundDrawable(drawable);


        if (textDrawable != null) {
            BitmapDrawable bd = (BitmapDrawable) textDrawable;
            ImageSpan imageSpan = new ImageSpan(getContext(), bd.getBitmap());

            String text = "[icon]";
            SpannableString ss = new SpannableString("[icon]");

            ss.setSpan(imageSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }


        if (normalTextColor != 0 && selectedTextColor != 0) {
            //设置state_selected状态时，和正常状态时文字的颜色
            setClickable(true);
            int[][] states = new int[3][1];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{android.R.attr.state_pressed};
            states[2] = new int[]{};
            ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
            setTextColor(textColorSelect);
        }else{
            setClickable(false);
        }
    }

    /**
     * 设置填充图片
     *
     * @param drawableId  drawable id
     */
    public void setTextDrawable(int drawableId) {
        if (drawableId != 0) {
            Drawable textdrwable = getResources().getDrawable(drawableId);
            BitmapDrawable bd = (BitmapDrawable) textdrwable;
            ImageSpan imageSpan = new ImageSpan(getContext(), bd.getBitmap());

            String text = "[icon]";
            SpannableString ss = new SpannableString("[icon]");

            ss.setSpan(imageSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(ss);
        }
    }

    /**
     *
     * 设置填充颜色
     *
     * @param colorId   颜色id
     */
    public void setSolidColor(int colorId) {
        drawable.setColor(colorId);
        setBackgroundDrawable(drawable);
    }

    /**
     * 设置textView选中的背景颜色
     *
     * @param normalColor     正常状态颜色
     * @param selectedColor   按下状态颜色
     */
    public void setSelectedSolidColor(@ColorRes int normalColor, @ColorRes int selectedColor) {
        if (normalColor != 0 && selectedColor != 0) {
            //设置state_selected状态时，和正常状态时文字的颜色
            final int finalSelectedColor = ContextCompat.getColor(getContext(), selectedColor);
            final int finalNormalColor = ContextCompat.getColor(getContext(), normalColor);
            drawable.setColor(finalNormalColor);
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            drawable.setColor(finalSelectedColor);
                            break;
                        case MotionEvent.ACTION_UP:
                            drawable.setColor(finalNormalColor);
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }else{
            setClickable(false);
        }

    }

    /**
     * 设置圆角弧度
     *
     * @param leftTopRadius         左上角弧度
     * @param leftBottomRadius      左下角弧度
     * @param rightTopRadius        右上角弧度
     * @param rightBottomRadius     右下角弧度
     */
    public void setRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius, int rightBottomRadius) {
        drawable.setCornerRadii(new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius});
        setBackgroundDrawable(drawable);
    }

    /**
     * 设置边框颜色及宽度
     *
     * @param strokeWidth      边框宽度
     * @param colorId          边框颜色 id
     */
    public void setStrokeColorAndWidth(int strokeWidth,int colorId){
        drawable.setStroke(strokeWidth, getResources().getColor(colorId));
    }


    public void setSelectedTextColorRes(@ColorRes int normalTextColorRes,@ColorRes int selectedTextColorRes) {
        int normalTextColor = getResources().getColor(normalTextColorRes);
        int selectedTextColor = getResources().getColor(selectedTextColorRes);
        setSelectedTextColor(normalTextColor, selectedTextColor);
    }

    /**
     * 设置textView选中状态颜色
     *
     * @param normalTextColor     正常状态颜色
     * @param selectedTextColor   按下状态颜色
     */
    public void setSelectedTextColor(@ColorInt int normalTextColor, @ColorInt int selectedTextColor) {
        if (normalTextColor != 0 && selectedTextColor != 0) {
            //设置state_selected状态时，和正常状态时文字的颜色
            setClickable(true);
            int[][] states = new int[3][1];
            states[0] = new int[]{android.R.attr.state_selected};
            states[1] = new int[]{android.R.attr.state_pressed};
            states[2] = new int[]{};
            ColorStateList textColorSelect = new ColorStateList(states, new int[]{selectedTextColor, selectedTextColor, normalTextColor});
            setTextColor(textColorSelect);
        }else{
            setClickable(false);
        }
    }

}
