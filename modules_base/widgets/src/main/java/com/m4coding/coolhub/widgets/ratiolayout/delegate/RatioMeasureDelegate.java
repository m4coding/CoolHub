package com.m4coding.coolhub.widgets.ratiolayout.delegate;


public interface RatioMeasureDelegate {

    /**
     * <p>This method must be called by {link #onMeasure(int, int)} to store the
     * measured width and measured height. Failing to do so will trigger an
     * exception at measurement time.</p>
     *
     * @param measuredWidth The measured width of this view.  May be a complex
     * bit mask as defined by {link #MEASURED_SIZE_MASK} and
     * {link #MEASURED_STATE_TOO_SMALL}.
     * @param measuredHeight The measured height of this view.  May be a complex
     * bit mask as defined by {link #MEASURED_SIZE_MASK} and
     * {link #MEASURED_STATE_TOO_SMALL}.
     */
    public void setDelegateMeasuredDimension(int measuredWidth, int measuredHeight);

    public void setRatio(RatioDatumMode mode,float datumWidth,float datumHeight);
}
