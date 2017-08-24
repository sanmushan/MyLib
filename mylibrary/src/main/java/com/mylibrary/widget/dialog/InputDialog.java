package com.mylibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mylibrary.R;
import com.mylibrary.utils.DeviceUtils;


/**
 * Created by zhumg on 4/21.
 */
public class InputDialog extends Dialog {

    private EditText mTvMessage;//正文
    private TextView mBtnCancel;//左按钮
    private TextView mBtnSure;//右按钮
    private TextView mTvTitle;//标题
    private InputClickListener mListener;//单击事件
    private ViewGroup contentPanel;

    public InputDialog(Context context, int inputType) {
        super(context, R.style.Alert_Dialog_Style);
        init(context);
        mTvMessage.setInputType(inputType);
    }

    private void init(Context context) {
        setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.widget_dialog_input, null);
        setContentView(view);
        init(view);
    }

    private void init(View view) {
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvMessage = (EditText) view.findViewById(R.id.tv_message);
        mBtnCancel = (TextView) view.findViewById(R.id.dialog_btn_cancel);
        mBtnSure = (TextView) view.findViewById(R.id.dialog_btn_sure);
        contentPanel = (ViewGroup) view.findViewById(R.id.contentPanel);
        mBtnSure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
                if (mListener != null)
                    mListener.onInput(mTvMessage.getText().toString().trim());

            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
                if (mListener != null)
                    mListener.onInput(null);

            }
        });
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.alpha = 1.0f;
        params.width = DeviceUtils.screenWidth(this.getContext());
        window.setAttributes(params);
    }

    public InputDialog hibeLeftBtn() {
        findViewById(R.id.tig).setVisibility(View.GONE);
        mBtnCancel.setVisibility(View.GONE);
        return this;
    }

    public InputDialog hibeTitle() {
        findViewById(R.id.tv_title_g).setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
        return this;
    }

    public InputDialog setContentPane(View view) {
        mTvMessage.setVisibility(View.GONE);
        contentPanel.addView(view);
        return this;
    }

    public InputDialog setTitle(String title) {
        mTvTitle.setText(title);
        return this;
    }

    public InputDialog setLeftBtn(String leftBtn) {
        mBtnCancel.setText(leftBtn);
        return this;
    }

    public InputDialog setRightBtn(String rightBtn) {
        mBtnSure.setText(rightBtn);
        return this;
    }

    public InputDialog setContentMsg(String msg) {
        mTvMessage.setText(msg);
        return this;
    }

    public InputDialog setInputClickListener(InputClickListener listener) {
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
}
