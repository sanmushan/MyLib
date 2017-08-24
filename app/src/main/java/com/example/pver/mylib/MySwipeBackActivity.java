package com.example.pver.mylib;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;

import com.mylibrary.swipeback.BGASwipeBackHelper;
import com.mylibrary.swipeback.SwipeBackActivity;

public class MySwipeBackActivity extends SwipeBackActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_swipeback_layout);

    }

}
