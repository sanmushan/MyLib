package com.mylibrary.utils;

import android.content.Context;

/**
 * Created by zhumg on 3/15.
 */
public class StringUtils {

    public static boolean isTrimEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isTrimEmpty(Context context, String str, String toastMsg) {
        if(str == null || str.trim().length() == 0) {
            ToastUtil.showToast(context, toastMsg);
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Context context, String str, String toastMsg) {
        if(str == null || str.length() == 0) {
            ToastUtil.showToast(context, toastMsg);
            return true;
        }
        return false;
    }

    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    public static String nullStrToEmpty(Object str) {
        return (str instanceof String) ? (String) str : str == null ? "" : str.toString();
    }

}
