package com.mylibrary.loading;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.mylibrary.R;


/**
 * Created by zhumg on 2017/3/26 0026.
 */

public class LoadmoreView {

    private View pre;
    private View view;
    private TextView txt;
    private View.OnClickListener listener;
    private int type;

    public View getView(Context context, View.OnClickListener l) {
        this.listener = l;
        if (view == null) {
            view = View.inflate(context, R.layout.widget_view_loadmore, null);
            pre = view.findViewById(R.id.load_bar);
            txt = (TextView) view.findViewById(R.id.load_txt);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(type == 2 && listener != null) {
                        //点击可以重试加载
                        listener.onClick(v);
                    }
                }
            });
        }
        return view;
    }

    /**
     * 显示加载更多
     */
    public void setLoadmoreTxt(String text) {
        this.txt.setText(text);
        this.pre.setVisibility(View.VISIBLE);
        this.view.setVisibility(View.VISIBLE);
        type = 1;
    }

    /**
     * 显示加载异常 ， 这里允许点击重试
     */
    public void showError(String text) {
        this.txt.setText(text);
        this.pre.setVisibility(View.GONE);
        this.view.setVisibility(View.VISIBLE);
        type = 2;
    }

    /**
     * 显示加载完成，没有更多数据之类的
     */
    public void showComplete(String text) {
        this.txt.setText(text);
        this.pre.setVisibility(View.GONE);
        this.view.setVisibility(View.VISIBLE);
        type = 3;
    }

    public void hibe() {
        this.view.setVisibility(View.GONE);
    }
}
