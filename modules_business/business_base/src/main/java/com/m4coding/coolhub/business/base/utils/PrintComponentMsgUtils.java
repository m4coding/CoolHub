package com.m4coding.coolhub.business.base.utils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.m4coding.coolhub.base.utils.log.MLog;

/**
 * @author mochangsheng
 * @description
 */
public class PrintComponentMsgUtils {

    private static final boolean sIsShow = true;

    public static void showResult(CC cc, CCResult result) {
        if (!sIsShow) {
            return;
        }
        String text = "result:\n" + JsonFormat.format(result.toString());
        text += "\n\n---------------------\n\n";
        text += "cc:\n" + JsonFormat.format(cc.toString());
        MLog.e(text);
    }
}
