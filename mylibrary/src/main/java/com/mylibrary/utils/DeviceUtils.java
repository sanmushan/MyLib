package com.mylibrary.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class DeviceUtils {

    public static final int NETWORK_CLASS_UNKNOWN = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_CLASS_2_G = 2;
    public static final int NETWORK_CLASS_3_G = 3;
    public static final int NETWORK_CLASS_4_G = 4;

    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getLocalIPAddress() {
        try {
            Enumeration en;
            Enumeration enumIpAddr;
            
            for (en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();

                for (enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                        return inetAddress.getHostAddress().toString();
                }
            }
        } catch (SocketException ex) {
            return "0.0.0.0";
        }
        return "0.0.0.0";
    }

    public static String getExternalStorageDirectory() {
        Map map = System.getenv();
        String[] values = new String[map.values().size()];
        map.values().toArray(values);
        String path = values[(values.length - 1)];
        if (path.startsWith("/mnt/")) {
            if (!Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    .equals(path)) {
                return path;
            }
        }
        return null;
    }

    public static long getAvailaleSize() {
        if (!existSDCard()) {
            return 0L;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getAllSize() {
        if (!existSDCard()) {
            return 0L;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return availableBlocks * blockSize;
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo ni = cm.getActiveNetworkInfo();
            return ni != null ? ni.isConnectedOrConnecting() : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;

        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);

        List serviceList = activityManager
                .getRunningServices(2147483647);

        if (serviceList.size() == 0) {
            return false;
        }
        for (int i = 0; i < serviceList.size(); i++) {
            if (((ActivityManager.RunningServiceInfo) serviceList.get(i)).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public static boolean isProessRunning(Context context, String proessName) {
        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals(proessName)) {
                isRunning = true;
                return isRunning;
            }
        }

        return isRunning;
    }

    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        if (StringUtils.isEmpty(imei)) {
            imei = "";
        }

        return imei;
    }

    public static String getMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (StringUtils.isEmpty(mac)) {
            mac = "";
        }
        return mac;
    }

    public static String getUDID(Context context) {
        String udid = Settings.Secure.getString(context.getContentResolver(), "android_id");

        if ((StringUtils.isEmpty(udid)) || (udid.equals("9774d56d682e549c")) ||
                (udid
                        .length() < 15)) {
            SecureRandom random = new SecureRandom();
            udid = new BigInteger(64, random).toString(16);
        }

        if (StringUtils.isEmpty(udid)) {
            udid = "";
        }

        return udid;
    }

    public static String getLatestCameraPicture(Context context) {
        if (!existSDCard()) {
            return null;
        }

        String[] projection = {"_id", "_data", "bucket_display_name", "datetaken", "mime_type"};

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, "datetaken DESC");

        if (cursor.moveToFirst()) {
            String path = cursor.getString(1);
            return path;
        }
        return null;
    }

    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displaysMetrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaysMetrics);

        return displaysMetrics;
    }

    public static int screenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int screenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }

        return height;
    }

    public static int getNavigationBarHeight(Context context) {
        int height = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");

        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }


    @SuppressLint({"NewApi"})
    public static boolean startActivityForPackage(Context context, String packageName) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        Intent resolveIntent = new Intent("android.intent.action.MAIN", null);
        resolveIntent.addCategory("android.intent.category.LAUNCHER");
        resolveIntent.setFlags(131072);

        resolveIntent.setPackage(pi.packageName);

        List apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);

        ResolveInfo ri = (ResolveInfo) apps.iterator().next();
        if (ri != null) {
            String packageName1 = ri.activityInfo.packageName;
            String className = ri.activityInfo.name;

            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setFlags(270532608);

            ComponentName cn = new ComponentName(packageName1, className);

            intent.setComponent(cn);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public static void hideInputSoftFromWindowMethod(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInputSoftFromWindowMethod(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isActiveSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    public static void goHome(Context context) {
        Intent mHomeIntent = new Intent("android.intent.action.MAIN");
        mHomeIntent.addCategory("android.intent.category.HOME");
        mHomeIntent.addFlags(270532608);

        context.startActivity(mHomeIntent);
    }

    public static int getPhoneType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        return telephonyManager.getPhoneType();
    }

    public static int getNetType(Context context) {
        int netWorkType = 0;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if ((networkInfo != null) && (networkInfo.isConnected())) {
            int type = networkInfo.getType();

            if (type == 1) {
                netWorkType = 1;
            } else if (type == 0) {
                TelephonyManager telephonyManager = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);

                switch (telephonyManager.getNetworkType()) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 2;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 3;
                    case 13:
                        return 4;
                }
                return 0;
            }

        }

        return netWorkType;
    }

    public static void callPhone(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse(new StringBuilder().append("tel:").append(phoneNumber).toString())));
    }

    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(new StringBuilder().append("tel:").append(phoneNumber).toString())));
    }

    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse(new StringBuilder().append("smsto:")
                .append(TextUtils.isEmpty(phoneNumber)
                        ? "" : phoneNumber).toString());
        Intent intent = new Intent("android.intent.action.SENDTO", uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        return telephony.getPhoneType() != 0;
    }
}
