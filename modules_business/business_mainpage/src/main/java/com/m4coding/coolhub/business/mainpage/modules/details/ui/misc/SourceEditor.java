package com.m4coding.coolhub.business.mainpage.modules.details.ui.misc;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.m4coding.coolhub.api.datasource.bean.BlobBean;
import com.m4coding.coolhub.api.datasource.bean.FileBean;
import com.m4coding.coolhub.base.utils.EncodeUtils;
import com.m4coding.coolhub.base.utils.MiscUtils;
import com.m4coding.coolhub.base.utils.StringUtils;
import com.m4coding.coolhub.business.mainpage.modules.details.ui.activity.RepositoryDetailsActivity;

import java.io.UnsupportedEncodingException;


/**
 * 代码显示
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("SetJavaScriptEnabled")
public class SourceEditor {

    private final WebView mWebView;

    private boolean mWrap;

    private String mName;

    private String mContent;

    private boolean mEncoded;

    private boolean mMarkdown;

    //是否是MarkDown预览模式
    private boolean mIsMarkdownViewer;

    /**
     * Create source editor using given web mWebView
     *
     * @param webView
     */
    @SuppressLint("AddJavascriptInterface")
    public SourceEditor(final WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        try {
            int version = android.os.Build.VERSION.SDK_INT;
            if (version >= 11) {
                // 这个方法在API level 11 以上才可以调用，不然会发生异常
                settings.setDisplayZoomControls(false);
            }
        } catch (NumberFormatException e) {

        }
        settings.setUseWideViewPort(true);
        webView.addJavascriptInterface(SourceEditor.this, "SourceEditor");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            webView.setWebViewClient(new WebClientN());
        } else {
            webView.setWebViewClient(new WebClient());
        }
        this.mWebView = webView;

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }*/
    }


    /**
     * 回调js返回的信息
     */
   @JavascriptInterface
   public void onMessageByJs(String json) {

       if (null == mWebView) {
           return;
       }

       try {
           JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
           int type = jsonObject.get("type").getAsInt();
           switch (type) {
               case JsMsgBean.TYPE_IS_CODE_H_SCROLL:
                   Context context = mWebView.getContext();
                   if (context instanceof RepositoryDetailsActivity) {
                       RepositoryDetailsActivity repositoryDetailsActivity = (RepositoryDetailsActivity) context;
                       repositoryDetailsActivity.setViewPagerScroll(jsonObject.get("msg").getAsBoolean());
                   }
                   break;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }

   }

    /**
     * @return Name
     */
    @JavascriptInterface
    public String getName() {
        return mName;
    }

    /**
     * 设置Markdown查看预览模式
     * @param isMarkdownViewer
     */
    public void setMarkdownViewer(boolean isMarkdownViewer) {
        this.mIsMarkdownViewer = isMarkdownViewer;
    }

    /**
     * @return mContent
     */
    @JavascriptInterface
    public String getRawContent() {
        return mContent;
    }

    /**
     * @return mContent
     */
    @JavascriptInterface
    public String getContent() {
        if (mEncoded)
            try {
                return new String(EncodeUtils.base64Decode(mContent),
                        EncodeUtils.CHARSET_UTF8);
            } catch (UnsupportedEncodingException e) {
                return getRawContent();
            }
        else
            return getRawContent();
    }

    /**
     * @return mWrap
     */
    @JavascriptInterface
    public boolean getWrap() {
        return mWrap;
    }

    /**
     * @return mMarkdown
     */
    @JavascriptInterface
    public boolean isMarkdown() {
        return mMarkdown;
    }

    /**
     * Set whether lines should mWrap
     *
     * @param wrap
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor setWrap(final boolean wrap) {
        this.mWrap = wrap;
        loadSource();
        return this;
    }

    /**
     * Sets whether the mContent is a mMarkdown file
     *
     * @param markdown
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor setMarkdown(final boolean markdown) {
        this.mMarkdown = markdown;
        return this;
    }

    /**
     * Bind mContent to current {@link WebView}
     *
     * @param name
     * @param content
     * @param encoded
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor setSource(final String name, final String content,
                                  final boolean encoded) {
        this.mName = name;
        this.mContent = content;
        this.mEncoded = encoded;
        loadSource();

        return this;
    }

    @JavascriptInterface
    private void loadSource() {
        if (mName != null && mContent != null) {
            if (mIsMarkdownViewer) {
                String mdHtml = HtmlHelper.generateMdHtml(mContent,false,
                        "","",false);
                mWebView.loadDataWithBaseURL("file:///android_asset/markdown-preview/",
                        mdHtml, "text/html", "utf-8", null);
            } else {
                mWebView.loadUrl("file:///android_asset/source-editor.html");
            }
        }
    }

    /**
     * Bind blob mContent to current {@link WebView}
     *
     * @param name
     * @param blob
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor setSource(final String name, final BlobBean blob) {

        String content = blob.getContent();
        if (content == null)
            content = "";
        boolean encoded = !TextUtils.isEmpty(content)
                && EncodeUtils.ENCODING_BASE64.equals(blob.getEncoding());
        return setSource(name, content, encoded);
    }

    /**
     * Toggle line mWrap
     *
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor toggleWrap() {
        return setWrap(!mWrap);
    }

    /**
     * Toggle mMarkdown file rendering
     *
     * @return this editor
     */
    @JavascriptInterface
    public SourceEditor toggleMarkdown() {
        return setMarkdown(!mMarkdown);
    }



    private class WebClientN extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            startActivity(request.getUrl());
            return true;
        }
    }

    private class WebClient extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            startActivity(Uri.parse(url));
            return true;
        }
    }

    private void startActivity(Uri uri){
        if(uri == null || StringUtils.isBlank(uri.toString())) return;

        MiscUtils.openBrowser(mWebView.getContext(), uri.toString());
    }
}
