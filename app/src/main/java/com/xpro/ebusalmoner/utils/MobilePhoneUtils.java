package com.xpro.ebusalmoner.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by houyang on 2016/11/30.
 */
public class MobilePhoneUtils {

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 获取手机MAC地址
     *
     * @param context
     * @return
     */
    public static String getPhoneMacAdress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }


    /**
     * 手机wifi设备的Mac地址（一般跟手机mac地址一致）
     */
    public static String getWifiMacAdress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                byte[] addr = iF.getHardwareAddress();
                if (addr == null || addr.length == 0) {
                    continue;
                }
                StringBuilder buf = new StringBuilder();
                for (byte b : addr) {
                    buf.append(String.format("%02X:", b));
                }
                if (buf.length() > 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                String mac = buf.toString();
                Log.e("wifimac", "interfaceName=" + iF.getName() + ", mac=" + mac);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return macAddress;
    }


    /**
     * 获得附近所有路由器网络的mac地址
     *
     * @param context
     * @return
     */
    public static List<ScanResult> getAllWifiRouterInfo(Context context) {
        String wserviceName = Context.WIFI_SERVICE;
        WifiManager wm = (WifiManager) context.getSystemService(wserviceName);
        List<ScanResult> wifiList = wm.getScanResults();
        for (int i = 0; i < wifiList.size(); i++) {

            ScanResult result = wifiList.get(i);
            Log.d("dada", "bssid=" + result.BSSID + " ssid:" + result.SSID);
        }
        return wifiList;
    }


    /**
     * 获得已连接的路由器的mac地址
     *
     * @param context
     * @return
     */
    public static String getIsConnectionRouterInfo(Context context) {
        WifiManager mWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifi.isWifiEnabled()) {
            WifiInfo wifiInfo = mWifi.getConnectionInfo();
            String netName = wifiInfo.getSSID(); //获取被连接网络的名称
            String netMac = wifiInfo.getBSSID(); //获取被连接网络的mac地址
            String localMac = wifiInfo.getMacAddress();// 获得本机的MAC地址
            Log.d("MainActivity", "---netName:" + netName);   //---netName:HUAWEI MediaPad
            Log.d("MainActivity", "---netMac:" + netMac);     //---netMac:78:f5:fd:ae:b9:97
            Log.d("MainActivity", "---localMac:" + localMac); //---localMac:BC:76:70:9F:56:BD
            return wifiInfo.getBSSID();
        }
        return "error";
    }


    /**
     * 获得手机串号
     *
     * @param activity
     * @return
     */
    public static String getMiei(Activity activity) {
        Context context = activity.getWindow().getContext();
        TelephonyManager telephonemanage = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonemanage.getDeviceId();
    }


    /**
     * 获得手机号
     * 一般情况下都是null,因为移动运营商没有把手机号码放到sim卡信息中
     *
     * @param activity
     * @return
     */
    public static String getPhoneNum(Activity activity) {
        Context context = activity.getWindow().getContext();
        TelephonyManager telephonemanage = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonemanage.getLine1Number();
    }

    /**
     * 获得手机设备号
     *
     * @return
     */
    public static String getSerialNumber() {
        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serial;
    }




}
