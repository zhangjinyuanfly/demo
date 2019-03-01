package com.huajiao.webview.bridge;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**JSBridge
 * 通过反射调用接口，优先查找上层业务覆盖的方法，如果上层没有覆盖，则尝试反射内置的方法。
 * Created by zhangzhen on 2017/10/28.
 */

public class JSBridge {
    private static final String TAG = "JSBridge";

    private IJSCallback mCallback;

    private HashMap<String, IJSBridgeMethod> mMethodMap;


    public JSBridge(){
    }

    public void registerCallback(IJSCallback callback){
        mCallback = callback;
    }

    public void registerMethod(String methodName, IJSBridgeMethod method){
        if(mMethodMap == null){
            mMethodMap = new HashMap<>();
        }
        mMethodMap.put(methodName,method);
    }

    public void unregisterMethod(String methodName){
        if(mMethodMap != null && mMethodMap.containsKey(methodName)){
            mMethodMap.remove(methodName);
        }
    }

    private void unregisterAllMethods(){
        if(mMethodMap != null){
            mMethodMap.clear();;
        }
    }

    public void destroy(){
        unregisterAllMethods();
        mCallback = null;
    }

    /**
     * 处理JS onPromt
     * @param jsMsg 通过onJSPromt得到的message; 也可以通过其他渠道传递，如scheme等
     *              格式如下：
     *              {"method":"methodname","callback":"callbackname","params":{...},"extra":""}
     */
    public void handleJSCall(String jsMsg){
//        LivingLog.e(TAG, "js message==" + jsMsg);
        String method = null;
        String callback = null;
        try {
            JSONObject msgJson = new JSONObject(jsMsg);
            if (msgJson != null) {
                method = msgJson.optString("method");
                callback = msgJson.optString("callback");
                JSONObject paramJson = msgJson.optJSONObject("params");
                JSCall(method,callback,paramJson);
            }
        }catch (JSONException e){
            e.printStackTrace();
//            LivingLog.e(TAG, "handleJSCall json error" );
        }
    }


    /**
     * JS调用,建议通过handleJSCall来调用
     * 优先查找上层业务覆盖的方法，如果上层没有覆盖，则尝试反射内置的方法。
     * @param methodname
     * @param callback
     * @param params
     */
    public void JSCall(String methodname, String callback,  JSONObject params){
        if(mMethodMap != null && mMethodMap.containsKey(methodname)){
            IJSBridgeMethod method = mMethodMap.get(methodname);
            method.onCall(methodname,callback,params);
        } else {
            //上层未覆盖方法，查找预制的静态方法
            if(preloadJSCall == null) {
                return;
            }
            preloadJSCall.setIJSCallback(mCallback);
            try {
                Method method = preloadJSCall.getClass().getDeclaredMethod("invoke",new Class[]{String.class, String.class, JSONObject.class});
                try {
                    method.invoke(preloadJSCall,methodname,callback,params);
                } catch (IllegalAccessException e) {
//                    LivingLog.e(TAG, "JSCall local method: IllegalAccessException" );
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
//                    LivingLog.e(TAG, "JSCall local method: InvocationTargetException" );
                    e.printStackTrace();
                }

            } catch (NoSuchMethodException e) {
//                LivingLog.e(TAG, "JSCall local method: NoSuchMethodException:"+methodname );
                e.printStackTrace();
            }
        }

    }

    /**
     * 统一的JAVA 回调 JS方法
     * @param callback
     * @param result
     */
    public void callBackJS(String callback, JSONObject result) {
        if(mCallback != null){
            mCallback.callbackJS(callback,result);
        }

    }

    private IJSCallDefault preloadJSCall;
    public void setPreloadJSCall(IJSCallDefault jsCall) {
        this.preloadJSCall = jsCall;
    }

}
