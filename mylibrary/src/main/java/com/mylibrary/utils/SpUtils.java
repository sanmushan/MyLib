package com.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhumg on 2017/3/23 0023.
 */

public class SpUtils {

    private static SharedPreferences appShared;// 用于保存应用内部数据
    private static SharedPreferences.Editor appEditor; // 编辑appShared中的数据

    public static void init(Context context, String name) {
        appShared = context.getSharedPreferences(name, MODE_PRIVATE);
        appEditor = appShared.edit();
    }

    public static void saveValue(String key, String value) {
        appEditor.putString(key, value);
        appEditor.commit();
    }

    public static String loadValue(String key) {
        return appShared.getString(key, "");
    }

    public static void saveJson(String key, String json) {
        appEditor.putString(key, json);
        appEditor.commit();
    }

    public static <T> T loadJson(String key, Class<T> cls) throws JsonIOException, JsonSyntaxException {
        String json = loadValue(key);
        if (json.length() > 0) {
            return JsonUtils.fromJson(json, cls);
        }
        return null;
    }
}
