package com.m4coding.coolhub.base.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.m4coding.coolhub.base.base.BaseApplication;
import com.m4coding.coolhub.base.net.ClientConfiguration;

/**
 * @author mochangsheng
 * @description Gson配置助手
 */
public class GsonHelper {

    private static GsonHelper sGsonHelper;

    private Gson mGson;

    private GsonHelper() {

    }

    public static Gson getGsonInstance() {
        if (sGsonHelper == null) {
            synchronized (GsonHelper.class) {
                if (sGsonHelper == null) {
                    sGsonHelper = new GsonHelper();
                    GsonBuilder builder = new GsonBuilder();
                    ClientConfiguration.GsonConfiguration gsonConfiguration =
                            BaseApplication.getGlobalConfig().getGsonConfiguration();
                    if (gsonConfiguration != null) {
                        gsonConfiguration.configGson(BaseApplication.getContext(), builder);
                    }
                    sGsonHelper.mGson = builder.create();
                }
            }
        }

        return sGsonHelper.mGson;
    }
}
