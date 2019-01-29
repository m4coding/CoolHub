package com.m4coding.coolhub.business.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import com.m4coding.coolhub.base.utils.DeviceUtils;
import com.m4coding.coolhub.base.utils.ShellUtils;
import com.m4coding.coolhub.base.utils.channel.ChannelUtil;
import java.util.HashMap;
import java.util.UUID;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 设备参数配置管理
 */
public class PhoneInformationManager {

    private static PhoneInformationManager sInstance;

    public static PhoneInformationManager getInstance() {
        if (sInstance == null) {
            synchronized (PhoneInformationManager.class) {
                if (sInstance == null) {
                    sInstance = new PhoneInformationManager();
                }
            }
        }
        return sInstance;
    }

    public static String KEY_SCREEN = "screen"; //屏幕大小
    public static String KEY_UDID = "udid"; //唯一设备id
    public static String KEY_OS = "os"; //系统类型
    public static String KEY_OS_VERSION = "os"; //系统版本
    public static String KEY_NET_TYPE = "net_type"; //网络类型
    public static String KEY_FROM = "from"; //渠道
    public static String KEY_APP_VERSION = "app_version";//app版本号
    public static String KEY_APP_VERSION_NAME = "app_version_name";//app版本名


    private static String NET_TYPE_WIFI = "wifi"; //网络类型--wifi
    private static String NET_TYPE_CELLULAR = "cellular"; //网络类型--移动电话

    private int mScreenWidth;
    private int mScreenHeight;


    private HashMap<String, String> mMap;

    private PhoneInformationManager() {
        mMap = new HashMap<>();
    }

    @SuppressLint("CheckResult")
    public Observable<Context> init(Context context) {
        return Observable.just(context).subscribeOn(Schedulers.newThread())
                .map(new Function<Context, Context>() {
                    @Override
                    public Context apply(Context context) throws Exception {
                        setupUdid(context);
                        setupAppVersion(context);
                        setupFrom(context);
                        setupNetType(context);
                        setupOs(context);
                        setupScreen(context);
                        return context;
                    }
                });
    }


    //配置App版本号
    private void setupAppVersion(Context context) {
       mMap.put(KEY_APP_VERSION, DeviceUtils.getVersionCode(context) + "");
       mMap.put(KEY_APP_VERSION_NAME, DeviceUtils.getVersionName(context) + "");
    }


    //渠道信息
    private void setupFrom(Context context) {
        mMap.put(KEY_FROM, ChannelUtil.getChannel(context, "Default"));
    }

    //配置系统版本
    private void setupOs(Context context) {
        mMap.put(KEY_OS, "android");
        mMap.put(KEY_OS_VERSION, Build.VERSION.RELEASE);
    }

    //配置网络类型
    private void setupNetType(Context context) {
        int type = DeviceUtils.getNetworkType(context);
        String netType = "";
        switch (type) {
            case DeviceUtils.NETTYPE_WIFI:
                netType = NET_TYPE_WIFI;
                break;
            case DeviceUtils.NETTYPE_CMNET:
                netType = NET_TYPE_CELLULAR;
                break;
        }
        mMap.put(KEY_NET_TYPE, netType);
    }

    //屏幕宽、高
    private void setupScreen(Context context) {
        //调整屏幕分辨率的获取，避免常规方式获取有问题（有虚拟导航栏的手机就会获取不准确）
        try {
            ShellUtils.CommandResult commandResult = ShellUtils.execCmd("wm size", false);
            if (commandResult.result == 0) {
                String[] strs = commandResult.successMsg.split(" ");
                mMap.put(KEY_SCREEN, strs[strs.length - 1]);
                String[] temp = strs[strs.length - 1].split("x");
                mScreenWidth = Integer.parseInt(temp[0]);
                mScreenHeight = Integer.parseInt(temp[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();

            DisplayMetrics displayMetrics = DeviceUtils.getDisplayMetrics(context);
            if (displayMetrics != null) {
                mScreenWidth = displayMetrics.widthPixels;
                mScreenHeight = displayMetrics.heightPixels;
            }
            mMap.put(KEY_SCREEN, mScreenWidth + "x" + mScreenHeight);
        }
    }

    //配置设备唯一id
    private void setupUdid(Context context) {
        mMap.put(KEY_UDID, getPseudoID());
    }

    //获取设备唯一id
    private static String getPseudoID(){
        String serial = null;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 位

        try{
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch(Exception exception){
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 根据key获取对应的value
     * @param key
     * @return
     */
    public String getValue(String key) {
        return mMap.get(key);
    }

}
