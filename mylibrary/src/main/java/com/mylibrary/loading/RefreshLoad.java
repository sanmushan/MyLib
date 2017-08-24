package com.mylibrary.loading;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.mylibrary.R;
import com.mylibrary.utils.DeviceUtils;
import com.mylibrary.widget.GridViewWithHeaderAndFooter;


import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

import static com.lzy.okgo.OkGo.getContext;

import static com.lzy.okgo.OkGo.getContext;

/**
 * Created by zhumg on 2017/3/25 0025.
 * <p>
 * 加载，刷新
 */

public class RefreshLoad {

    PtrClassicFrameLayout ptr;

    RefreshLoadListener listener;

    LoadingView loadingView;
    LoadmoreView loadmoreView;

    //当前加载方式 1普通加载，2下拉加载，3加载更多
    int loadType;
    //上一次时间
    long oldTime;
    //刷新判断时间
    long refresTime;

    //是否允许下拉，默认为true
    boolean canRefresh = true;
    //是否能加载更多，默认可以
    boolean canLoadMore = true;
    //是否在加载更多中
    boolean runLoadMore = false;

    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }


    public RefreshLoad(Context context, PtrClassicFrameLayout ptrLayout, View rootView, final RefreshLoadListener rListener) {
        this(context, ptrLayout, rootView, rListener, null);
    }

    public RefreshLoad(Context context, PtrClassicFrameLayout ptrLayout, View rootView, final RefreshLoadListener rListener, AbsListView listView) {

        this.ptr = ptrLayout;
        this.listener = rListener;
        this.loadingView = new LoadingView(rootView);
        this.loadingView.setResetListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
            }
        });
        //默认隐藏
        this.loadingView.hibe();

        this.addHead();

        //设置刷新监听
        this.ptr.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (loadType == 2) {
                    Log.e("db", "程序强制刷新，需要判断刷新时间");
                    if (refresTime > 0) {
                        long time = System.currentTimeMillis();
                        if (time - oldTime > refresTime) {
                            oldTime = time;
                        } else {
                            //直接完成
                            ptr.refreshComplete();
                            return;
                        }
                    }
                } else {
                    Log.e("db", "用户手动刷新，不判断刷新时间");
                }
                loadType = 2;
                listener.onRefresh(false);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //加载中
                if (loadingView.isShow()) {
                    return false;
                }
                //加载更多中
                if (runLoadMore) {
                    return false;
                }
                //不允许下拉刷新
                if (!canRefresh) {
                    return false;
                }
                return checkContentCanBePulledDown(frame, content, header);
            }
        });
        if (listView == null) {
            return;
        }

        AbsListView absListView = null;

        if (listView instanceof ListView) {
            ListView g = (ListView) listView;
            View v = createLoadmore();
            g.addFooterView(v);
            absListView = g;
        } else if (listView instanceof GridViewWithHeaderAndFooter) {
            GridViewWithHeaderAndFooter g = (GridViewWithHeaderAndFooter) listView;
            View v = createLoadmore();
            g.addFooterView(v);
            absListView = g;
        }

        if (absListView != null) {
            absListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                private boolean isBottom;

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    if (scrollState == SCROLL_STATE_IDLE) {
                        if (isBottom) {
                            loadMore();
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                        Log.e("db", "滚动到底部了");
                        //显示底部加载更多
                        isBottom = true;
                    } else {
                        isBottom = false;
                    }
                }
            });
        }
    }

    View createLoadmore() {
        loadmoreView = new LoadmoreView();
        View v = loadmoreView.getView(getContext(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadType = 3;
                listener.onLoadmore(false);
            }
        });
        //默认先隐藏
        v.setVisibility(View.GONE);
        return v;
    }

    void loadMore() {
        if (!canLoadMore) {
            Log.e("db", "当前不允许加载更多.....");
            return;
        }
        if (runLoadMore) {
            Log.e("db", "正在加载更多.....");
            return;
        }
        runLoadMore = true;
        loadType = 3;
        //显示
        loadmoreView.setLoadmoreTxt("正在加载...");
        listener.onLoadmore(false);
        Log.e("db", "开始 加载更多.....");
    }

    public void addHead() {
        Context context = getContext();

        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, DeviceUtils.dip2px(context, 20), 0, DeviceUtils.dip2px(context, 20));
        header.initWithString("0085 ONE");
        header.setTextColor(context.getResources().getColor(R.color.font_6));

        this.ptr.setDurationToCloseHeader(1500);
        this.ptr.setHeaderView(header);
        this.ptr.addPtrUIHandler(header);
    }

    public void setRefresTime(long refresTime) {
        this.refresTime = refresTime;
    }

    public void showLoading() {
        this.loadType = 1;
        this.loadingView.showLoading();
        this.listener.onLoading(false);
    }

    public void showLoading(String txt) {
        this.loadType = 1;
        this.listener.onLoading(false);
        this.loadingView.showLoading(txt);
    }

    public void showRefresh() {
        this.loadType = 2;
        this.ptr.autoRefresh();
    }

    public LoadingView getLoadingView() {
        return this.loadingView;
    }

    public void showError(String error) {
        runLoadMore = false;
        if (loadType == 1) {
            this.loadingView.showReset(error);
        } else if (loadType == 2) {
            this.loadType = 0;
            this.ptr.refreshComplete();
            this.loadingView.showLoadingTxtBtn(error, "刷新");
            this.listener.onLoading(false);
        } else if (loadType == 3) {
            this.loadmoreView.showError(error);
        }
    }

    public void showReset(String error) {
        this.loadType = 0;
        this.ptr.refreshComplete();
        this.loadingView.showLoadingTxtBtn(error, "刷新");
    }

    public void showInfo(int imgResId, String info, String btn, View.OnClickListener clickListener) {
        this.loadType = 0;
        this.ptr.refreshComplete();
        this.loadingView.showLoadingImgTxtBtn(imgResId, info, btn);
        this.loadingView.setResetListener(clickListener);
    }

    public boolean isLoading() {
        return loadType == 1;
    }

    public boolean isRefresh() {
        return loadType == 2;
    }

    public boolean isLoadMore() {
        return loadType == 3;
    }

    public void complete(boolean canLoadMore, boolean isEmpty) {
        if (loadType == 1) {
            this.loadingView.hibe();
            this.listener.onLoading(true);
        } else if (loadType == 2) {
            this.ptr.refreshComplete();
            this.listener.onRefresh(true);
        }

        this.runLoadMore = false;
        this.canLoadMore = canLoadMore;

        if(this.loadmoreView != null) {
            if (canLoadMore) {
                this.loadmoreView.hibe();
            } else if(!isEmpty) {
                //不为空的情况下
                this.loadmoreView.showComplete("没有更多数据了");
                this.listener.onLoadmore(true);
            }
        }
        loadType = 0;
    }

    //强制隐藏
    public void hibe() {
        loadType = 0;
        this.loadingView.hibe();
    }

}
