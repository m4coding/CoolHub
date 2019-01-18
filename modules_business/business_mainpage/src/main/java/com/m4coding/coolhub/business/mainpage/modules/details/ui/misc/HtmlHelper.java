

package com.m4coding.coolhub.business.mainpage.modules.details.ui.misc;

import android.support.annotation.NonNull;


class HtmlHelper {

    static String generateMdHtml(@NonNull String mdSource,
                                 boolean isDark, @NonNull String backgroundColor,
                                 @NonNull String accentColor, boolean wrapCode) {
        String skin = isDark ? "markdown_dark.css" : "markdown_white.css";
        return generateMdHtml(mdSource, skin, backgroundColor, accentColor, wrapCode);
    }

    private static String generateMdHtml(@NonNull String mdSource, String skin,
                                         @NonNull String backgroundColor,
                                         @NonNull String accentColor, boolean wrapCode) {
        return "<html>\n" +
                    "<head>\n" +
                        "<meta charset=\"utf-8\" />\n" +
                        "<title>MD View</title>\n" +
                        "<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\"/>" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"./" + skin + "\">\n" +
                        "<style>" +
                            "body{background: " + backgroundColor + ";}" +
                            "a {color:" + accentColor + " !important;}" +
                            ".highlight pre, pre {" +
                            " word-wrap: " + (wrapCode ? "break-word" : "normal") + "; " +
                            " white-space: " + (wrapCode ? "pre-wrap" : "pre") + "; " +
                            "}" +
                        "</style>" +
                "\n<script src=\"file:///android_asset/CodeScrollHandle.js\"></script>" +
                    "<script type=\"text/javascript\">\n" +
                "    function toastClick(){\n" +
                "        window.AndroidToast.show('from js');\n" +
                "    }\n" +
                "    function showAlert(){\n" +
                "        var str=window.AndroidMessage.getMsg();\n" +
                "        console.log(str);\n" +
                "    }\n" +
                "    function callFromJava(str){\n" +
                "        alert(str);\n" +
                "    }\n" +
                "</script>" +
                    "</head>\n" +
                    "<body>\n" +
                        mdSource +
                    "</body>\n" +
                "</html>";
    }

}
