package com.mylibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.mylibrary.R;
import com.mylibrary.utils.DeviceUtils;


/**
 * TODO<提示dialog>
 *
 * @author ak
 * @data: 2015年8月23日 下午4:08:24
 * @version: V1.0
 */
public class TipDialog extends Dialog {

    private TextView mTvMessage;//正文
    private TextView mBtnCancel;//左按钮
    private TextView mBtnSure;//右按钮
    private TextView mTvTitle;//标题
    private TipClickListener mListener;//单击事件
    private ViewGroup contentPanel;
    //锁定,不允许按返回键
    private boolean lock = true;
    //点击关闭按钮，默认是允许的
    private boolean click_close = true;

    public TipDialog(Context context) {
        super(context, R.style.Alert_Dialog_Style);
        init(context);
    }

    private void init(Context context) {
        setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.widget_dialog_tip, null);
        setContentView(view);
        init(view);
    }

    private void init(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mBtnCancel = (TextView) view.findViewById(R.id.dialog_btn_cancel);
        mBtnSure = (TextView) view.findViewById(R.id.dialog_btn_sure);
        contentPanel = (ViewGroup) view.findViewById(R.id.contentPanel);
        mBtnSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
                if (mListener != null)
                    mListener.onClick(false);

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
                if (mListener != null)
                    mListener.onClick(true);

            }
        });
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = DeviceUtils.screenWidth(this.getContext());
        window.setAttributes(params);
    }

    public boolean isClick_close() {
        return click_close;
    }

    public void setClick_close(boolean click_close) {
        this.click_close = click_close;
    }

    public TipDialog hibeLeftBtn() {
        findViewById(R.id.tig).setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.GONE);
        return this;
    }

    public TipDialog hibeTitle() {
        findViewById(R.id.tv_title_g).setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
        return this;
    }

    public TipDialog setContentPane(View view) {
        mTvMessage.setVisibility(View.GONE);
        contentPanel.addView(view);
        return this;
    }

    public TipDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public TipDialog setLeftBtn(String leftBtn) {
        mBtnCancel.setText(leftBtn);
        mBtnCancel.setVisibility(View.VISIBLE);
        return this;
    }

    public TipDialog setRightBtn(String rightBtn) {
        mBtnSure.setText(rightBtn);
        mBtnSure.setVisibility(View.VISIBLE);
        return this;
    }

    public TipDialog setContentMsg(String msg) {
        mTvMessage.setText(msg);
        return this;
    }

    public TipDialog setTipClickListener(TipClickListener listener) {
        this.mListener = listener;
        return this;
    }

    public TextView getLeftBtn() {
        return mBtnCancel;
    }

    public TextView getTitle() {
        return mTvTitle;
    }

    public TextView getContentMsg() {
        return mTvMessage;
    }

    public TextView getRightBtn() {
        return mBtnSure;
    }

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
}
