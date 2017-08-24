package com.mylibrary.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.widget.TextView;

import com.mylibrary.R;
import com.mylibrary.widget.dialog.TipClickListener;
import com.mylibrary.widget.dialog.TipDialog;


/**
 * Created by zhumg on 3/17.
 */
public class DialogUtils {

    /**
     * @param mContext
     * @param translucent 是否透明
     * @param lock        是否锁定
     * @return
     */
    public static Dialog createLoadingDialog(Context mContext, boolean translucent, final boolean lock) {
        return createLoadingDialog(mContext, "请稍后", translucent, lock);
    }

    /**
     * @param mContext
     * @param msg
     * @param translucent 是否透明
     * @return
     */
    public static Dialog createLoadingDialog(Context mContext, String msg, boolean translucent, final boolean lock) {
        int id = translucent ? R.style.Translucent_NoTitle_Dialog : R.style.NoTitle_Dialog;
        Dialog dialog = new Dialog(mContext, id) {
            @Override
            public boolean onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
                    if (lock) {
                        return true;
                    }
                } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                    //监控/拦截菜单键
                } else if (keyCode == KeyEvent.KEYCODE_HOME) {
                    //由于Home键为系统键，此处不能捕获，需要重写onAttachedToWindow()
                }
                return super.onKeyDown(keyCode, event);
            }
        };
        dialog.setCanceledOnTouchOutside(!lock);
        dialog.setContentView(R.layout.widget_dialog_loading);
        TextView tv_loading = (TextView) dialog.findViewById(R.id.tv_loading);
        tv_loading.setText(msg);
        return dialog;
    }

    //提示
    public static TipDialog createTipDialog(Context context, String content, String btn) {
        return createTipDialog(context, null, content, null, btn, null);
    }

    //提示
    public static TipDialog createTipDialog(Context context, String content, TipClickListener listener) {
        return createTipDialog(context, null, content, "取消", "确定", listener);
    }

    //提示
    public static TipDialog createTipDialog(Context context, String content, String btn, TipClickListener listener) {
        return createTipDialog(context, null, content, null, btn, listener);
    }

    //提示
    public static TipDialog createTipDialog(Context context, String title, String content, String leftBtn, String rightBtn, TipClickListener listener) {
        TipDialog tipDialog = new TipDialog(context);
        if (title == null) {
            title = "温馨提示";
        }
        tipDialog.setTitle(title);
        tipDialog.setContentMsg(content);
        if (leftBtn == null) {
            tipDialog.hibeLeftBtn();
        } else {
            tipDialog.setLeftBtn(leftBtn);
        }
        tipDialog.setRightBtn(rightBtn);
        tipDialog.setTipClickListener(listener);
        return tipDialog;
    }
}
