package com.mylibrary.utils;

import android.view.View;

/**
 * Created by PVer on 2017/8/22.
 */

public class ViewUtils {
    public static <T> T find(View view, int id) {
        return (T)view.findViewById(id);
    }
}
