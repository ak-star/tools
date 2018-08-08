package com.lak.tools.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class MetaUtils {

    /**
     * 获取manifest中设置的参数
     *
     * @param name         key
     * @param defaultValue 默认值
     */
    public static <T> T getMetaData(Context ctx, String name, T defaultValue) {
        PackageManager packageManager = ctx.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null
                    && applicationInfo.metaData != null
                    && applicationInfo.metaData.containsKey(name)) {
                return (T) applicationInfo.metaData.get(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * @return manifest中是否含有 name
     */
    public static boolean hasName(Context ctx, String name) {
        PackageManager packageManager = ctx.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(
                    ctx.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null
                    && applicationInfo.metaData != null) {
                return applicationInfo.metaData.containsKey(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
