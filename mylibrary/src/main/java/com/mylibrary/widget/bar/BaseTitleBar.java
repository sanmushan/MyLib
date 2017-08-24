package com.mylibrary.widget.bar;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylibrary.R;


/**
 * Created by zhumg on 3/17.
 */
public class BaseTitleBar {

    /** 返回键 资源 */
    public static int bar_color_resid = 0;
    public static int back_btn_img_resid = 0;

    private ImageView leftImg;
    private TextView centerTxt;
    private TextView title_right_txt;
    private ImageView rightImg;

    private View leftLayout;
    private View rightLayout;

    public BaseTitleBar(View v) {

        View view = v.findViewById(R.id.base_titlebar);

        leftImg = (ImageView)view.findViewById(R.id.title_left_img);
        centerTxt = (TextView)view.findViewById(R.id.title_center_txt);
        title_right_txt = (TextView)view.findViewById(R.id.title_right_txt);
        rightImg = (ImageView)view.findViewById(R.id.title_right_img);

        leftLayout = view.findViewById(R.id.title_left);
        rightLayout = view.findViewById(R.id.title_right);

        view.findViewById(R.id.base_titlebar).setBackgroundResource(bar_color_resid);
    }

    public void setLeftImg(int resId) {
        leftImg.setImageResource(resId);
    }

    public void setLeftListener(View.OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setLeftBack(final Activity activity) {
        setLeftImg(back_btn_img_resid);
        setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setRightImg(int resId, View.OnClickListener listener) {
        rightLayout.setVisibility(View.VISIBLE);
        rightImg.setImageResource(resId);
        rightImg.setVisibility(View.VISIBLE);
        rightLayout.setOnClickListener(listener);
        title_right_txt.setVisibility(View.GONE);
    }
    public void setRightTxt(String title, View.OnClickListener listener) {
        rightLayout.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        title_right_txt.setVisibility(View.VISIBLE);
        title_right_txt.setText(title);
        rightLayout.setOnClickListener(listener);
    }

    public void setCenterTxt(String txt) {
        centerTxt.setText(txt);
    }

    public void setCenterTxt(String txt, View.OnClickListener listener) {
        centerTxt.setText(txt);
        centerTxt.setOnClickListener(listener);
    }
}
