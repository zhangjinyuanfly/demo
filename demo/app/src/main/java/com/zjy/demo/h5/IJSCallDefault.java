package com.zjy.demo.h5;

import org.json.JSONObject;

public interface IJSCallDefault {

    /**
     * js调java
     * @param method
     * @param callBack
     * @param params
     */
    void invoke(String method, String callBack, JSONObject params);

    /**
     * java调js
     * @param ijsCallback
     */
    void setIJSCallback(IJSCallback ijsCallback);

    /**
     * 本地jsbridge版本号
     * @return
     */
    int getJSVersion();
}
