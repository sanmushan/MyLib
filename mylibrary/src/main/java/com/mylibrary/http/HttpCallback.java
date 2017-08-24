package com.mylibrary.http;

import android.content.Context;


import com.mylibrary.utils.ToastUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zhumg on 3/15.
 */
public abstract class HttpCallback<T> {

    protected Context context;
    protected int code;
    protected String msg;
    protected T data;
    protected String dataKey;
    //是否允许直接pass,当response没有内容时，该值应该设置为true
    protected boolean pass = false;

    public HttpCallback() {
    }

    public HttpCallback(String dataKey) {
        this.dataKey = dataKey;
    }

    public HttpCallback setPass() {
        pass = true;
        return this;
    }

    //拿到泛型实现类
    protected final Type getT() {
        try {
            Type mySuperClass = this.getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) mySuperClass).getActualTypeArguments()[0];
            return type;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public void successBefore(T data) {

    }

    /**
     * 数据接收并解释成功
     */
    public abstract void onSuccess(T data);

    /**
     * 数据接收并解释错误时调用
     * 即，期待 T ，但服务器没有返回 T ，返回了 不同的 code
     */
    public void onFailure() {
        if (msg != null) {
            ToastUtil.showToast(context, msg);
        }
    }

    /**
     * 数据接收异常，或者解释异常时调用
     */
    public void onError() {
        onFailure();
    }
}
