package com.huajiao.webview;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceResponse;

import com.tencent.sonic.sdk.SonicRuntime;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

class SonicRuntimeImpl extends SonicRuntime {
    public SonicRuntimeImpl(Context context) {
        super(context);
    }

    private String ua;

    private String userAccount;

    private File cacheFile;
    private File cacheResourceFile;

    public void setUserAgent(String ua) {
        this.ua = ua;
    }

    public void setCurrentUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public void setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    public void setCacheResourceFile(File cacheResourceFile) {
        this.cacheResourceFile = cacheResourceFile;
    }

    /**
     * 获取用户UA信息
     * @return
     */
    @Override
    public String getUserAgent() {
        return ua;
    }

    /**
     * 获取用户ID信息
     * @return
     */
    @Override
    public String getCurrentUserAccount() {
        return userAccount;
    }

    @Override
    public String getCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }

    @Override
    public void log(String tag, int level, String message) {
        switch (level) {
            case Log.ERROR:
                Log.e(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            default:
                Log.d(tag, message);
        }
    }

    @Override
    public Object createWebResourceResponse(String mimeType, String encoding, InputStream data, Map<String, String> headers) {
        WebResourceResponse resourceResponse =  new WebResourceResponse(mimeType, encoding, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            resourceResponse.setResponseHeaders(headers);
        }
        return resourceResponse;
    }

    @Override
    public void showToast(CharSequence text, int duration) {

    }

    @Override
    public void notifyError(SonicSessionClient client, String url, int errorCode) {

    }

    @Override
    public boolean isSonicUrl(String url) {
        return true;
    }

    @Override
    public boolean setCookie(String url, List<String> cookies) {
        if (!TextUtils.isEmpty(url) && cookies != null && cookies.size() > 0) {
            CookieManager cookieManager = CookieManager.getInstance();
            for (String cookie : cookies) {
                cookieManager.setCookie(url, cookie);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isNetworkValid() {
        return true;
    }

    @Override
    public void postTaskToThread(Runnable task, long delayMillis) {
        Thread thread = new Thread(task, "SonicThread");
        thread.start();
    }

    @Override
    public File getSonicCacheDir() {
        if(cacheFile != null) {
            if(!cacheFile.exists()){
                cacheFile.mkdir();
            }
            return cacheFile;
        }
        return super.getSonicCacheDir();
    }

    @Override
    public String getHostDirectAddress(String url) {
        return null;
    }

    @Override
    public File getSonicResourceCacheDir() {
        if(cacheResourceFile != null) {
            if(!cacheResourceFile.exists()) {
                cacheResourceFile.mkdir();
            }
            return cacheResourceFile;
        }
        return super.getSonicResourceCacheDir();
    }
}
