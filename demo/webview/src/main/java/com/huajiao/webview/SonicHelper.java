package com.huajiao.webview;

import android.content.Context;
import android.content.Intent;

import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;

import java.io.File;

public class SonicHelper {

    public static final String TAG = "sonicweb";

    private static SonicHelper sonicHelper;
    private Context mContext;
    private String ua;
    private String userAccount;
    private File cacheFile;
    private File cacheResourceFile;

    private SonicHelper(Context context) {
        if(context != null) {
            this.mContext = context.getApplicationContext();
        }
    }

    public static synchronized SonicHelper getInstance(Context context) {
        if(sonicHelper == null) {
            sonicHelper = new SonicHelper(context);
        }
        return sonicHelper;
    }

    private static final long sonic_check = 6 * 60 * 60 * 1000L;

    private SonicRuntimeImpl sonicRuntime;

    private SonicRuntimeImpl getSonicRuntime() {
        if(sonicRuntime == null) {
            sonicRuntime = new SonicRuntimeImpl(mContext);
            sonicRuntime.setCacheFile(cacheFile);
            sonicRuntime.setCacheResourceFile(cacheResourceFile);
            sonicRuntime.setUserAgent(ua);
            sonicRuntime.setCurrentUserAccount(userAccount);
        }

        return sonicRuntime;
    }

    public void initSonicEngine() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicConfig sonicConfig = new SonicConfig.Builder()
                    .setCacheCheckTimeInterval(sonic_check)
                    .setMaxPreloadSessionCount(10)// 最大proload数量
                    .build();
            SonicEngine.createInstance(getSonicRuntime(), sonicConfig);
        }
    }

    public SonicHelper setUserAgent(String ua) {
        this.ua = ua;
        if(sonicRuntime != null) {
            sonicRuntime.setUserAgent(ua);
        }
        return this;
    }

    /**
     * 设置用户，cache分用户贮存
     * @param userAccount
     * @return
     */
    public SonicHelper setUserAccount(String userAccount) {
        this.userAccount = userAccount;
        if(sonicRuntime != null) {
            sonicRuntime.setCurrentUserAccount(userAccount);
        }
        return this;
    }

    public SonicHelper setCacheFile(File cacheFile) {
        this.cacheFile = cacheFile;
        if(sonicRuntime != null) {
            sonicRuntime.setCacheFile(cacheFile);
        }
        return this;
    }

    public SonicHelper setCacheResourceFile(File cacheResourceFile) {
        this.cacheResourceFile = cacheResourceFile;
        if(sonicRuntime != null) {
            sonicRuntime.setCacheResourceFile(cacheResourceFile);
        }
        return this;
    }

    /**
     * 预加载url
     * @param url
     */
    public boolean preloadUrl(String url, SonicCacheInterceptor interceptor) {
        initSonicEngine();
        SonicSessionConfig sonicSessionConfig = new SonicSessionConfig.Builder()
                .setSupportLocalServer(true)
                .setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
                    @Override
                    public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                        return new SonicSessionConnection.SessionConnectionDefaultImpl(session, intent);
                    }
                })
                .build();
        return SonicEngine.getInstance().preCreateSession(url, sonicSessionConfig);
    }

    /**
     * 预加载url
     * @param url
     * @return 是否预加载成功
     */
    public boolean preloadUrl(String url) {
        initSonicEngine();
        SonicSessionConfig sonicSessionConfig = new SonicSessionConfig.Builder()
                .setSupportLocalServer(true)
                .build();
        return SonicEngine.getInstance().preCreateSession(url, sonicSessionConfig);
    }

    /**
     * 清理sonic cache
     */
    public void clearCache() {
        initSonicEngine();
        SonicEngine.getInstance().cleanCache();
    }

    /**
     * 创建sonicwebview
     * @param url
     * @return
     */
    public SonicWebView buildSonic(String url) {
        SonicWebView build = new SonicWebView(mContext, url);
        return build;
    }

    public void onDestroy(SonicWebView webView) {
        if(webView != null) {
            webView.onDestroy();
        }
    }

    public void onDestroy() {
        mContext = null;
    }
}
