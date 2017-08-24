package com.mylibrary.http;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.BaseRequest;
import com.mylibrary.AfinalActivityHttpLife;
import com.mylibrary.utils.JsonUtils;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by zhumg on 3/15.
 */
public class Callback extends AbsCallback implements Runnable {

    private HttpCallback httpCallback;
    protected Map map;
    protected String url;
    protected Context context;
    protected boolean post = false;
    protected Dialog dialog;
    protected boolean success = true;//0成功，1失败

    public Callback(Context context, Map map, String url, boolean post, HttpCallback httpCallback, Dialog dialog) {
        this.context = context;
        this.map = map;
        this.url = url;
        this.post = post;
        this.httpCallback = httpCallback;
        this.httpCallback.context = context;
        this.dialog = dialog;
    }

    @Override
    public Object convertSuccess(Response response) throws Exception {


        try {
            String data = StringConvert.create().convertSuccess(response);

            if (Http.Debug) {
                StringBuilder sb = new StringBuilder(url);
                if (map != null && map.size() > 0) {
                    sb.append("?");
                    Iterator it = map.keySet().iterator();
                    while (it.hasNext()) {
                        String k = (String) it.next();
                        String v = (String) map.get(k);
                        sb.append(k).append("=").append(v).append("&");
                    }
                }
                //打印
                Log.i("afinal", "\n╔════════════════════════════════════════════════════════════════════════════════════════");
                Log.i("afinal", "\n\thttp get url = " + sb.toString());
                Log.i("afinal", "\n\thttp result = " + JsonUtils.formatJson(data));
                Log.i("afinal", "\n╚════════════════════════════════════════════════════════════════════════════════════════");
            }
            //关闭
            response.close();

            JSONObject json = new JSONObject(data);
            httpCallback.code = json.optInt("code");
            httpCallback.msg = json.optString("msg");
            //如果失败
            if (httpCallback.code != 0) {
                //直接返回字符串，表示失败
                success = false;//标记为失败
                return httpCallback.msg;
            }
            if (httpCallback.pass) {
                return httpCallback.msg;
            }

            //获取其泛形
            Type typeArgument = httpCallback.getT();
            //如果没有指定泛形，直接返回json 或者 string
            if (typeArgument == null) {
                return getDataJson(json);
            }
            String dataStr = getDataStr(json);
            if (dataStr.length() > 0) {
                try {
                    //如果没有类型，则直接返回json
                    Object obj = JsonUtils.fromJson(dataStr, typeArgument);
                    httpCallback.successBefore(obj);
                    return obj;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return dataStr;
                }
            }
            return dataStr;
        } catch (Exception e) {
            httpCallback.onFailure();
            e.printStackTrace();
        }
        return null;
    }

    String getDataStr(JSONObject json) {
        if (httpCallback.dataKey != null) {
            if (null != json.optJSONObject("response")) {
                return json.optJSONObject("response").optString(httpCallback.dataKey);
            } else {
                return null;
            }

        } else {
            return json.optString("response");
        }
    }

    JSONObject getDataJson(JSONObject json) {
        if (httpCallback.dataKey != null) {
            return json.optJSONObject("response").optJSONObject(httpCallback.dataKey);
        } else {
            return json.optJSONObject("response");
        }
    }

    @Override
    public void run() {
        try {
            if (post) {
                OkGo.post(url)     // 请求方式和请求url
                        .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                        .params(map)
                        .execute(this);
            } else {
                OkGo.get(url)     // 请求方式和请求url
                        .tag(context)                       // 请求的 tag, 主要用于取消对应的请求
                        .params(map)
                        .execute(this);
            }
            if (Http.Debug) {
                StringBuilder sb = new StringBuilder(url);
                if (map != null && map.size() > 0) {
                    sb.append("?");
                    Iterator it = map.keySet().iterator();
                    while (it.hasNext()) {
                        String k = (String) it.next();
                        String v = (String) map.get(k);
                        sb.append(k).append("=").append(v).append("&");
                    }
                }
                //打印
                Log.i("afinal", "\n╔════════════════════════════════════════════════════════════════════════════════════════");
                Log.i("afinal", "\n\thttp get url = " + sb.toString());
                Log.i("afinal", "\n╚════════════════════════════════════════════════════════════════════════════════════════");
            }
        } catch (Exception e) {
            httpCallback.onFailure();
            e.printStackTrace();
        }
    }

    /**
     * 请求网络开始前，UI线程
     */
    public void onBefore(BaseRequest request) {
    }

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public void onSuccess(Object t, Call call, Response response) {
        if (httpCallback.context instanceof AfinalActivityHttpLife) {
            AfinalActivityHttpLife life = (AfinalActivityHttpLife) httpCallback.context;
            if (life.isHttpFinish()) {
                Log.e("db", "http 回调时，UI 已被关闭，不再回调");
                return;
            }
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        if (success) {
            httpCallback.onSuccess(t);
        } else {
            httpCallback.onFailure();
        }
    }

    /**
     * 缓存成功的回调,UI线程
     */
    public void onCacheSuccess(Object t, Call call) {
    }

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
     */
    public void onError(Call call, Response response, Exception e) {
        if (httpCallback.context instanceof AfinalActivityHttpLife) {
            AfinalActivityHttpLife life = (AfinalActivityHttpLife) httpCallback.context;
            if (life.isHttpFinish()) {
                Log.e("db", "http 回调时，UI 已被关闭，不再回调");
                return;
            }
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        if (response == null) {
            if (e instanceof java.net.SocketTimeoutException) {
                httpCallback.msg = "网络超时，请稍后再试";
            } else {
                httpCallback.msg = "网络异常，请稍后再试";
            }
            httpCallback.onError();
            return;
        }
        //如果有异常，则拿异常字符串出来
        if (e != null) {
            e.printStackTrace();
            httpCallback.code = response.code();
            //如果是404
            if (httpCallback.code == 404) {
                httpCallback.msg = "404 服务器异常";
            } else if (httpCallback.code == 400) {
                httpCallback.msg = "400 客户端传参错误";
            } else {
                httpCallback.msg = response.message();
            }
            //异常处理
            httpCallback.onError();
            return;
        }
        if (!success) {
            //失败
            httpCallback.onFailure();
            return;
        }
        httpCallback.onError();
    }

    /**
     * 缓存失败的回调,UI线程
     */
    public void onCacheError(Call call, Exception e) {
    }

    /**
     * 网络失败结束之前的回调
     */
    public void parseError(Call call, Exception e) {
    }

    /**
     * 请求网络结束后，UI线程
     */
    public void onAfter(Object t, Exception e) {
        if (e != null) e.printStackTrace();
    }

}
