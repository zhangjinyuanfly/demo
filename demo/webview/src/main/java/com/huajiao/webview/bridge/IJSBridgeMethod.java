package com.huajiao.webview.bridge;

import org.json.JSONObject;

/**
 * Created by zhangzhen on 2017/10/28.
 */

public interface IJSBridgeMethod {
    /**
     * 业务相关的方法调用，由业务自己定义，JSBridge透传调用
     * @param method
     * @param callback
     * @param params
     */
    void onCall(String method, String callback, JSONObject params);
}
