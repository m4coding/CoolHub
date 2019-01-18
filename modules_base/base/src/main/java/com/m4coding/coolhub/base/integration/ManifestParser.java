package com.m4coding.coolhub.base.integration;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Manifest解析
 */
public final class ManifestParser {

    private static final String MODULE_VALUE = "ConfigModule";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    public List<IConfigModule> parse() {
        List<IConfigModule> modules = new ArrayList<IConfigModule>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        modules.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse IConfigModule", e);
        }

        return modules;
    }

    private static IConfigModule parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find IConfigModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate IConfigModule implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate IConfigModule implementation for " + clazz, e);
        }

        if (!(module instanceof IConfigModule)) {
            throw new RuntimeException("Expected instanceof IConfigModule, but found: " + module);
        }
        return (IConfigModule) module;
    }
}