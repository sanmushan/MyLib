package com.example.pver.mylib;

import android.os.Environment;


/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class Cache {

    /**
     * sdcard
     */
    public static final String SDCARD_FOLDER = Environment.getExternalStorageDirectory().toString();

    /**
     * 根目录
     */
    public static final String ROOT_FOLDER = SDCARD_FOLDER + "/db_courier/";

    /**
     * 图片目录
     */
    public static final String IMAGE_FOLDER = ROOT_FOLDER + "img/";

    //登陆的帐号
//    public static Passport passport;
    //快递员的工作状态，默认休息中
//    public static ClockTypeEnum clockTypeEnum = ClockTypeEnum.SLEEPING;
    //快递员的工作状态
//    public static WorkStatus workStatus;

    //开拓历史状态
//    public static ExploreHistoryInfo exploreHistoryInfo;

}
