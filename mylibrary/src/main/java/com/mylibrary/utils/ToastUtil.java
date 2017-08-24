package com.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by zhumg on 3/15.
 */
public class ToastUtil {

    private static Toast toast = null;

    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.show();
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }
}
