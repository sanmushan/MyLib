package com.mylibrary;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mylibrary.http.Http;
import com.mylibrary.http.HttpCallback;
import com.mylibrary.utils.DialogUtils;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by zhumg on 3/15.
 */
public abstract class AfinalFragment extends Fragment {

    protected Dialog loadingDialog;

    protected View root_view;

    /**
     * Fragment当前状态是否可见
     */
    private boolean _visible;
    /**
     * 是否已初始化过
     */
    private boolean _init = false;

    /**
     * 是否被暂停过
     */
    private boolean _pause = false;

    /**
     * 第一次显示
     * 由于android 内部调用顺序问题，
     * setUserVisibleHint -> onCreateView -> onResume
     * <p/>
     * 这里一定要保证，onCreateView 完了，才能调用 onVisible
     */
    private boolean _first_visible = false;

    public abstract int getContentViewId();

    protected abstract void initViewData(View view);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ALog.e("fragment onCreateView " + getClass().getName());
        if (root_view == null) {
            int viewId = getContentViewId();
            if (viewId != 0) {
                root_view = inflater.inflate(viewId, null);

                _init = true;//初始化完成标记
                ButterKnife.bind(this, root_view);
                initViewData(root_view);

            } else {
                TextView txt = new TextView(this.getActivity());
                txt.setText("Fragment");
                txt.setTextColor(0);
                txt.setTextSize(16);
                root_view = txt;

                _init = true;//初始化完成标记
                initViewData(root_view);

                return root_view;
            }
        }
        return root_view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
//            Log.e("anlib", "fragment setUserVisibleHint true " + getClass().getName());
            _visible = true;
            if (_init) {
                _pause = false;
                Log.e("onVisible", "fragment setUserVisibleHint " + getClass().getName());
                onVisible();
            } else {
                _first_visible = true;
            }
        } else {
//            Log.e("anlib", "fragment setUserVisibleHint false / " + _visible + ", " + getClass().getName());
            boolean show = _visible;
            if (show && _init) {
                _visible = false;
                Log.e("onInvisible", "fragment setUserVisibleHint " + getClass().getName());
                onInvisible();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (root_view != null) {
            ButterKnife.unbind(this);
//            Log.e("anlib", "AfinalFramgent onDestroy");
        }
    }

    @Override
    public void onResume() {
//        Log.e("anlib", "fragment onResume " + getClass().getName());
        super.onResume();
        //可能存在的第一次显示
        if (_first_visible) {
            _first_visible = false;
            _visible = true;
            Log.e("onVisible", "fragment onResume _first_visible " + getClass().getName());
            onVisible();
            return;
        }
        //弹出activity，再关闭activity
        if (_pause) {
            _pause = false;
            if (_visible) {
                Log.e("onVisible", "fragment onResume " + getClass().getName());
                onVisible();
                return;
            }
        }
    }

    @Override
    public void onPause() {
//        Log.e("anlib", "fragment onPause " + getClass().getName());
        _pause = true;
        super.onPause();
        if (_visible) {
            Log.e("onInvisible", "fragment onPause " + getClass().getName());
//            //_visible = false;
            onInvisible();
        }
    }

    /**
     * 可见时，才进行网络调用
     */
    public void onVisible() {
    }

    /**
     * 不可见，清除一些数据
     */
    protected void onInvisible() {

    }

    //网络请求
    public void httpGet(Map map, String url, HttpCallback callback) {
        showLoadingDialog();
        Http.get(this.getActivity(), map, url, callback, loadingDialog);
    }

    //POS
    public void httpPost(Map map, String url, HttpCallback callback) {
        showLoadingDialog();
        Http.post(this.getActivity(), map, url, callback, loadingDialog);
    }

    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.createLoadingDialog(this.getContext(), true, true);
        }
        loadingDialog.show();
    }

    protected void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
