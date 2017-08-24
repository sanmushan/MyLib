package com.mylibrary.widget;

import android.widget.AdapterView;

/**
 * Created by zhumg on 3/23.
 */
public interface AdapterClickListener<DATA> {
    public void onItemClick(AdapterView<?> adapterView, DATA model, int position);
}
