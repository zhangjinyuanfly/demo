package com.zjy.demo.h5;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.zjy.demo.BaseApplication;
import org.json.JSONObject;

public class DefaultJSCall implements IJSCallDefault {
    private String TAG = DefaultJSCall.class.getSimpleName();

    public DefaultJSCall() {}

    public IJSCallback jsCallback;
    private String mBridgeName;

    public DefaultJSCall(String bridgeName) {
        this.mBridgeName = bridgeName;
    }

    @Override
    public void invoke(String method, String callBack, JSONObject params) {
        if(!TextUtils.isEmpty(method)) {
            if(TextUtils.equals(method, "showToast")) {
                log(callBack, params);
            }
        }
    }

    private void log(String callBack, JSONObject params) {
        Log.e("zjy","params = "+params.toString());
    }

    @Override
    public void setIJSCallback(IJSCallback jsCallback) {
        this.jsCallback = jsCallback;

    }

    @Override
    public int getJSVersion() {
        return 1;
    }

}
