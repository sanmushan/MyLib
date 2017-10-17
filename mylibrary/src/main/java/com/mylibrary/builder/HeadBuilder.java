package com.mylibrary.builder;


import com.mylibrary.http.OkHttpUtils;
import com.mylibrary.request.OtherRequest;
import com.mylibrary.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
