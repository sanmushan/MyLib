package com.mylibrary.loading;

/**
 * Created by zhumg on 2017/3/25 0025.
 */

public abstract class RefreshLoadListener {
    //当前显示加载中界面了，可以隐藏部分需要被遮档的界面
    public abstract void onLoading(boolean over);
    public abstract void onRefresh(boolean over);
    public void onLoadmore(boolean over) {}
}
