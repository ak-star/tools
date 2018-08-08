package com.lak.tools.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.lak.logger.CLog;

import java.io.IOException;

/**
 * Created by lawrence
 * <p>
 * 网络状态
 * https://blog.csdn.net/gdutxiaoxu/article/details/53008266
 * https://mp.weixin.qq.com/s/W9U7uzXRYcqCmgbX3gbbKg
 */

public class NetUtils {
    public static boolean isConnected(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) return info.isConnected();
        }
        return false;
    }

    /**
     * 判断当前网络是否是移动网络
     */
    public static boolean isMobileNet(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else { return false; }
    }

    /**
     * 判断当前网络是否是wifi网络
     */
    public static boolean isWifi(Context ctx) {
        ConnectivityManager manager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else { return false; }
    }

    /**
     * wifi是否打开
     */
    public static boolean isWifiEnabled(Context ctx) {
        ConnectivityManager mgrConn = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null
                && mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
                || mgrTel.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
    }

    /**
     * 获取当前的网络状态
     *
     * @return 没有网络-0：WIFI网络1：4G网络-4：3G网络-3：2G网络-2
     */
    public static int getConnectedType(Context ctx) {
        int result = 0; //获取手机所有连接管理对象
        ConnectivityManager manager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo(); // 获取NetworkInfo对象
        if (networkInfo == null) return result; // NetworkInfo对象为空 则代表没有网络
        int nType = networkInfo.getType();  // 否则 NetworkInfo对象不为空 则获取该networkInfo的类型
        if (nType == ConnectivityManager.TYPE_WIFI) {
            result = 1; //WIFI
        } else if (nType == ConnectivityManager.TYPE_MOBILE) {
            int nSubType = networkInfo.getSubtype();
            TelephonyManager telephonyManager =
                    (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            if (nSubType == TelephonyManager.NETWORK_TYPE_LTE
                    && !telephonyManager.isNetworkRoaming()) {
                result = 4;
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
                    || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    && !telephonyManager.isNetworkRoaming()) {
                result = 3; //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
            } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
                    || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
                    || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
                    && !telephonyManager.isNetworkRoaming()) {
                result = 2; //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
            } else {
                result = 2;
            }
        }
        return result;
    }

    /**
     * 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    public static final boolean ping() {
        try {
            String sIP = "www.baidu.com";   // ping 的地址，可以换成任何一种可靠的外网
            Process process = Runtime.getRuntime()
                    .exec("ping -c 3 -w 100 " + sIP);   // ping网址3次
            int status = process.waitFor(); // ping的状态
            CLog.d("ping result=" + (status == 0));
            return status == 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
