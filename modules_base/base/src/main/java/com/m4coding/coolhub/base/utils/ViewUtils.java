package com.m4coding.coolhub.base.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.m4coding.coolhub.base.base.BaseApplication;


/**
 * @author mochangsheng
 * @description 处理View的工具类
 */

public class ViewUtils {

    /**
     *  处理软键盘的显示或隐藏
     * @param editText
     * @param isShow
     */
    public static void handleSoftInput(EditText editText, boolean isShow) {
        if (editText == null) {
            return;
        }

        InputMethodManager inputManager = (InputMethodManager) BaseApplication.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            //弹出软键盘
            inputManager.showSoftInput(editText, 0);
        } else {
            //收起软键盘
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
