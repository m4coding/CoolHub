package com.m4coding.coolhub.business.base.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.m4coding.business_base.R;
import com.m4coding.coolhub.business.base.widgets.CustomSheetDialog;

/**
 * @author mochangsheng
 * @description Dialog工具类
 */

public class DialogUtils {

    public static CustomSheetDialog getSheetDialog(Context context, String[] stringItems, final OnOperItemClickL onOperItemClickL) {
        final CustomSheetDialog dialog = new CustomSheetDialog(context, stringItems, null);
        dialog.isTitleShow(false)
                //.layoutAnimation(null)//取消layout动画
                .lvBgColor(ContextCompat.getColor(context, R.color.white))
                .itemTextColor(ContextCompat.getColor(context, R.color.primary_text))
                .cancelText(ContextCompat.getColor(context, R.color.accent))
                .heightScale(0.5f);

        if (onOperItemClickL != null) {
            dialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onOperItemClickL.onOperItemClick(parent, view, position, id);
                    dialog.dismiss();
                }
            });
        }

        return dialog;
    }
}
