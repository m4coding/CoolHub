package com.m4coding.coolhub.business.base.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.m4coding.business_base.R;

public class BusinessProgressDialog extends RxDialog<BusinessProgressDialog> {


    /**
     * 显示加载弹窗
     * @param context
     * @return
     */
    public static BusinessProgressDialog show(Context context) {
        BusinessProgressDialog dialog = new BusinessProgressDialog(context);
        dialog.show();

        return dialog;
    }

    private BusinessProgressDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        heightScale(0.15f);

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        /*inflate.setBackground(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5));*/


        //采用attachToRoot为false的方式，可以使inflase view中的xml设置layout_height和layout_width起到作用
        return LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading,
                mLlControlHeight, false);
    }

    @Override
    public void setUiBeforShow() {

    }
}
