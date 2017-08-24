package com.mylibrary.loading;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylibrary.R;
import com.mylibrary.widget.ProgressBarCircular;


/**
 * Created by zhumg on 3/16.
 */
public class LoadingView implements View.OnClickListener {

    public static int Null_data_resid = 0;
    public static int Btn_Bg_Color_Resid = R.drawable.btn_blue;
    public static int Load_Bg_Color_Resid = R.color.blue;

    private View load_v;
    private ImageView wlv_img;
    private TextView wlv_btn;
    private View wlv_pro;
    private TextView wlv_txt;
    private View.OnClickListener btnListener;

    public LoadingView(View v) {
        load_v = v.findViewById(R.id.loadview);
        wlv_img = (ImageView) v.findViewById(R.id.wlv_img);
        wlv_btn = (TextView) v.findViewById(R.id.wlv_btn);
        wlv_pro = v.findViewById(R.id.wlv_pro);
        wlv_txt = (TextView) v.findViewById(R.id.wlv_txt);

        wlv_btn.setOnClickListener(this);
        wlv_btn.setBackgroundResource(Btn_Bg_Color_Resid);

        ((ProgressBarCircular) wlv_pro).backgroundColor = v.getResources().getColor(Load_Bg_Color_Resid);
    }

    public boolean isShow() {
        return load_v.getVisibility() == View.VISIBLE;
    }

    public void hibe() {
        load_v.setVisibility(View.GONE);
    }

    /**
     * 显示默认样式，圆圈加载，文字显示
     */
    public void showLoading() {
        wlv_txt.setText("加载中，请稍后...");
        wlv_txt.setVisibility(View.VISIBLE);
        wlv_pro.setVisibility(View.VISIBLE);

        wlv_img.setVisibility(View.GONE);
        wlv_btn.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 显示默认样式，圆圈加载，文字显示
     */
    public void showLoading(String txt) {

        wlv_txt.setText(txt);
        wlv_txt.setVisibility(View.VISIBLE);
        wlv_pro.setVisibility(View.VISIBLE);

        wlv_img.setVisibility(View.GONE);
        wlv_btn.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 只显示一张图片
     */
    public void showLoadingImg(int resId) {

        wlv_img.setImageResource(resId);
        wlv_img.setVisibility(View.VISIBLE);

        wlv_txt.setVisibility(View.GONE);
        wlv_pro.setVisibility(View.GONE);
        wlv_btn.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 只显示一个字符串
     */
    public void showLoadingTxt(String txt) {

        wlv_img.setVisibility(View.GONE);

        wlv_txt.setVisibility(View.VISIBLE);
        wlv_txt.setText(txt);

        wlv_pro.setVisibility(View.GONE);
        wlv_btn.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 显示图片，与 字符串
     */
    public void showLoadingImgTxt(int resId, String txt) {

        wlv_img.setImageResource(resId);
        wlv_img.setVisibility(View.VISIBLE);

        wlv_txt.setVisibility(View.VISIBLE);
        wlv_txt.setText(txt);

        wlv_pro.setVisibility(View.GONE);
        wlv_btn.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 显示图片，与 字符串，按钮
     */
    public void showLoadingImgTxtBtn(int resId, String txt, String btnName) {

        wlv_img.setImageResource(resId);
        wlv_img.setVisibility(View.VISIBLE);

        wlv_txt.setVisibility(View.VISIBLE);
        wlv_txt.setText(txt);

        wlv_btn.setText(btnName);
        wlv_btn.setVisibility(View.VISIBLE);

        wlv_pro.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }


    /**
     * 显示 字符串，按钮
     */
    public void showLoadingTxtBtn(String txt, String btnName) {

        wlv_img.setVisibility(View.GONE);

        wlv_txt.setVisibility(View.VISIBLE);
        wlv_txt.setText(txt);

        wlv_btn.setText(btnName);
        wlv_btn.setVisibility(View.VISIBLE);

        wlv_pro.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    /**
     * 显示 字符串，按钮
     */
    public void showReset(String txt) {

        wlv_img.setVisibility(View.GONE);

        wlv_txt.setVisibility(View.VISIBLE);
        wlv_txt.setText(txt);

        wlv_btn.setVisibility(View.VISIBLE);

        wlv_pro.setVisibility(View.GONE);

        load_v.setVisibility(View.VISIBLE);
        load_v.getParent().requestLayout();
    }

    public LoadingView setResetListener(View.OnClickListener resetListener) {
        this.btnListener = resetListener;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (btnListener != null) {
            btnListener.onClick(v);
        }
    }
}
