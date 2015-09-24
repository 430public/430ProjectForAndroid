package com.projectforandroid.utils.networkutils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import org.apache.http.conn.util.InetAddressUtils;

/**
 * Created by 大灯泡 on 2015/9/20.
 * 网络工具类
 */
public class NetUtils {

    /**
     * 网络检测
     *
     * @param context 上下文
     * @return false:无网络,true:有网络
     */
    public static boolean isOnline(Context context) {
        boolean isOnline = false;
        final ConnectivityManager cm =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            isOnline = networkInfo.isAvailable();
        }
        // String netType = "当前网络类型为：" + networkInfo.getTypeName();
        return isOnline;
    }

    /**
     * 获取网路连接类型
     *
     * @param context 上下文
     * @return 网络类型
     * 需要添加权限<uses-permission android:name="android.permission.INTERNET"/>
     * 需要添加权限<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static String getNetType(Context context) {
        ConnectivityManager conn =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        String result = null;
        if (info != null && info.isAvailable()) {
            if (info.isConnected()) {
                int type = info.getType();
                String typeName = info.getTypeName();
                switch (type) {
                    case ConnectivityManager.TYPE_BLUETOOTH:
                        result = "蓝牙连接   :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_DUMMY:
                        result = "虚拟数据连接    :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_ETHERNET:
                        result = "以太网数据连接    :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_MOBILE:
                        result = "移动数据连接   : " + typeName;
                        break;
                    case ConnectivityManager.TYPE_MOBILE_DUN:
                        result = "网络桥接 :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_MOBILE_HIPRI:
                        result = "高优先级的移动数据连接 :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_MOBILE_MMS:
                        result = "运营商的多媒体消息服务  : " + typeName;
                        break;
                    case ConnectivityManager.TYPE_MOBILE_SUPL:
                        result = "平面定位特定移动数据连接  :  " + typeName;
                        break;
                    case ConnectivityManager.TYPE_WIFI:
                        result = "Wifi数据连接   : " + typeName;
                        break;
                    case ConnectivityManager.TYPE_WIMAX:
                        result = "全球微波互联   : " + typeName;
                        break;
                    default:
                        break;
                }
            }
        }
        return result;
    }

    /**
     * 获取有网线下的Ip地址
     * 需要添加权限:<uses-permission android:name="android.permission.INTERNET" />
     *
     * @param context 上下文
     * @return IP地址
     */
    public static String getWXLocalIpAddress(Context context) {
        String ipv4 = "0.0.0.0";
        try {
            boolean boo = true;
            List<NetworkInterface> nilist =
                Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                List<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(
                        ipv4 = address.getHostAddress())) {
                        boo = false;
                        break;
                    }
                    if (!boo) {
                        break;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("WangLuo", ex.toString());
        }
        return ipv4;
    }

    /**
     * 获取Wifi下的Ip地址
     * 需要添加权限: <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     *
     * @param context 上下文
     * @return IP地址
     */
    public static String getWifiLocalIpAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        int ipAddress = info.getIpAddress();
        return intToIp(ipAddress);
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + ((i >> 24)
            & 0xFF);
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
            "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
