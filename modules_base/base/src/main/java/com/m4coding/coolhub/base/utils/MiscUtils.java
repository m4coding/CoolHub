package com.m4coding.coolhub.base.utils;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.m4coding.coolhub.base.R;

public class MiscUtils {

    public static void toCopy(@NonNull Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
        }
    }

    /**
     * 打开浏览器
     *
     * @param targetUrl 外部浏览器打开的地址
     */
    public static void openBrowser(@NonNull Context context, String targetUrl) {

        if (TextUtils.isEmpty(targetUrl) || targetUrl.startsWith("file://")) {
            ToastUtils.showShort(R.string.can_not_open_in_browser);
            return;
        }

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(targetUrl);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 调用系统分享
     * @param context
     * @param text
     */
    public static void shareText(@NonNull Context context, @NonNull String text) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain"); //通过mine匹配ntentFilter
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try{
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_title))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }catch (ActivityNotFoundException e){
            ToastUtils.showShort(R.string.no_share_clients);
        }
    }
}
