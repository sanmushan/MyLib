package com.example.pver.mylib.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.pver.mylib.MySwipeBackActivity;
import com.example.pver.mylib.R;
import com.example.pver.mylib.bean.DeliveryTodayPayInfo;
import com.mylibrary.utils.ViewUtils;
import com.mylibrary.widget.AfinalAdapter;

import java.util.List;

/**
 * 送货收入adapter
 * Created by PVer on 2017/7/5.
 */

public class DeliveryTodayPayAdapter extends AfinalAdapter<DeliveryTodayPayInfo.ResponseEntity.DatasEntity> {
    MySwipeBackActivity activity;


    public DeliveryTodayPayAdapter(Context context, List<DeliveryTodayPayInfo.ResponseEntity.DatasEntity> deliveryTodayPayInfos, MySwipeBackActivity activity) {
        super(context, deliveryTodayPayInfos);
        this.activity = activity;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_results, null);
            holder = new DeliveryTodayPayAdapter.Holder();
            holder.init(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        DeliveryTodayPayInfo.ResponseEntity.DatasEntity resultsInfo = getItem(position);
//        double deliveryTodayPay = resultsInfo.getDeliveryTodayPay() / 100;
//        double _deliveryTodayPay = deliveryTodayPay / 100000;

        double l = Double.valueOf(resultsInfo.getDeliveryTodayPay());
        double deliveryTodayPay = l / 100;
        double _deliveryTodayPay = deliveryTodayPay / 100000;


        java.text.DecimalFormat df = new java.text.DecimalFormat("#.#####");

//        if (resultsInfo.getDeliveryTodayPay()>0) {
            holder.income.setText("￥" + df.format(_deliveryTodayPay) + "");
            holder.courierName.setText(resultsInfo.getName());
            holder.num.setText(String.valueOf(resultsInfo.getId()));
//        }
        return convertView;
    }

    class Holder {
        TextView courierName;
        TextView income;
        TextView num;

        void init(View view) {
            courierName = ViewUtils.find(view, R.id.courierName);
            income = ViewUtils.find(view, R.id.income);
            num = ViewUtils.find(view, R.id.num);
        }
    }
}
