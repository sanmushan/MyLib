package com.example.pver.mylib;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.mylibrary.swipeback.BaseActivity;

public class SwipeBackTwoActivity extends BaseActivity {
    private SwitchCompat mSwipeBackEnableSwitch;




    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_swipe_back_two);
        mSwipeBackEnableSwitch = getViewById(R.id.swipeBackEnableSwitch);
    }

    @Override
    protected void setListener() {
        testSwipeBackLayout();
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    /**
     * 测试滑动返回相关接口
     */
    private void testSwipeBackLayout() {
        // 测试动态设置滑动返回是否可用
        mSwipeBackEnableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean swipeBackEnable) {
                /**
                 * 设置滑动返回是否可用
                 */
                mSwipeBackHelper.setSwipeBackEnable(swipeBackEnable);
            }
        });

        // 测试动态设置是否仅仅跟踪左侧边缘的滑动返回
        ((SwitchCompat) getViewById(R.id.onlyTrackingLeftEdgeSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isOnlyTrackingLeftEdge) {
                /**
                 * 设置是否仅仅跟踪左侧边缘的滑动返回
                 */
                mSwipeBackHelper.setIsOnlyTrackingLeftEdge(isOnlyTrackingLeftEdge);
            }
        });

        // 测试动态设置是否是微信滑动返回样式
        ((SwitchCompat) getViewById(R.id.weChatStyleSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isWeChatStyle) {
                /**
                 * 设置是否是微信滑动返回样式
                 */
                mSwipeBackHelper.setIsWeChatStyle(isWeChatStyle);
            }
        });

        // 测试动态设置是否显示滑动返回的阴影效果
        ((SwitchCompat) getViewById(R.id.needShowShadowSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isNeedShowShadow) {
                /**
                 * 设置是否显示滑动返回的阴影效果
                 */
                mSwipeBackHelper.setIsNeedShowShadow(isNeedShowShadow);
            }
        });

        // 测试动态设置阴影区域的透明度是否根据滑动的距离渐变
        ((SwitchCompat) getViewById(R.id.shadowAlphaGradientSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isShadowAlphaGradient) {
                /**
                 * 设置阴影区域的透明度是否根据滑动的距离渐变
                 */
                mSwipeBackHelper.setIsShadowAlphaGradient(isShadowAlphaGradient);
            }
        });
    }
}
