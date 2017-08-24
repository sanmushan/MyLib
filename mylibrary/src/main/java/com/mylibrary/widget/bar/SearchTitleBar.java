package com.mylibrary.widget.bar;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mylibrary.R;


/**
 * Created by zhumg on 3/17.
 */
public class SearchTitleBar implements View.OnClickListener {

    public static int search_edittext_bg_resid = 0;
    public static int search_left_img_resid = 0;
    public static int search_btn_x_resid = 0;

    private ImageView leftImg;
    private TextView leftTxt;
    private View leftLayout;

    private ImageView rightImg;
    private TextView rightTxt;
    private View rightLayout;

    private View centerLayout;

    private ImageView center_search_img;
    private ImageView center_search_ximg;
    private EditText center_search_edit;

    private EditTextListener listener;

    public SearchTitleBar(View view) {

        leftImg = (ImageView) view.findViewById(R.id.title_left_img);
        leftLayout = view.findViewById(R.id.title_left);
        leftTxt = (TextView) view.findViewById(R.id.title_left_txt);

        rightImg = (ImageView) view.findViewById(R.id.title_right_img);
        rightLayout = view.findViewById(R.id.title_right);
        rightTxt = (TextView)view.findViewById(R.id.title_right_txt);

        centerLayout = view.findViewById(R.id.title_center_layout);

        center_search_img = (ImageView) view.findViewById(R.id.center_search_img);
        center_search_ximg = (ImageView) view.findViewById(R.id.center_search_ximg);
        center_search_edit = (EditText) view.findViewById(R.id.center_search_edit);
        center_search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString().trim();
                if (str.length() < 1) {
                    if (listener != null) {
                        listener.notifyInputTxt(null);
                    }
                    center_search_ximg.setVisibility(View.INVISIBLE);
                } else {
                    if (listener != null) {
                        listener.notifyInputTxt(str);
                    }
                    center_search_ximg.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        centerLayout.setBackgroundResource(search_edittext_bg_resid);
        center_search_img.setImageResource(search_left_img_resid);
        center_search_ximg.setImageResource(search_btn_x_resid);
        center_search_ximg.setVisibility(View.INVISIBLE);
        center_search_ximg.setOnClickListener(this);

        view.findViewById(R.id.search_bar).setBackgroundResource(BaseTitleBar.bar_color_resid);
    }

    public void setLeftImg(int resId) {
        leftImg.setImageResource(resId);
    }

    public void setLeftListener(View.OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setLeftTxt(String txt) {
        leftTxt.setText(txt);
    }

    public void setLeftBack(final Activity activity) {
        setLeftImg(BaseTitleBar.back_btn_img_resid);
        setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void setRightImg(int resId) {
        rightLayout.setVisibility(View.VISIBLE);
        rightImg.setImageResource(resId);
    }

    public void setRightListener(View.OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public void setRightTxt(String txt) {
        rightLayout.setVisibility(View.VISIBLE);
        rightTxt.setText(txt);
    }

    public void setEditTxt(String editTxt) {
        this.center_search_edit.setText(editTxt);
    }

    public void setEditTxtListener(EditTextListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.center_search_ximg) {
            center_search_edit.setText("");
        }
    }

    public void setTextHint(String s) {
        center_search_edit.setHint(s);
    }

    public static interface EditTextListener {
        public void notifyInputTxt(String str);
    }

    public ImageView getLeftImg() {
        return leftImg;
    }

    public TextView getLeftTxt() {
        return leftTxt;
    }

    public View getLeftLayout() {
        return leftLayout;
    }

    public ImageView getRightImg() {
        return rightImg;
    }

    public TextView getRightTxt() {
        return rightTxt;
    }

    public View getRightLayout() {
        return rightLayout;
    }

    public View getCenterLayout() {
        return centerLayout;
    }

    public ImageView getCenter_search_img() {
        return center_search_img;
    }

    public ImageView getCenter_search_ximg() {
        return center_search_ximg;
    }

    public EditText getCenter_search_edit() {
        return center_search_edit;
    }
}
