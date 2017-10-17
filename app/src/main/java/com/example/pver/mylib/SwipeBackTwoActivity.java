package com.example.pver.mylib;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.mylibrary.swipeback.SwipeBackActivity;


public class SwipeBackTwoActivity extends SwipeBackActivity {
    SwitchCompat compat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back_two);
        compat = (SwitchCompat) findViewById(R.id.onlyTrackingLeftEdgeSwitch);
        initView();
    }

    private void initView() {
        // 测试动态设置是否仅仅跟踪左侧边缘的滑动返回
        compat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isOnlyTrackingLeftEdge) {
                /**
                 * 设置是否仅仅跟踪左侧边缘的滑动返回
                 */
//                mSwipeBackHelper.setIsOnlyTrackingLeftEdge(isOnlyTrackingLeftEdge);
            }
        });
    }

}