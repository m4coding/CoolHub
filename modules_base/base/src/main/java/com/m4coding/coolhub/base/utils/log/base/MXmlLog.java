package com.m4coding.coolhub.base.utils.log.base;

import android.text.TextUtils;
import android.util.Log;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @author mochangsheng
 * @version 1.0
 * @description xml打印实现
 * @created 2017/1/3
 * @changeRecord [修改记录] <br/>
 */

public class MXmlLog {

    public static void printLog(int type, String tag, String xml, String headString) {

        if (xml != null) {
            xml = MXmlLog.formatXML(xml);
        } else {
            xml = MLogConstant.NULL_TIPS;
        }

        String tmpMsg = "";
        switch (type) {
            case MLogConstant.VERBOSE:
                tmpMsg = MLogConstant.TYPE_VERBOSE_FORMAT;
                break;
            case MLogConstant.DEBUG:
                tmpMsg = MLogConstant.TYPE_DEBUG_FORMAT;
                break;
            case MLogConstant.INFO:
                tmpMsg = MLogConstant.TYPE_INFO_FORMAT;
                break;
            case MLogConstant.WARN:
                tmpMsg = MLogConstant.TYPE_WARN_FORMAT;
                break;
            case MLogConstant.ERROR:
                tmpMsg = MLogConstant.TYPE_ERROR_FORMAT;
                break;
            case MLogConstant.ASSERT:
                tmpMsg = MLogConstant.TYPE_ASSERT_FORMAT;
                break;
        }

        if (!TextUtils.isEmpty(tmpMsg)) {
            xml = headString + MLogConstant.LINE_SEPARATOR + tmpMsg
                    + MLogConstant.LINE_SEPARATOR + xml;
        } else {
            xml = headString + MLogConstant.LINE_SEPARATOR + xml;
        }

        MLogConstant.printLine(tag, true);
        String[] lines = xml.split(MLogConstant.LINE_SEPARATOR);
        for (String line : lines) {
            if (!MLogConstant.isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }
        MLogConstant.printLine(tag, false);
    }

    private static String formatXML(String inputXML) {
        try {
            Source xmlInput = new StreamSource(new StringReader(inputXML));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (Exception e) {
            e.printStackTrace();
            return inputXML;
        }
    }

}
