package com.example.pver.mylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pver.mylib.bean.DeliveryTodayPayInfo;
import com.example.pver.mylib.zxing.ScanActivity;
import com.mylibrary.AfinalActivity;
import com.mylibrary.SwipeBackLayout;
import com.mylibrary.http.Http;
import com.mylibrary.http.HttpCallback;
import com.mylibrary.widget.bar.BaseTitleBar;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AfinalActivity implements View.OnClickListener{
    ScanActivity.ScanCallback scanCallback;
    private BaseTitleBar baseTitleBar;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        setTranslucentStatus();
        baseTitleBar = new BaseTitleBar(view);
        baseTitleBar.setCenterTxt("妖孽哪里跑");
        baseTitleBar.setRightImg(R.drawable.scan, this);

        TextView tx_01 = (TextView) findViewById(R.id.tx_01);
        TextView tx_02 = (TextView) findViewById(R.id.tx_02);
        tx_01.setOnClickListener(new View.OnClickListener() {//侧滑返回
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,MySwipeBackActivity.class);
                startActivity(in);
            }
        });
        tx_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this,SwipeBackTwoActivity.class);
                startActivity(in);
            }
        });
        todayIncome();
    }

    private void todayIncome() {
        Map map = new HashMap();
        Http.post(this,map, "http://db.0085.com/logisticsController/getTodayDeliveryPay", new HttpCallback<DeliveryTodayPayInfo.ResponseEntity.DatasEntity>() {
            @Override
            public void onSuccess(DeliveryTodayPayInfo.ResponseEntity.DatasEntity data) {
            }

            @Override
            public void onFailure() {
                super.onFailure();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.title_right) {
            //扫一扫
            ScanActivity.setScanCallback(scanCallback);
            Intent intent = new Intent(MainActivity.this, ScanActivity.class);
            MainActivity.this.startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
