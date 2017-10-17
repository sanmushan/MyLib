package com.example.pver.mylib;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pver.mylib.bean.WeChatInfo;
import com.mylibrary.AfinalSwipeBackActivity;
import com.mylibrary.callback.BitmapCallback;
import com.mylibrary.callback.GenericsCallback;
import com.mylibrary.http.OkHttpUtils;
import com.mylibrary.widget.JsonGenericsSerializator;

import okhttp3.Call;

/**
 * OkHttpUtils请求
 */
public class MySwipeBackActivity extends AfinalSwipeBackActivity {
    TextView txt;
    ImageView img;


    @Override
    public int getContentViewId() {
        return R.layout.my_swipeback_layout;
    }

    @Override
    public void initView(View view) {
        txt = (TextView) findViewById(R.id.txt);
        img = (ImageView) findViewById(R.id.mImageView);
        getImage();
//        checkForHeaderPost();
    }
    public void getImage()
    {
        String url = "http://images.csdn.net/20150817/1.jpg";
        OkHttpUtils
                .get()//
                .url(url)//
                .tag(this)//
                .build()//
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(new BitmapCallback()
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
//                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int id)
                    {
                        Log.e("TAG", "onResponse：complete");
                        img.setImageBitmap(bitmap);
                    }
                });
    }

    public void getUser(View view)//TODO ------------------------------------
    {
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
                    public void onResponse(WeChatInfo response, int id)
                    {
//                        txt.setText(""+response.getResult().getList().get(0).getId());
//                        ToastUtil.showToast(MySwipeBackActivity.this, "123456");
                        Toast.makeText(MySwipeBackActivity.this,"自定义显示位置的Toast",Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
// ToastUtil.showToast(MySwipeBackActivity.this, "" + response.getResponse().getDatas().get(0).getId());
//         txt.setText(""+response.getResponse().getDatas().get(0).getName());