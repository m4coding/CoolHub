package com.m4coding.coolhub.widgets.roundlayout;

/**
 * @author m4coding
 * @description 该类的主要功能描述
 */
public interface IRound {

    /**
     * 设置填充颜色
     *
     * @param colorId 颜色id
     */
    public void setSolidColor(int colorId);

    /**
     * 设置圆角弧度
     *
     * @param leftTopRadius     左上角弧度
     * @param leftBottomRadius  左下角弧度
     * @param rightTopRadius    右上角弧度
     * @param rightBottomRadius 右下角弧度
     */
    public void setRadius(int leftTopRadius, int leftBottomRadius, int rightTopRadius, int rightBottomRadius);

    /**
     * 设置边框颜色及宽度
     *
     * @param strokeWidth 边框宽度
     * @param colorId     边框颜色 id
     */
    public void setStrokeColorAndWidth(int strokeWidth, int colorId);
}
