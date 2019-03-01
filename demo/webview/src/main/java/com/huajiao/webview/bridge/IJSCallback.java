package com.huajiao.webview.bridge;

import org.json.JSONObject;

/**
 * JS回调接口
 * 实现者可以是个webview,也可以是其他东西
 * Created by zhangzhen on 2017/10/28.
 */

public interface IJSCallback {
    void callbackJS(String callback, JSONObject result);
}
