package com.mylibrary;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;
import com.mylibrary.http.Http;
import com.mylibrary.http.HttpCallback;
import com.mylibrary.swipeback.SwipeBackActivityBase;
import com.mylibrary.swipeback.SwipeBackActivityHelper;
import com.mylibrary.utils.DialogUtils;
import com.mylibrary.utils.GlobalDataParam;

import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by zhumg on 3/15.
 */
public abstract class AfinalSwipeBackActivity extends FragmentActivity implements AfinalActivityHttpLife,SwipeBackActivityBase {

    private SwipeBackActivityHelper mHelper;

    public static int top_bg_color_resid = 0;

    public abstract int getContentViewId();

    public abstract void initView(View view);

    protected Dialog loadingDialog;

    protected boolean httpLife = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);

        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();

        int viewid = getContentViewId();
        View rootView = null;
        if (viewid != 0) {
            rootView = View.inflate(this, viewid, null);
            setContentView(rootView);
            ButterKnife.bind(this);
            initView(rootView);
        }
    }

    //--------------------------------以下是滑动返回-----------------------------------
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    //-----------------------------以上是滑动返回----------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void finish() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        super.finish();
        httpLife = true;
        OkGo.getInstance().cancelTag(this);
        ActivityManager.removeActivity(this);
    }

    public boolean isHttpFinish() {
        return httpLife;
    }

    /**
     * 设置状态栏背景状态
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        int resid = top_bg_color_resid;
        if (resid != 0) {
            tintManager.setStatusBarTintResource(resid);// 状态栏无背景
        }
    }

    //网络请求
    public void httpGet(Map map, String url, HttpCallback callback) {
        showLoadingDialog();
        Http.get(this, map, url, callback, loadingDialog);
    }
    //TODO 无LoadingDialog的GET方法的网络请求
    public void httpGetTwo(Map map, String url, HttpCallback callback) {
        Http.get(this, map, url, callback);
    }

    //POS
    public void httpPost(Map map, String url, HttpCallback callback) {
        showLoadingDialog();

        String permissions = Manifest.permission.READ_PHONE_STATE;
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissions}, 1010);
        } else {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String DEVICE_ID = tm.getDeviceId();
            //TODO 传递到URL的公共参数，没有的话不需要写
            map.put("accessToken", GlobalDataParam.accessToken);
            map.put("passportId", GlobalDataParam.passportId);
            map.put("roleValue", GlobalDataParam.roleValue);
            map.put("showName", GlobalDataParam.showName);
            map.put("version", GlobalDataParam.versionstr);
            map.put("platform", "2");
            map.put("device", DEVICE_ID);
            map.put("channel", "0");
            Http.post(this, map, url, callback, loadingDialog);
        }
    }

    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = DialogUtils.createLoadingDialog(this, true, false);
        }
        loadingDialog.show();
    }

    protected void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
//
//    //TODO 解决 activity 多层 fragment 嵌套时，fragment 的 onActivityResult 无法调用问题
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        FragmentManager fm = getSupportFragmentManager();
//        int index = requestCode >> 16;
//        if (index != 0) {
//            index--;
//            if (fm.getFragments() == null || index < 0
//                    || index >= fm.getFragments().size()) {
//                Log.w("AfinalActivity", "Activity result fragment index out of range: 0x"
//                        + Integer.toHexString(requestCode));
//                onActivityResultImpl(requestCode, resultCode, data);
//                return;
//            }
//            Fragment frag = fm.getFragments().get(index);
//            if (frag == null) {
//                Log.w("AfinalActivity", "Activity result no fragment exists for index: 0x"
//                        + Integer.toHexString(requestCode));
//            } else {
//                handleResult(frag, requestCode, resultCode, data);
//            }
//            return;
//        }
//        onActivityResultImpl(requestCode, resultCode, data);
//    }
//
//    protected void onActivityResultImpl(int requestCode, int resultCode, Intent data) {
//    }
//
//    /**
//     * 递归调用，对所有子Fragement生效
//     *
//     * @param frag
//     * @param requestCode
//     * @param resultCode
//     * @param data
//     */
//    private void handleResult(Fragment frag, int requestCode, int resultCode,
//                              Intent data) {
//        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
//        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
//        if (frags != null) {
//            for (Fragment f : frags) {
//                if (f != null)
//                    handleResult(f, requestCode, resultCode, data);
//            }
//        }
//    }
}
