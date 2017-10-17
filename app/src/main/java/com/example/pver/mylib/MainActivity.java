package com.example.pver.mylib;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pver.mylib.bean.DeliveryTodayPayInfo;
import com.example.pver.mylib.bean.WeChatInfo;
import com.example.pver.mylib.zxing.ScanActivity;
import com.google.gson.Gson;
import com.mylibrary.AfinalActivity;
import com.mylibrary.callback.GenericsCallback;
import com.mylibrary.http.Http;
import com.mylibrary.http.HttpCallback;
import com.mylibrary.http.OkHttpUtils;
import com.mylibrary.utils.ToastUtil;
import com.mylibrary.widget.JsonGenericsSerializator;
import com.mylibrary.widget.bar.BaseTitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AfinalActivity implements View.OnClickListener {
    ScanActivity.ScanCallback scanCallback;
    private BaseTitleBar baseTitleBar;
    String  courierURL = "http://db.0085.com/logisticsController/getTodayDeliveryPay";
    ImageView imgHead;
    TextView tx_02;
    List<WeChatInfo.ResultEntity.ListEntity>list = new ArrayList<>();
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
         tx_02 = (TextView) findViewById(R.id.tx_02);
        imgHead = (ImageView) findViewById(R.id.imgHead);
        tx_01.setOnClickListener(new View.OnClickListener() {//侧滑返回
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, MySwipeBackActivity.class);
                startActivity(in);
            }
        });
        tx_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, SwipeBackTwoActivity.class);
                startActivity(in);
            }
        });

//        todayIncome();
        data();
//        getData();
//        dataOK();
    }

    private void dataOK() {
        String url = "http://v.juhe.cn/weixin/query?pno=1&ps=3&dtype=&key=b920149721e049c80bcc4897f50f3f52";
        // step 1: 创建 OkHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // step 2： 创建一个请求，不指定请求方法时默认是GET。
        Request.Builder requestBuilder = new Request.Builder().url(url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);

        // step 3：创建 Call 对象
        Call call = okHttpClient.newCall(requestBuilder.build());

        //step 4: 开始异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // TODO: 17-1-4  请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO: 17-1-4 请求成功
                final String str = response.body().string();
                Log.d("JSON : ", str);
                Gson gson = new Gson();
                final WeChatInfo info = gson.fromJson(str, WeChatInfo.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
        });
    }


    private void data() {
        String url0 = "http://v.juhe.cn/weixin/query?pno=1&ps=1&dtype=&key=b920149721e049c80bcc4897f50f3f52";
        OkHttpUtils
                .post()//
                .url(url0)//
//                .addParams("username", "hyman")//
//                .addParams("password", "123")//
                .build()//
                .execute(new GenericsCallback<WeChatInfo>(new JsonGenericsSerializator())
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
//                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(final WeChatInfo response, int id)
                    {
                        tx_02.setText("onResponse:" + response.getResult().getList().get(0).getTitle());
                    }
                });
    }

    private void getData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
//                .add("passportId", Cache.passport.getPassportIdStr() + "")
                .build();
        Request request = new Request.Builder()
                .url("http://v.juhe.cn/weixin/query?pno=1&ps=3&dtype=&key=b920149721e049c80bcc4897f50f3f52")
                .get()
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String str = response.body().string();
                Log.e("http : ", "http://v.juhe.cn/weixin/query?pno=1&ps=1&dtype=&key=b920149721e049c80bcc4897f50f3f52");
//                Log.d("JSON : ", str);
                Gson gson = new Gson();
                final WeChatInfo.ResultEntity.ListEntity info = gson.fromJson(str, WeChatInfo.ResultEntity.ListEntity.class);
                Log.d("JSON : ", ""+info.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ImageLoader.getInstance().displayImage(info.getFirstImg(), imgHead);
//                        Log.e("img"," = "+info.getFirstImg());
//                        Log.e("img"," = "+info.getId());
                    }
                });
            }

        });

    }

    private void todayIncome() {

        Map map = new HashMap();
        Http.post(this, map, courierURL, new HttpCallback<DeliveryTodayPayInfo.ResponseEntity.DatasEntity>() {
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
