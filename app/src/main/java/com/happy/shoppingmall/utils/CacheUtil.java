package com.happy.shoppingmall.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by happy on 2017/5/29.
 */

public class CacheUtil {
    /**
     * 获取是否进入主界面
     *
     * @param context
     * @param key
     * @param Default
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean Default) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        return sp.getBoolean(key, Default);
    }

    /**
     * 设置是否进入主界面
     *
     * @param context
     * @param key
     * @param isBoolean
     */
    public static void putBoolean(Context context, String key, boolean isBoolean) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, isBoolean).commit();
    }

    /**
     * 缓存网络请求数据
     *
     * @param context
     * @param key
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取缓存请求数据方法
     *
     * @param context
     * @param key
     * @param def     默认值
     * @return
     */
    public static String getString(Context context, String key, String def) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        String result = sp.getString(key, def);
        return result;
    }

    public static int getInt(Context context, String key, int size) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        return sp.getInt(key, size);

    }

    public static void putInt(Context context, String key, int realSize) {
        SharedPreferences sp = context.getSharedPreferences("ShoppingMall", Context.MODE_PRIVATE);
        sp.edit().putInt(key, realSize).commit();
    }
}
